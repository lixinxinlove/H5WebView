package com.lixinxin.h5webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UpdataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata);
        new UpdateManager(this).checkUpdate(true);
    }
}
