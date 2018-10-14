package main.zm.elieli.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import main.zm.elieli.R;

public class OpenWebActivity extends AppCompatActivity {

    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_openweb);
    }

    @Override
    protected void onStart() {
        super.onStart();

         myWebView = (WebView) findViewById(R.id.id_webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
       // webSettings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        webSettings.setUseWideViewPort(true); // 关键点
      //  webSettings.setAllowFileAccess(true); // 允许访问文件

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
       webSettings.setDomStorageEnabled(true);//DOM Storage
        webSettings.setUserAgentString("User-Agent:Android");//设置用户代理，一般不用

     //   myWebView.setWebViewClient(new MyWebViewClient());
        String webUrl = getIntent().getStringExtra("webUrl");
        // 注册一个JS对象 'WEBAPP'
        // myWebView.addJavascriptInterface(new MyWebApp(), "WEBAPP");
        // 加载首页 (不支持HTTPS)
        // myWebView.loadUrl("http://m.v.qq.com/"); //mobile
        myWebView.loadUrl(webUrl);
    }

    //
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 决定怎么样打开一个新的网址: 开一个新窗口 or 用本窗口打开 or 第三方打开 ..
             myWebView.loadUrl(url);
            return true;
        }
    }


    public class MyWebApp {
        @JavascriptInterface
        public void jsDial(String number) {
            Uri uri = Uri.parse("tel:" + number);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(intent);
        }
    }
}
