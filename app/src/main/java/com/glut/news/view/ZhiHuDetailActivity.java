package com.glut.news.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.glut.news.R;
import com.glut.news.entity.ZhiHuDetail;
import com.glut.news.net.manager.RetrofitManager;
import com.glut.news.net.service.RetrofitService;
import com.glut.news.utils.HttpUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;



/**
 * Created by yy on 2018/2/2.
 */
public class ZhiHuDetailActivity extends AppCompatActivity {
    private CollapsingToolbarLayout c;
    private ImageView image;
    private TextView title;
    private TextView author;
    private Toolbar toolbar;
    private NestedScrollView nested;
    private WebView mWebView;
    ActionBar actionBar;
    private String share_title;
    private String id;
    TextView mTvLoadEmpty;
    TextView mTvLoadError;
    ContentLoadingProgressBar mPbLoading;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicover_detail);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        id= getIntent().getStringExtra("id");
        initView();
        initWebView();
        loadDetailData(id);

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
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_from) + share_title + "，http://daily.zhihu.com/story/" +id);
        startActivity(Intent.createChooser(intent, share_title));
    }



    private void initView() {
        c = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        image = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
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



    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.dicover_webview);
        WebSettings w = mWebView.getSettings();
        w.setJavaScriptCanOpenWindowsAutomatically(true);
        w.setJavaScriptEnabled(true);
        // mWebView.setWebViewClient(new onc);


    }



    public void loadDetailData(String id) {

        RetrofitManager.builder(RetrofitService.BASE_ZHIHU_URL,"ZhuHuService").getZhiHuDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<ZhiHuDetail, ZhiHuDetail>() {
                    @Override
                    public ZhiHuDetail call(ZhiHuDetail zhiHuDetail) {
                        return zhiHuDetail;
                    }
                })
                .subscribe(new Action1<ZhiHuDetail>() {
                    @Override
                    public void call(ZhiHuDetail zhiHuDetail) {

                        mPbLoading.setVisibility(View.GONE);
                        if (zhiHuDetail==null){

                            mTvLoadEmpty.setVisibility(View.VISIBLE);
                        }else{
                        Glide.with(ZhiHuDetailActivity.this).load(zhiHuDetail.getImage()).into(image);
                        title.setText(zhiHuDetail.getTitle());
                        author.setText("图片来源："+zhiHuDetail.getImage_source());
                        mWebView.setDrawingCacheEnabled(true);
                        share_title=zhiHuDetail.getTitle();
                        //c.setTitle(zhiHuDetail.getTitle());
                        //actionBar.setTitle(zhiHuDetail.getTitle());
                        String htmlData = HttpUtil.createHtmlData(zhiHuDetail);
                        mWebView.loadData(htmlData, HttpUtil.MIME_TYPE,HttpUtil.ENCODING);
                            mTvLoadEmpty.setVisibility(View.GONE);

                        }
                        mTvLoadError.setVisibility(View.GONE);
                        }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        mPbLoading.setVisibility(View.GONE);
                        mTvLoadError.setVisibility(View.VISIBLE);
                        mTvLoadEmpty.setVisibility(View.GONE);
                    }
                });

    }




}
