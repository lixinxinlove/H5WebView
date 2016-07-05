package com.lixinxin.h5webview;

import android.graphics.Bitmap;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private Toolbar toolbar;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        initView();
        initData();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        webView = (WebView) findViewById(R.id.webView);
        tv = (TextView) findViewById(R.id.tv);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowFileAccess(false);
        webSettings.setUseWideViewPort(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDatabaseEnabled(false);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBlockNetworkImage(false);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
                // return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }


        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                tv.setText("加载进度--" + newProgress);

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                toolbar.setTitle(title);
            }
        });
    }

    private void initData() {
        webView.loadUrl("https://www.baidu.com");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.removeAllViews();
        webView.destroy();
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }

    }
}
