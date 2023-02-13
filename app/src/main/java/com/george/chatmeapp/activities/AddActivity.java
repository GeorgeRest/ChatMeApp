package com.george.chatmeapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.george.chatmeapp.R;
import com.george.chatmeapp.beans.AddUserInfo;
import com.george.chatmeapp.utils.MyToast;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private String imageName = "";
    AddUserInfo addUserInfo = new AddUserInfo();
    private TextView addName;
    private String name;
    private List<AddUserInfo> addUserInfos;
    private int selectedIndex;
    private ImageView[] photoImageViews;
    private int imageId;
    private String activity;
    private Button addButton;
    private TextView title;
    private Long orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initialize();
        Intent intent = getIntent();
        activity = intent.getStringExtra("activity");
        if (activity.equals("ChatListFragment") || activity.equals("ContactFragment")) {
            title.setText("添加你喜欢的用户");
        }else if(activity.equals("SelfFragment")){
            title.setText("修改信息");
        }
        addUserInfos = LitePal.findAll(AddUserInfo.class);
        orderId = LitePal.max(AddUserInfo.class, "orderid", long.class);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addName = (TextView) findViewById(R.id.add_name);
                name = addName.getText().toString().trim();

                if (name.isEmpty() || imageName.equals("")) {
                    MyToast.show(AddActivity.this, "请输入名字并选择头像");
                    return;
                }
                if (activity.equals("ChatListFragment") || activity.equals("ContactFragment")) {
                    LitePal.getDatabase();
                    for (AddUserInfo addUser : addUserInfos) {
                        if (name.equals(addUser.getName())) {
                            MyToast.show(AddActivity.this, "用户" + name + "已被添加");
                            return;
                        }
                    }
                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String time = formatter.format(date);
                    addUserInfo.setGender("男");
                    addUserInfo.setCreationTime(time);
                    addUserInfo.setName(name);
                    addUserInfo.setImageName(imageName);
                    addUserInfo.setIsSend(AddUserInfo.unSend);
                    addUserInfo.setOrderId(orderId+1);
                    addUserInfo.setIsTop(AddUserInfo.unTop);
                    addUserInfo.save();
                    finish();
                    MyToast.show(AddActivity.this, "用户" + name + "添加成功");
                }else if(activity.equals("SelfFragment")){

                    SharedPreferences selfInfo = getSharedPreferences("selfInfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = selfInfo.edit();
                    editor.putString("selfName",name);
                    editor.putInt("selfPhoto",imageId);
                    editor.commit();
                    MyToast.show(AddActivity.this, "修改成功");
                    finish();
                }
            }

        });

    }


    private void photoChange(int selectedIndex) {
        photoImageViews[selectedIndex].setSelected(true);
        for (int i = 0; i < photoImageViews.length; i++) {
            if (i != selectedIndex) {
                photoImageViews[i].setSelected(false);
            }
        }
    }

    private void initialize() {
        photoImageViews = new ImageView[16];
        photoImageViews[0] = (ImageView) findViewById(R.id.photo1);
        photoImageViews[1] = (ImageView) findViewById(R.id.photo2);
        photoImageViews[2] = (ImageView) findViewById(R.id.photo3);
        photoImageViews[3] = (ImageView) findViewById(R.id.photo4);
        photoImageViews[4] = (ImageView) findViewById(R.id.photo5);
        photoImageViews[5] = (ImageView) findViewById(R.id.photo6);
        photoImageViews[6] = (ImageView) findViewById(R.id.photo7);
        photoImageViews[7] = (ImageView) findViewById(R.id.photo8);
        photoImageViews[8] = (ImageView) findViewById(R.id.photo9);
        photoImageViews[9] = (ImageView) findViewById(R.id.photo10);
        photoImageViews[10] = (ImageView) findViewById(R.id.photo11);
        photoImageViews[11] = (ImageView) findViewById(R.id.photo12);
        photoImageViews[12] = (ImageView) findViewById(R.id.photo13);
        photoImageViews[13] = (ImageView) findViewById(R.id.photo14);
        photoImageViews[14] = (ImageView) findViewById(R.id.photo15);
        photoImageViews[15] = (ImageView) findViewById(R.id.photo16);
        addButton = (Button) findViewById(R.id.add_name_image);
        title = (TextView) findViewById(R.id.tv_title);
    }

    public void back(View view) {
        finish();
    }

    public void image(View view) {
        switch (view.getId()) {

            case R.id.photo1:
                imageName = "photo1";
                selectedIndex = 0;
                imageId = R.mipmap.photo1;
                photoChange(selectedIndex);

                break;
            case R.id.photo2:
                imageName = "photo2";
                selectedIndex = 1;
                imageId = R.mipmap.photo2;
                photoChange(selectedIndex);

                break;
            case R.id.photo3:
                imageName = "photo3";
                selectedIndex = 2;
                imageId = R.mipmap.photo3;
                photoChange(selectedIndex);
                break;
            case R.id.photo4:
                imageName = "photo4";
                selectedIndex = 3;
                imageId = R.mipmap.photo4;
                photoChange(selectedIndex);
                break;
            case R.id.photo5:
                imageName = "photo5";
                selectedIndex = 4;
                imageId = R.mipmap.photo5;
                photoChange(selectedIndex);
                break;
            case R.id.photo6:
                imageName = "photo6";
                selectedIndex = 5;
                imageId = R.mipmap.photo6;
                photoChange(selectedIndex);
                break;
            case R.id.photo7:
                imageName = "photo7";
                selectedIndex = 6;
                imageId = R.mipmap.photo7;
                photoChange(selectedIndex);
                break;
            case R.id.photo8:
                imageName = "photo8";
                selectedIndex = 7;
                imageId = R.mipmap.photo8;
                photoChange(selectedIndex);
                break;
            case R.id.photo9:
                imageName = "photo9";
                selectedIndex = 8;
                imageId = R.mipmap.photo9;
                photoChange(selectedIndex);
                break;
            case R.id.photo10:
                imageName = "photo10";
                selectedIndex = 9;
                imageId = R.mipmap.photo10;
                photoChange(selectedIndex);
                break;
            case R.id.photo11:
                imageName = "photo11";
                selectedIndex = 10;
                imageId = R.mipmap.photo11;
                photoChange(selectedIndex);
                break;
            case R.id.photo12:
                imageName = "photo12";
                selectedIndex = 11;
                imageId = R.mipmap.photo12;
                photoChange(selectedIndex);
                break;
            case R.id.photo13:
                imageName = "photo13";
                selectedIndex = 12;
                imageId = R.mipmap.photo13;
                photoChange(selectedIndex);
                break;
            case R.id.photo14:
                imageName = "photo14";
                selectedIndex = 13;
                imageId = R.mipmap.photo14;
                photoChange(selectedIndex);
                break;
            case R.id.photo15:
                imageName = "photo15";
                selectedIndex = 14;
                imageId = R.mipmap.photo15;
                photoChange(selectedIndex);
                break;
            case R.id.photo16:
                imageName = "photo16";
                selectedIndex = 15;
                imageId = R.mipmap.photo16;
                photoChange(selectedIndex);
                break;
        }
    }


}