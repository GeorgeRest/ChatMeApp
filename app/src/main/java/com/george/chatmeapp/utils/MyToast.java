package com.george.chatmeapp.utils;

import android.content.Context;
import android.widget.Toast;

public class MyToast {

    public static void show(Context context, CharSequence content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}
