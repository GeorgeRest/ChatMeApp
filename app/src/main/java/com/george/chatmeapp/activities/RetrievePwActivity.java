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

public class RetrievePwActivity extends AppCompatActivity {

    private EditText retrieveId;
    private Button retrieveConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_pw_layout);
        initialize();
        retrievePw();
        textChange();
    }

    private void initialize() {
        retrieveId = (EditText) findViewById(R.id.et_retrieve_id);
        retrieveConfirm = (Button) findViewById(R.id.bt_retrieve_confirm);
    }

    private void retrievePw() {
        retrieveConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = retrieveId.getText().toString().trim();
                List<UserInfo> userInfoList = LitePal.findAll(UserInfo.class);
                boolean found = false;
                for (UserInfo userInfo : userInfoList) {
                    if (id.equals(userInfo.getUserName()) || id.equals(userInfo.getEmail())) {
                        found = true;
                        MyToast.show(RetrievePwActivity.this, userInfo.getPw());
                        return;
                    }
                }
                if (!found) {
                    MyToast.show(RetrievePwActivity.this, "未查询到该账号或邮箱");
                }
            }
        });
    }

    private void textChange() {
        TextChange textChange = new TextChange(retrieveId, 1, retrieveConfirm);
        retrieveId.addTextChangedListener(textChange);

    }

    public void back(View view) {
        finish();
    }
}