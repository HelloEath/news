package com.glut.news.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.glut.news.R;

/**
 * Created by yy on 2018/2/5.
 */
public class GuoKrDetailActivity extends AppCompatActivity {
    private CollapsingToolbarLayout c;
    private ImageView image;
    private TextView mTitle;
    private TextView author;
    private Toolbar toolbar;
    private NestedScrollView nested;
    private WebView mWebView;
    ActionBar actionBar;
    private String share_title;
    private String url;
    TextView mTvLoadEmpty;
    TextView mTvLoadError;
    ContentLoadingProgressBar mPbLoading;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guokrdetail);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initView();
    }



    //设置WebViewClient类
    //点击返回上一页面而不是退出浏览器

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_share, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_action_share:
                share();
                return true;


        }
        return true;
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_from_guokr) + share_title + url);
        startActivity(Intent.createChooser(intent, share_title));
    }



    private void initView() {
        c = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        image = (ImageView) findViewById(R.id.image);
        mTitle = (TextView) findViewById(R.id.title);
        author = (TextView) findViewById(R.id.author);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nested= (NestedScrollView) findViewById(R.id.nested_view);
        mWebView= (WebView) findViewById(R.id.dicover_webview);
        mTvLoadEmpty= (TextView) findViewById(R.id.tv_load_empty);
        mTvLoadError= (TextView) findViewById(R.id.tv_load_error);
        mPbLoading= (ContentLoadingProgressBar) findViewById(R.id.pb_loading);
        setSupportActionBar(toolbar);
        actionBar =getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            actionBar.setDisplayShowTitleEnabled(false);
        }
        //设置标题颜色
        c.setCollapsedTitleTextColor(Color.WHITE);

         url = getIntent().getStringExtra("url");
        String image_url=getIntent().getStringExtra("image_url");
        String image_from=getIntent().getStringExtra("image_from");
        Glide.with(this).load(image_url).into(image);
        author.setText("图片来源："+image_from);
        mWebView.loadUrl(url);

      /*  if (NetStatusUtil.isConnected(getApplicationContext())) { webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            //根据cache-control决定是否从网络上取数据。
            }
           else { webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载 }
*/
        //优先使用缓存:
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置自适应屏幕，两者合用
       /* mWebView.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        mWebView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小*/
        //步骤3. 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
                mPbLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
               mPbLoading.setVisibility(View.GONE);
                mTvLoadEmpty.setVisibility(View.GONE);
                mTvLoadError.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                mTvLoadError.setVisibility(View.VISIBLE);
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }
        });


        //设置WebChromeClient类
        mWebView.setWebChromeClient(new WebChromeClient() {
                                        //获取网站标题
                                        @Override
                                        public void onReceivedTitle(WebView view, String title) {
                                            System.out.println("标题在这里");
                                            mTitle.setText(title);
                                            share_title=title;
                                        }



//获取加载进度

                                        @Override

                                        public void onProgressChanged (WebView view,int newProgress){
                                            if (newProgress < 100) {
                                                String progress = newProgress + "%";
                                                mTvLoadEmpty.setText(progress);
                                            } else if (newProgress == 100) {
                                                String progress = newProgress + "%";
                                                mTvLoadEmpty.setText(progress);
                                            }
                                        }
                                    }

        );

    }





}
