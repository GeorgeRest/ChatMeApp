package com.george.chatmeapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.george.chatmeapp.R;
import com.george.chatmeapp.fragments.ChatListFragment;
import com.george.chatmeapp.fragments.ContactFragment;
import com.george.chatmeapp.fragments.SelfFragment;
import com.george.chatmeapp.utils.MyToast;

public class ChatListActivity extends AppCompatActivity {


    private FragmentManager fragmentManager;
    private ChatListFragment chatListFragment;
    private ContactFragment contactFragment;
    private View messageLayout;
    private View contactsLayout;
    private TextView tv_message;
    private TextView tv_contact;
    private TextView tv_self;
    private ImageView iv_message;
    private ImageView iv_contact;
    private ImageView iv_self;
    private FrameLayout frameLayout;
    private FragmentTransaction transaction;
    private SelfFragment selfFragment;
    private long firstPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);
        initialize();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        frameLayout = (FrameLayout) findViewById(R.id.fragment);
        chatListFragment=new ChatListFragment();
        contactFragment=new ContactFragment();
        selfFragment = new SelfFragment();
        transaction.add(R.id.fragment,chatListFragment);
        transaction.add(R.id.fragment,contactFragment);
        transaction.add(R.id.fragment,selfFragment);
        transaction.show(chatListFragment);
        transaction.hide(contactFragment);
        transaction.hide(selfFragment);
        transaction.commit();
        tv_message.setSelected(true);
        iv_message.setSelected(true);

    }

    public void tab(View view){
        switch(view.getId()){
            case R.id.message_layout:
                tv_message.setSelected(true);
                iv_message.setSelected(true);
                tv_contact.setSelected(false);
                iv_contact.setSelected(false);
                tv_self.setSelected(false);
                iv_self.setSelected(false);
                fragmentManager = getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.show(chatListFragment);
                transaction.hide(contactFragment);
                transaction.hide(selfFragment);
                transaction.commit();
                break;

            case R.id.contacts_layout:
                tv_contact.setSelected(true);
                iv_contact.setSelected(true);
                tv_message.setSelected(false);
                iv_message.setSelected(false);
                tv_self.setSelected(false);
                iv_self.setSelected(false);
                fragmentManager = getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.hide(chatListFragment);
                transaction.hide(selfFragment);
                transaction.show(contactFragment);
                transaction.commit();
                contactFragment.onStart();
                break;
            case R.id.self_layout:
                tv_self.setSelected(true);
                iv_self.setSelected(true);
                tv_contact.setSelected(false);
                iv_contact.setSelected(false);
                tv_message.setSelected(false);
                iv_message.setSelected(false);
                fragmentManager = getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.hide(chatListFragment);
                transaction.hide(contactFragment);
                transaction.show(selfFragment);
                transaction.commit();
                break;
        }


    }

    private void initialize() {
        messageLayout = findViewById(R.id.message_layout);
        contactsLayout = findViewById(R.id.contacts_layout);
        tv_message = (TextView) findViewById(R.id.tv_message);
        tv_contact = (TextView) findViewById(R.id.tv_contact);
        tv_self = (TextView) findViewById(R.id.tv_self);
        iv_message = (ImageView) findViewById(R.id.iv_message);
        iv_contact = (ImageView) findViewById(R.id.iv_contact);
        iv_self = (ImageView) findViewById(R.id.iv_self);
    }
    public void onBackPressed() {

        if (System.currentTimeMillis() - firstPressedTime < 2000) {

            moveTaskToBack(true);
        } else {
            MyToast.show(ChatListActivity.this,"再按一次返回键退出程序");
            firstPressedTime = System.currentTimeMillis();
        }
    }

}