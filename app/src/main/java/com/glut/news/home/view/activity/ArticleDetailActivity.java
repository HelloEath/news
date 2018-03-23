package com.glut.news.home.view.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glut.news.R;
import com.glut.news.common.model.adater.ArticleCommentAdater;
import com.glut.news.common.utils.DateUtil;
import com.glut.news.common.utils.SpUtil;
import com.glut.news.home.presenter.impl.ArticleDetailPresenterImpl;
import com.glut.news.my.model.entity.History;
import com.glut.news.my.model.entity.Star;
import com.glut.news.video.model.entity.VideoCommentListModel;
import com.glut.news.video.model.entity.VideoCommentsModel;
import com.mingle.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;


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
    private ArticleDetailPresenterImpl articleDetailPresenter=new ArticleDetailPresenterImpl(this);

    String  ids;
    private int nextPage;
    private final static String TAG="ArticleDetailActivity";
    private LoadingView loadView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        //String id=getIntent().getStringExtra("id");
        int id=getIntent().getIntExtra("id",0);
        ids=id+"";

        recoreHistory();
        String url="http://192.168.191.1:8085/News/article/detailArticle?article_Id="+id;
        initView();
        initWebView(url);
        loadData();

    }

    private void loadData() {
       loadCommentData();//加载评论数据
        recoreHistory();//记录历史数据

    }


    private void star(){
        Star star=new Star();
        star.setStar_ContentId(id);
        star.setStar_UserId(Integer.parseInt(SpUtil.getUserFromSp("UserId")));
        star.setStar_Type(1);
        star.setStar_Time(DateUtil.formatDate_getCurrentDate());
        articleDetailPresenter.star(star);//

    }
    private void recoreHistory() {
        History h=new History();
        h.setHistory_Article(Integer.parseInt(ids));
        h.setHistory_Persion(Integer.parseInt(SpUtil.getUserFromSp("UserId")));
        h.setHistory_Type(1);
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
                loadView.setVisibility(View.VISIBLE);
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

        //mNestRefresh.setOnRefreshListener(this);


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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sendcomment:

                //点击发表后收起虚拟键盘
                InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mCommentValue.getWindowToken(), 0);
                Toast.makeText(this,mCommentValue.getText(),Toast.LENGTH_SHORT).show();

                VideoCommentListModel videoCommentListModel =new VideoCommentListModel();
                //videoCommentListModel.setAuthor_logo(R.drawable.logo);
                String userName= SpUtil.getUserFromSp("userName");
                String userLogo=SpUtil.getUserFromSp("userLogo");
                videoCommentListModel.setAuthor_name("地球人");
                videoCommentListModel.setComment_Content(mCommentValue.getText()+"");

                videoCommentListModel.setComment_Time(DateUtil.formatDate_getCurrentDate());
                videoCommentListModel.setLikes(0);
                commentAdater.addSingGonComments(videoCommentListModel);
                mCommentValue.setText("");
                break;
        }
    }

    private void loadMoreCommentData(){
        articleDetailPresenter.loadMoreComment();//加载评论数据

    }
    private void loadCommentData() {
        articleDetailPresenter.loadComment(Integer.parseInt(ids));//加载评论数据

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

        Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStarFail() {
        Toast.makeText(this,"收藏失败",Toast.LENGTH_SHORT).show();
    }
}
