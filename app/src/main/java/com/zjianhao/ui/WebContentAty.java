package com.zjianhao.ui;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.zjianhao.base.NavigatorActivity;
import com.zjianhao.universalcontroller.R;

/**
 * Created by 张建浩（Clarence) on 2017-5-3 14:50.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class WebContentAty extends NavigatorActivity {
    WebView mWebview;
    ProgressBar loadProgress;
    private String url;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_content_main);
        url = getIntent().getStringExtra("url");
        initView();
        setNavigatorText("关于作者");
    }


    private void initView() {
        mWebview = (WebView) findViewById(R.id.webview);
        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDefaultTextEncodingName("UTF-8");
        mWebview.setWebChromeClient(new MyWebClient());
        mWebview.setWebViewClient(new WebViewClient());
        mWebview.loadUrl(url);
        loadProgress = (ProgressBar) findViewById(R.id.load_progress);
        loadProgress.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebview.destroy();
    }

    private class MyWebClient extends WebChromeClient {
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress > 80)//进度大于50%，取消ProgressBar显示
                loadProgress.setVisibility(View.GONE);
            super.onProgressChanged(view, newProgress);
        }

    }

    @Override
    public void onBackPressed() {
        if (mWebview.canGoBack())
            mWebview.goBack();
        else
            super.onBackPressed();
    }
}
