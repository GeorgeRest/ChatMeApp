package com.george.chatmeapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.george.chatmeapp.activities.AddActivity;
import com.george.chatmeapp.activities.ChatActivity;
import com.george.chatmeapp.R;
import com.george.chatmeapp.adapters.ChatListAdapter;
import com.george.chatmeapp.beans.AddUserInfo;
import com.george.chatmeapp.beans.MessageList;
import com.george.chatmeapp.beans.UserItem;
import com.george.chatmeapp.utils.MyToast;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ChatListFragment extends Fragment {
    private static final String TAG = "ChatListFragment";
    private List<UserItem> users = new ArrayList<>();
    private UserItem userItem;
    private String lastContent;
    private String time;
    private ListView listView;
    private ChatListAdapter chatListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chatlist_fragment, container, false);
        chatListAdapter = new ChatListAdapter(getActivity(), R.layout.chat_list_item, users);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(chatListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userItem = users.get(position);
                UserItem item = new UserItem();
                item.setUserName(userItem.getUserName());
                item.setImageId(userItem.getImageId());
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("item", item);
                getActivity().startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                UserItem userItem = users.get(position);
                String userName = userItem.getUserName();
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("删除用户");
                dialog.setMessage("确定删除用户" + userName + "吗?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddUserInfo addUserInfo = LitePal.where("name=?", userName).findFirst(AddUserInfo.class);
                        long deleteOrderId = addUserInfo.getOrderId();
                        List<AddUserInfo> userInfos = LitePal.findAll(AddUserInfo.class);
                        for (AddUserInfo userinfo :userInfos) {
                            if(userinfo.getOrderId()>deleteOrderId){
                                userinfo.setOrderId(userinfo.getOrderId()-1);
                                userinfo.save();
                            }

                        }
                        LitePal.deleteAll(AddUserInfo.class, "name=?", userName);
                        LitePal.deleteAll(MessageList.class, "username=?", userName);

                        data();

                        MyToast.show(getActivity(), "删除用户" + userName + "成功");

                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();



                return true;
            }
        });
        ImageButton more = (ImageButton) view.findViewById(R.id.ib_more_vertical);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), more);
                popupMenu.getMenuInflater().inflate(R.menu.add_delete_item, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.add_item:
                                Intent intent = new Intent(getActivity(), AddActivity.class);
                                intent.putExtra("activity","ChatListFragment");
                                startActivity(intent);
                                break;

                            case R.id.delete_item:
                                MyToast.show(getActivity(), "长按即可删除用户");
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        return view;

    }
    @Override
    public void onStart() {
        super.onStart();
        data();
    }
    private void data() {
        List<AddUserInfo> topUserinfoList = LitePal.where("istop=? and issend=?", AddUserInfo.Top+"",AddUserInfo.send+"").order("timestamp desc").find(AddUserInfo.class);
        List<AddUserInfo> noTopUserinfoList = LitePal.where("istop=? and issend=?", AddUserInfo.unTop + "",AddUserInfo.send+"").order("timestamp desc").find(AddUserInfo.class);
        List<AddUserInfo> addUserInfos=new ArrayList<>();
        addUserInfos.addAll(topUserinfoList);
        addUserInfos.addAll(noTopUserinfoList);
        users.clear();
        for (AddUserInfo addUserInfo : addUserInfos) {
            String name = addUserInfo.getName();
            int isTop = addUserInfo.getIsTop();
            MessageList lastData = LitePal.where("username=?", name).findLast(MessageList.class);
            if (addUserInfo.getIsSend() == AddUserInfo.send) {
                List<String> content = lastData.getContent();
                time = lastData.getTime();
                lastContent = content.get(content.size() - 1);
            } else {
                lastContent = "";
                time = "";
            }
            UserItem userItem = new UserItem(addUserInfo.getName(), lastContent, time,getDrawableRes(getActivity(), addUserInfo.getImageName()), isTop);
            users.add(userItem);

        }
        chatListAdapter.notifyDataSetChanged();


    }


    private int getDrawableRes(Context context, String name) {
        String packageName = context.getPackageName();
        return context.getResources().getIdentifier(name, "mipmap", packageName);
    }



}

