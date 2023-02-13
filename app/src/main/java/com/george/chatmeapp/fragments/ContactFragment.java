package com.george.chatmeapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.george.chatmeapp.activities.AddActivity;
import com.george.chatmeapp.R;
import com.george.chatmeapp.activities.UserInfoActivity;
import com.george.chatmeapp.adapters.ContactListAdapter;
import com.george.chatmeapp.beans.AddUserInfo;
import com.george.chatmeapp.beans.UserItem;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {
    private List<UserItem> users = new ArrayList<>();
    private UserItem userItem;
    private ListView listView;
    private ContactListAdapter contactListAdapter;
    private List<AddUserInfo> addUserInfos;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fragment, container, false);
        contactListAdapter = new ContactListAdapter(getContext(), R.layout.contact_item, users);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(contactListAdapter);

        TextView addFriend = (TextView) view.findViewById(R.id.tv_add_friend);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                intent.putExtra("activity","ContactFragment");
                startActivity(intent);

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userItem = users.get(position);
                UserItem item = new UserItem();
                item.setUserName(userItem.getUserName());
                item.setImageId(userItem.getImageId());
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                intent.putExtra("userName", item);
                getActivity().startActivity(intent);
            }
        });


        return view;
    }


    private void data() {
        addUserInfos = LitePal.findAll(AddUserInfo.class);
        users.clear();
        for (AddUserInfo addUserInfo : addUserInfos) {
            String name = addUserInfo.getName();
            String imageName = addUserInfo.getImageName();
            UserItem userItem = new UserItem();
            userItem.setUserName(name);
            userItem.setImageId(getDrawableRes(getActivity(), imageName));
            users.add(userItem);

        }
        contactListAdapter.notifyDataSetChanged();


    }

    private int getDrawableRes(Context context, String name) {
        String packageName = context.getPackageName();
        return context.getResources().getIdentifier(name, "mipmap", packageName);
    }

    @Override
    public void onStart() {
        super.onStart();
        data();
    }



}