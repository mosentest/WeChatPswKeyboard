package com.lnyp.pswkeyboard;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
                Log.i("moziqi", "content:" + content);
//                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
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
