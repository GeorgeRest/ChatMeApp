package com.george.chatmeapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.george.chatmeapp.R;
import com.george.chatmeapp.beans.AddUserInfo;
import com.george.chatmeapp.beans.MessageList;
import com.george.chatmeapp.beans.UserItem;
import com.george.chatmeapp.utils.MyToast;

import org.litepal.LitePal;

import java.util.List;

public class UserInfoActivity extends AppCompatActivity {

    public static final int RESULT_CODE_NAME = 3;
    private final int REQUEST_CODE_NAME = 1;
    private TextView tv_name;
    private TextView tv_gender;
    private TextView tv_time;
    private String userName;
    private ImageView photo;
    private List<AddUserInfo> addUserInfos;
    private String name;
    private String newName;
    private int imageId;
    private Switch aSwitch;
    private SharedPreferences isTop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initialize();
        Intent intent = getIntent();
        UserItem userItem = (UserItem) intent.getSerializableExtra("userName");
        userName = userItem.getUserName();
        imageId = userItem.getImageId();
        photo.setImageResource(imageId);
        addUserInfos = LitePal.where("name=?", userName).find(AddUserInfo.class);
        setInfo();
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isTop = getSharedPreferences("isTop", MODE_PRIVATE);
                    SharedPreferences.Editor editor = isTop.edit();
                    editor.putBoolean(newName, true);
                    editor.commit();


                    List<AddUserInfo> userInfo = LitePal.findAll(AddUserInfo.class);
                    AddUserInfo topUser = LitePal.where("name=?", newName).findFirst(AddUserInfo.class);
                    long topOrderId = topUser.getOrderId();
                    for (AddUserInfo user : userInfo) {
                        long orderId = user.getOrderId();
                        if (user.getName().equals(newName)) {
                            user.setOrderId(AddUserInfo.Top);
                            user.setIsTop(AddUserInfo.Top);
                        } else if (orderId < topOrderId) {
                            user.setOrderId(orderId + 1);
                        }
                    }
                    LitePal.saveAll(userInfo);

                } else {
                    SharedPreferences isTop = getSharedPreferences("isTop", MODE_PRIVATE);
                    SharedPreferences.Editor editor = isTop.edit();
                    editor.putBoolean(newName, false);
                    editor.commit();
                    int topCount = LitePal.where("istop=?", AddUserInfo.Top + "").count(AddUserInfo.class);

                    AddUserInfo cancelUser = LitePal.where("name = ?", newName).findFirst(AddUserInfo.class);
                    long cancelOrderId = cancelUser.getOrderId();
                    List<AddUserInfo> userInfo = LitePal.where("istop = ?", "1").find(AddUserInfo.class);
                    for (AddUserInfo user : userInfo) {
                        long itemOrderId = user.getOrderId();
                        if (user.getName().equals(newName)) {
                            user.setOrderId(topCount);
                            user.setIsTop(AddUserInfo.unTop);
                        } else if (itemOrderId > cancelOrderId) {
                            user.setOrderId(itemOrderId - 1);
                        }
                        LitePal.saveAll(userInfo);

                    }

                }
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setInfo();
    }


    private void setInfo() {

        for (AddUserInfo userInfo : addUserInfos) {
            newName = userInfo.getName();
            tv_name.setText(newName);
            tv_gender.setText(userInfo.getGender());
            tv_time.setText(userInfo.getCreationTime());

            if ("男".equals(userInfo.getGender())) {
                tv_name.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.mipmap.man, 0);
            } else {
                tv_name.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.mipmap.woman, 0);
            }
        }

        SharedPreferences isTop = getSharedPreferences("isTop", MODE_PRIVATE);
        boolean isSelect = isTop.getBoolean(newName, false);
        aSwitch.setChecked(isSelect);


    }

    public void alter(View view) {
        Intent intent = new Intent(UserInfoActivity.this, ModificationActivity.class);
        UserItem userItem = new UserItem();
        userItem.setUserName(newName);
        userItem.setaSwitch(aSwitch.isChecked());
        intent.putExtra("userName", userItem);
        startActivityForResult(intent, REQUEST_CODE_NAME);
    }

    public void clearChat(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(UserInfoActivity.this);
        dialog.setTitle("清空聊天记录");
        dialog.setMessage("确定清空聊天记录吗?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LitePal.deleteAll(MessageList.class, "username=?", newName);
                ContentValues values = new ContentValues();
                values.put("issend", AddUserInfo.unSend);
                LitePal.updateAll(AddUserInfo.class, values, "name=?", newName);
                MyToast.show(UserInfoActivity.this, "清空聊天记录成功");

            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }
    public void deleteContacts(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(UserInfoActivity.this);
        dialog.setTitle("删除用户");
        dialog.setMessage("确定删除用户" + userName + "吗?");
        dialog.setCancelable(true);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddUserInfo addUserInfo = LitePal.where("name=?", newName).findFirst(AddUserInfo.class);
                long deleteOrderId = addUserInfo.getOrderId();
                List<AddUserInfo> userInfos = LitePal.findAll(AddUserInfo.class);
                for (AddUserInfo userinfo :userInfos) {
                    if(userinfo.getOrderId()>deleteOrderId){
                        userinfo.setOrderId(userinfo.getOrderId()-1);
                        userinfo.save();
                    }

                }
                LitePal.deleteAll(AddUserInfo.class, "name=?", newName);
                LitePal.deleteAll(MessageList.class, "username=?", newName);

                MyToast.show(UserInfoActivity.this, "删除用户" + newName + "成功");

            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    private void initialize() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_time = (TextView) findViewById(R.id.tv_time);
        photo = (ImageView) findViewById(R.id.iv_photo);
        aSwitch = (Switch) findViewById(R.id.chatTop);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case ModificationActivity.RESULT_CODE_NAME:
                name = data.getStringExtra("name");
                addUserInfos = LitePal.where("name=?", name).find(AddUserInfo.class);
        }
    }

    public void back(View view) {
        Intent intent = new Intent();
        intent.putExtra("name", newName);
        setResult(RESULT_CODE_NAME, intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("name", newName);
        setResult(RESULT_CODE_NAME, intent);
        super.onBackPressed();

    }


    public void sendMessage(View view) {
        UserItem userItem = new UserItem();
        userItem.setUserName(newName);
        userItem.setImageId(imageId);
        Intent intent = new Intent(UserInfoActivity.this, ChatActivity.class);
        intent.putExtra("item", userItem);
        startActivity(intent);
    }



}