package com.george.chatmeapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.george.chatmeapp.R;
import com.george.chatmeapp.beans.UserInfo;
import com.george.chatmeapp.utils.MyToast;
import com.george.chatmeapp.utils.TextChange;

import org.litepal.LitePal;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerUser;
    private EditText registerEmail;
    private EditText registerPw;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        initialize();
        textChange();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = registerUser.getText().toString().trim();
                String email = registerEmail.getText().toString().trim();
                String pw = registerPw.getText().toString().trim();

                LitePal.getDatabase();
                if (!isEmailValid(email)) {
                    MyToast.show(RegisterActivity.this, "请输入正确的邮箱格式");
                    return;
                }
                //重复注册
                List<UserInfo> userInfoList = LitePal.findAll(UserInfo.class);
                for (UserInfo userInfo : userInfoList) {
                    if (userId.equals(userInfo.getUserName())) {
                        MyToast.show(RegisterActivity.this, "该用户已注册，请登录");
                        return;
                    }
                }

                UserInfo userInfo = new UserInfo();
                userInfo.setUserName(userId);
                userInfo.setPw(pw);
                userInfo.setEmail(email);
                userInfo.save();
                finish();
                MyToast.show(RegisterActivity.this, "注册成功");
            }
        });

    }

    private void initialize() {
        registerUser = (EditText) findViewById(R.id.et_register_user);
        registerEmail = (EditText) findViewById(R.id.et_register_email);
        registerPw = (EditText) findViewById(R.id.et_register_pw);
        register = (Button) findViewById(R.id.bt_register_layout_register);
    }


    private  boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,5}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void textChange() {
        TextChange textChange = new TextChange(registerUser, registerPw, registerEmail, 3, register);
        registerUser.addTextChangedListener(textChange);
        registerPw.addTextChangedListener(textChange);
        registerEmail.addTextChangedListener(textChange);

    }

}