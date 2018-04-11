package com.glut.news.home.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glut.news.AppApplication;
import com.glut.news.R;
import com.glut.news.common.model.adater.ArticleCommentAdater;
import com.glut.news.common.utils.DateUtil;
import com.glut.news.common.utils.NetUtil;
import com.glut.news.common.utils.SpUtil;
import com.glut.news.common.utils.ToastUtil;
import com.glut.news.common.utils.UserUtil;
import com.glut.news.home.presenter.impl.ArticleDetailPresenterImpl;
import com.glut.news.login.view.fragment.LoginActivity;
import com.glut.news.my.model.entity.History;
import com.glut.news.my.model.entity.Star;
import com.glut.news.video.model.entity.VideoCommentListModel;
import com.glut.news.video.model.entity.VideoCommentsModel;
import com.mingle.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import static android.view.KeyEvent.KEYCODE_BACK;


/**
 * Created by yy on 2018/1/24.
 */
public class ArticleDetailActivity extends AppCompatActivity implements View.OnClickListener,ArticleDetailView{
    private TextView title;
    private TextView author;
    private TextView time;
    private WebView mWebView;
    private RecyclerView mComment;
    private ArticleCommentAdater commentAdater;
    private List<VideoCommentListModel> videoCommentListModels;

    private RelativeLayout btn_sendComment;
    private EditText mCommentValue;
    private Toolbar toolbar;
    private String titleString;
    private RelativeLayout relativeLayout;
    private ArticleDetailPresenterImpl articleDetailPresenter=new ArticleDetailPresenterImpl(this);

   private int  articleId;
    private int nextPage;
    private String contentType;
    private final static String TAG="ArticleDetailActivity";
    private LoadingView loadView;
    private String userId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        //String id=getIntent().getStringExtra("id");

        initView();
        initData();

