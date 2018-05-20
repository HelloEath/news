package com.glut.news.home.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glut.news.AppApplication;
import com.glut.news.R;
import com.glut.news.common.model.adater.ArticleCommentAdater;
import com.glut.news.common.model.entity.Comment;
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
import com.ldoublem.thumbUplib.ThumbUpView;
import com.mingle.widget.LoadingView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
    private LinearLayout mLayout;
    private SmartRefreshLayout mRefreshLayout;
    private ThumbUpView thumbUpView;
    private Comment c;
    private  LinearLayout linearLayout;
    private TextView textView;
    private LinearLayout linearLayout1;
    private NestedScrollView mNestedScrollView;
    private LinearLayout mLinearLayout_sendComment;
    private boolean isHaveNextPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        initView();
        initData();
        loadData();
        AppApplication.getInstance().addActivity(this);

    }

    private void initData() {
        articleId=Integer.parseInt(getIntent().getStringExtra("id"));
        contentType=getIntent().getStringExtra("ContentType");
        thumbUpView.setOnThumbUp(new ThumbUpView.OnThumbUp() {
            @Override
            public void like(boolean like) {
                if (like){
                    if (UserUtil.isUserLogin()){
                        star();

                    }else {
                        Snackbar s= Snackbar.make(relativeLayout,"登录才有的特权",Snackbar.LENGTH_LONG).setAction("点我立即登录", new View.OnClickListener() {
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

            }


        });



    }

    private void loadData() {

        if (NetUtil.isNetworkConnected()){
            //String url="http://47.100.243.11/NewsServerApi/article/detailArticle?Article_Id="+articleId;
            String url="http://47.100.243.11/NewsServerApi/article/detailArticle?Article_Id="+articleId;

            initWebView(url);

        }else {
            Toast.makeText(ArticleDetailActivity.this,"网络已走失...",Toast.LENGTH_SHORT).show();
        }



    }


    private void star(){
        userId=SpUtil.getUserFromSp("UserId");
        Star star=new Star();
        star.setStar_ContentId(articleId);
        star.setStar_UserId(Integer.parseInt(userId));
        star.setStar_Type(1);
        star.setContent_type(contentType);
        star.setStar_Time(DateUtil.formatDate_getCurrentDate());
        articleDetailPresenter.star(star);//

    }

    private void recordHistory(){
        userId=SpUtil.getUserFromSp("UserId");
        History history=new History();
        history.setContent_type(contentType);
        history.setHistory_Persion(Integer.parseInt(userId));
        history.setHistory_Article(articleId);
        history.setHistory_Type(1);
        history.setHistory_Time(DateUtil.formatDate_getCurrentDateByF("yyyy-MM-dd"));
        articleDetailPresenter.onHistory(history);

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
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setDomStorageEnabled(false);


        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadView.setVisibility(View.GONE);
                loadCommentData("fp");//加载评论数据
                mNestedScrollView.setVisibility(View.VISIBLE);
                mLinearLayout_sendComment.setVisibility(View.VISIBLE);
                if (UserUtil.isUserLogin()){
                    recordHistory();//记载到历史
                }

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
        thumbUpView=findViewById(R.id.btn_star);
        mLinearLayout_sendComment=findViewById(R.id.sendcomment_lay);
        mRefreshLayout=findViewById(R.id.refreshLayout);
        mLayout=findViewById(R.id.comment_lay);
        relativeLayout=findViewById(R.id.relativeLayout);
        toolbar=findViewById(R.id.toolbar);
        loadView= (LoadingView) findViewById(R.id.loadView);
        title= (TextView) findViewById(R.id.title);
        author= (TextView) findViewById(R.id.author);
        time= (TextView) findViewById(R.id.time);
        mWebView= (WebView) findViewById(R.id.content);
        mComment= (RecyclerView) findViewById(R.id.oneCommentsModel);
        mNestedScrollView=findViewById(R.id.nested_view);
        btn_sendComment= (RelativeLayout) findViewById(R.id.btn_sendcomment);
        mCommentValue= (EditText) findViewById(R.id.comment_value);
        videoCommentListModels =new ArrayList<>();
        commentAdater=new ArticleCommentAdater(this, videoCommentListModels);
        mComment.setNestedScrollingEnabled(false);

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
        l.setOrientation(LinearLayoutManager.VERTICAL);
        l.setOrientation(OrientationHelper.VERTICAL);

        mComment.setLayoutManager(l);
        mComment.setAdapter(commentAdater);
        //加载评论
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
        //加载更多评论
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadCommentData("null");
            }
        });

        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    // Log.i(TAG, "Scroll DOWN");
                }
                if (scrollY < oldScrollY) {
                    //Log.i(TAG, "Scroll UP");
                }

                if (scrollY == 0) {
                    //Log.i(TAG, "TOP SCROLL");//到顶
                    loadData();

                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    //Log.i(TAG, "BOTTOM SCROLL");//到底
                    if (!isHaveNextPage){
                        mRefreshLayout.autoLoadMore();
                    }


                }
            }
        });

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
            //videoCommentListModel.setAuthor_logo(R.drawable.logo);
            String userName= SpUtil.getUserFromSp("UserName");
            String userLogo=SpUtil.getUserFromSp("UserLogo");
            c=new Comment();
            c.setAuthor_logo(userLogo);
            c.setAuthor_name(userName);
            c.setComment_Content(mCommentValue.getText()+"");
            c.setComment_Article(articleId);
            c.setComment_Likes(0);
            c.setComment_Time(DateUtil.formatDate_getCurrentDate());
            articleDetailPresenter.sendComment(c);
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

    private void loadCommentData(String fp) {
        articleDetailPresenter.loadComment(articleId,fp);//加载评论数据


    }




    @Override
    public void addAdater(VideoCommentsModel commonData) {
        commentAdater.addComments(commonData.getData());
        mRefreshLayout.finishLoadMore(true);
        mLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void changeAdater(VideoCommentsModel commonData) {
        commentAdater.changeData(commonData.getData());
        mRefreshLayout.finishRefresh(true);
        mRefreshLayout.finishLoadMore();
        mLayout.setVisibility(View.VISIBLE);
        isHaveNextPage=false;

    }

    @Override
    public void onStarSuccess() {

        ToastUtil.showSuccess("收藏成功",3000,this);
    }

    @Override
    public void onStarFail() {
        ToastUtil.showError("收藏失败",3000,this);
    }

    @Override
    public void noMoreData() {
        mRefreshLayout.setNoMoreData(true);
        isHaveNextPage=true;
    }

    @Override
    public void onSendCommentSuccess() {
        sendSuccess();
        articleDetailPresenter.updateComments();//评论数加一
        Toast.makeText(this,"发送成功",Toast.LENGTH_SHORT).show();
    }

    private void sendSuccess() {
        VideoCommentListModel videoCommentListModel =new VideoCommentListModel();
        videoCommentListModel.setAuthor_name(c.getAuthor_name());
        videoCommentListModel.setComment_Content(c.getComment_Content());
        videoCommentListModel.setAuthor_logo(c.getAuthor_logo());
        videoCommentListModel.setComment_Time(DateUtil.formatDate_getCurrentDate());
        videoCommentListModel.setLikes(0);
        commentAdater.addSingGonComments(videoCommentListModel);

        mComment.scrollToPosition(0);

        mCommentValue.setText("");
        View vv=  mComment.getChildAt(0);

        linearLayout= (LinearLayout) vv;
         linearLayout1= linearLayout.findViewById(R.id.delete);
        textView=new TextView(this);

        textView.setText("删除");
        mLayout.setVisibility(View.VISIBLE);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteComment(userId);

            }
        });
        linearLayout1.addView(textView);

    }

    private void DeleteComment( String userId) {
       articleDetailPresenter.deleteComment(userId);


    }

    @Override
    public void onSendCommentFail() {
        Toast.makeText(ArticleDetailActivity.this,"发送失败",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleteCommentSuccess() {
        commentAdater.removeComments();
        linearLayout1.removeView(textView);
        Toast.makeText(ArticleDetailActivity.this,"删除成功",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleteCommentFail() {
        Toast.makeText(ArticleDetailActivity.this,"删除失败",Toast.LENGTH_SHORT).show();

    }
}
