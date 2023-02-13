package com.george.chatmeapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.george.chatmeapp.activities.AddActivity;
import com.george.chatmeapp.activities.MainActivity;
import com.george.chatmeapp.R;
import com.george.chatmeapp.utils.DeviceUtils;

public class SelfFragment extends Fragment {

    private TextView exit;
    private TextView self_name;
    private TextView phone;
    private TextView editPhoto;
    private ImageView photo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.self_fragment, null);

        exit = (TextView) view.findViewById(R.id.tv_exit);
        self_name = (TextView) view.findViewById(R.id.tv_self_name);
        phone = (TextView) view.findViewById(R.id.tv_phone);
        editPhoto = (TextView) view.findViewById(R.id.edit_photo);
        photo = (ImageView) view.findViewById(R.id.self_photo);
        phone.setText(DeviceUtils.getPhoneModel());

        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                intent.putExtra("activity", "SelfFragment");
                startActivity(intent);
            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                SharedPreferences isLogin = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = isLogin.edit();
                editor.putBoolean("isLogin", false);
                editor.commit();
                getActivity().finish();

            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        showUserInfo();

    }

    private void showUserInfo() {
        SharedPreferences selfInfo = getActivity().getSharedPreferences("selfInfo", Context.MODE_PRIVATE);
        int selfPhoto = selfInfo.getInt("selfPhoto", R.mipmap.photo1);
        String selfName = selfInfo.getString("selfName", " ");
        self_name.setText(selfName);
        photo.setImageResource(selfPhoto);
    }
}