        loadData();
        AppApplication.getInstance().addActivity(this);

    }

    private void initData() {
        articleId=getIntent().getIntExtra("id",0);
        contentType=getIntent().getStringExtra("ContentType");




    }

    private void loadData() {

        if (NetUtil.isNetworkConnected()){
            String url="http://192.168.191.1:8085/News/article/detailArticle?Article_Id="+articleId;
            initWebView(url);
            if (UserUtil.isUserLogin()) {
                userId=SpUtil.getUserFromSp("UserId");
                recoreHistory();//记录历史数据
            }

            loadCommentData();//加载评论数据
        }else {
            Toast.makeText(ArticleDetailActivity.this,"网络已走失",Toast.LENGTH_SHORT).show();
        }



    }


    private void star(){
        Star star=new Star();
        star.setStar_ContentId(articleId);
        star.setStar_UserId(Integer.parseInt(userId));
        star.setStar_Type(1);
        star.setContent_type(contentType);
        star.setStar_Time(DateUtil.formatDate_getCurrentDate());
        articleDetailPresenter.star(star);//

    }
    private void recoreHistory() {
        History h=new History();
        h.setHistory_Article(articleId);
        h.setHistory_Persion(Integer.parseInt(userId));
        h.setHistory_Type(1);
        h.setContent_type(contentType);
        h.setHistory_Time(DateUtil.formatDate_getCurrentDate());
      articleDetailPresenter.onHistory(h);
    }


    private void initWebView(String url) {

        //addJs(mWebView);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
       /* mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setVerticalScrollbarOverlay(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setHorizontalScrollbarOverlay(false);*/
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.getSettings().setDomStorageEnabled(true);


        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //loadView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadView.setVisibility(View.GONE);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });

        mWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                titleString=title;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void addJs(WebView content) {
        class JsObject{
             @JavascriptInterface
            public void jsFunctioning(final  String i){
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         Log.i(TAG,"run"+i);
                     }
                 });
             }
        }
        content.addJavascriptInterface(new JsObject(),"jscontrolimg");
    }

    private void initView() {
        relativeLayout=findViewById(R.id.relativeLayout);
        toolbar=findViewById(R.id.toolbar);
        loadView= (LoadingView) findViewById(R.id.loadView);
        title= (TextView) findViewById(R.id.title);
        author= (TextView) findViewById(R.id.author);
        time= (TextView) findViewById(R.id.time);
        mWebView= (WebView) findViewById(R.id.content);
        //mComment= (RecyclerView) findViewById(R.id.articleComment);
        //mNestRefresh=findViewById(R.id.nested_view);
        btn_sendComment= (RelativeLayout) findViewById(R.id.btn_sendcomment);
        mCommentValue= (EditText) findViewById(R.id.comment_value);
        videoCommentListModels =new ArrayList<>();
        commentAdater=new ArticleCommentAdater(this, videoCommentListModels);

        setSupportActionBar(toolbar);
        //mNestRefresh.setOnRefreshListener(this);
//动态改变Toolbar返回按钮颜色：改为灰色
        Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        Drawable upArrow2 = getResources().getDrawable(R.drawable.ic_share);

        upArrow.setColorFilter(getResources().getColor(R.color.tab_color3), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        upArrow2.setColorFilter(getResources().getColor(R.color.tab_color3), PorterDuff.Mode.SRC_ATOP);

        toolbar.setTitle("");
        ActionBar a=getSupportActionBar();
        if (a!=null){
            a.setDisplayHomeAsUpEnabled(true);

            a.setTitle("");
        }

        btn_sendComment.setOnClickListener(this);

        LinearLayoutManager l=new LinearLayoutManager(this);
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        l.setOrientation(OrientationHelper.VERTICAL);

       // mComment.setLayoutManager(l);

        View FooterView= LayoutInflater.from(this).inflate(R.layout.item_video_detailfoot,mComment,false);
        commentAdater.addFootView(FooterView);
        commentAdater.addHeadView(FooterView);

       // mComment.setAdapter(commentAdater);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_action_share:
                if (NetUtil.isNetworkConnected()){
                    share();
                }else {
                   Toast.makeText(ArticleDetailActivity.this,"网络已走失",Toast.LENGTH_SHORT).show();
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_from_jikezhiyue) +"["+ titleString +"]:"+ mWebView.getUrl());
        startActivity(Intent.createChooser(intent, titleString));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_share, menu);
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sendcomment:

                if (NetUtil.isNetworkConnected()){
                    sendComment();
                }else {
                    Toast.makeText(ArticleDetailActivity.this,"网络已走失",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    public void sendComment(){

        if (UserUtil.isUserLogin()){

            //点击发表后收起虚拟键盘
            InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mCommentValue.getWindowToken(), 0);
            Toast.makeText(this,mCommentValue.getText(),Toast.LENGTH_SHORT).show();

            VideoCommentListModel videoCommentListModel =new VideoCommentListModel();
            //videoCommentListModel.setAuthor_logo(R.drawable.logo);
            String userName= SpUtil.getUserFromSp("userName");
            String userLogo=SpUtil.getUserFromSp("userLogo");
            videoCommentListModel.setAuthor_name(userName);
            videoCommentListModel.setComment_Content(mCommentValue.getText()+"");
            videoCommentListModel.setAuthor_logo(userLogo);
            videoCommentListModel.setComment_Time(DateUtil.formatDate_getCurrentDate());
            videoCommentListModel.setLikes(0);
            commentAdater.addSingGonComments(videoCommentListModel);
            mCommentValue.setText("");
        }else {

            Snackbar s= Snackbar.make(relativeLayout,"登录获得评论技能",Snackbar.LENGTH_LONG).setAction("点我立即登录", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ArticleDetailActivity.this, LoginActivity.class));

                }
            });
            View sv=s.getView();
//文字的颜色
            ((TextView) sv.findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.side_1));
            sv.setBackgroundColor(0xffffffff);
            s.show();


        }

    }
    private void loadMoreCommentData(){
        articleDetailPresenter.loadMoreComment();//加载评论数据

    }
    private void loadCommentData() {
        articleDetailPresenter.loadComment(articleId);//加载评论数据

    }




    @Override
    public void addAdater(VideoCommentsModel commonData) {
        commentAdater.addComments(commonData.getData());
    }

    @Override
    public void changeAdater(VideoCommentsModel commonData) {
        commentAdater.changeData(commonData.getData());
    }

    @Override
    public void onStarSuccess() {

        ToastUtil.showSuccess("收藏成功",3000,this);
    }

    @Override
    public void onStarFail() {
        ToastUtil.showError("收藏失败",3000,this);
    }
}
