package com.lnyp.pswkeyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.moziqi.pwd.widget.VerificationCodeView;

public class MsgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        final VerificationCodeView verificationCodeView = findViewById(R.id.verificationcodeview);
        verificationCodeView.setOnCodeFinishListener(new VerificationCodeView.OnCodeFinishListener() {
            @Override
            public void onComplete(String content) {

            }
        });
        findViewById(R.id.tv_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationCodeView.clearText();
            }
        });
    }
}
