package com.lixinxin.h5webview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View v) {
        startActivity(new Intent(this, WebActivity.class));
    }
    public void onClickU(View v) {
        startActivity(new Intent(this, UpdataActivity.class));
    }
}
