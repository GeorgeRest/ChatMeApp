package com.george.chatmeapp.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.george.chatmeapp.R;

public class TextChange implements TextWatcher {
    private EditText userId;
    private EditText userPw;
    private EditText userEmail;
    private int counts;
    private Button button;

    public TextChange(EditText userId, int counts, Button button) {
        this.userId = userId;
        this.counts = counts;
        this.button = button;
    }

    public TextChange(EditText userId, EditText userPw, EditText userEmail, int counts, Button button) {
        this(userId,counts,button);
        this.userPw = userPw;
        this.userEmail = userEmail;

    }

    public TextChange(EditText userId, EditText userPw, int counts, Button button) {
        this(userId,counts,button);
        this.userPw = userPw;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        switch (counts) {
            case 1:
                String id = userId.getText().toString().trim();
                if (id.length() > 0) {
                    button.setBackgroundResource(R.drawable.retrieve_button);
                    button.setEnabled(true);
                } else {
                    button.setBackgroundResource(R.drawable.button_gray);
                    button.setEnabled(false);
                }
                break;
            case 2:
                String id1 = userId.getText().toString().trim();
                String pw = userPw.getText().toString().trim();
                if (id1.length() > 0 && pw.length() > 0) {
                    button.setBackgroundResource(R.drawable.bg_button_login);
                    button.setEnabled(true);
                } else {
                    button.setBackgroundResource(R.drawable.button_gray);
                    button.setEnabled(false);
                }
                break;
            case 3:
                String id2 = userId.getText().toString().trim();
                String pw1 = userPw.getText().toString().trim();
                String email = userEmail.getText().toString().trim();
                if (id2.length() > 0 && pw1.length() > 0 && email.length() > 0) {
                    button.setBackgroundResource(R.drawable.register_button);
                    button.setEnabled(true);
                } else {
                    button.setBackgroundResource(R.drawable.button_gray);
                    button.setEnabled(false);
                }
                break;

        }
    }
}
