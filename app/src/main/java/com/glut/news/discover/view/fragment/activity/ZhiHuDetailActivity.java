package com.glut.news.discover.view.fragment.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import com.glut.news.AppApplication;
import com.glut.news.R;
import com.glut.news.common.utils.HttpUtil;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.discover.model.entity.ZhiHuDetailModel;
import com.mingle.widget.LoadingView;

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
    private LoadingView loadingView;


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
        AppApplication.getInstance().addActivity(this);
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
                finish();
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
        loadingView=findViewById(R.id.loadView);
        c = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        image = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
        author = (TextView) findViewById(R.id.author);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nested= (NestedScrollView) findViewById(R.id.nested_view);
        mWebView= (WebView) findViewById(R.id.dicover_webview);
        setSupportActionBar(toolbar);
        actionBar =getSupportActionBar();
        //动态改变Toolbar返回按钮颜色：改为黑色
        Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        Drawable upArrow2 = getResources().getDrawable(R.drawable.ic_share);

        upArrow.setColorFilter(getResources().getColor(R.color.tab_color3), PorterDuff.Mode.SRC_ATOP);
        upArrow2.setColorFilter(getResources().getColor(R.color.tab_color3), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);


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


    }



    public void loadDetailData(String id) {

        RetrofitManager.builder(RetrofitService.BASE_ZHIHU_URL,"ZhuHuService").getZhiHuDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .map(new Func1<ZhiHuDetailModel, ZhiHuDetailModel>() {
                    @Override
                    public ZhiHuDetailModel call(ZhiHuDetailModel zhiHuDetailModel) {
                        return zhiHuDetailModel;
                    }
                })
                .subscribe(new Action1<ZhiHuDetailModel>() {
                    @Override
                    public void call(ZhiHuDetailModel zhiHuDetailModel) {

                        if (zhiHuDetailModel ==null){

                        }else{
                            loadingView.setVisibility(View.GONE);
                        Glide.with(ZhiHuDetailActivity.this).load(zhiHuDetailModel.getImage()).into(image);
                        title.setText(zhiHuDetailModel.getTitle());
                        author.setText("图片来源："+ zhiHuDetailModel.getImage_source());
                        mWebView.setDrawingCacheEnabled(true);
                        share_title= zhiHuDetailModel.getTitle();
                        //c.setTitle(zhiHuDetailModel.getTitle());
                        //actionBar.setTitle(zhiHuDetailModel.getTitle());
                        String htmlData = HttpUtil.createHtmlData(zhiHuDetailModel);
                        mWebView.loadData(htmlData, HttpUtil.MIME_TYPE,HttpUtil.ENCODING);
                        }
                        }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }




}
