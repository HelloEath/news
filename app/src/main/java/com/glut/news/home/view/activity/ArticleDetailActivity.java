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
import com.glut.news.adapter.ArticleCommentAdater;
import com.glut.news.my.model.entity.History;
import com.glut.news.video.model.entity.VideoCommentListModel;
import com.glut.news.video.model.entity.VideoCommentsModel;
import com.glut.news.net.manager.RetrofitManager;
import com.glut.news.net.service.RetrofitService;
import com.glut.news.utils.DateUtil;
import com.glut.news.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;



/**
 * Created by yy on 2018/1/24.
 */
public class ArticleDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView title;
    private TextView author;
    private TextView time;
    private WebView mWebView;
    private RecyclerView mComment;
    private ArticleCommentAdater commentAdater;
    private List<VideoCommentListModel> videoCommentListModels;

    private RelativeLayout btn_sendComment;
    private EditText mCommentValue;

    String  ids;
    private int nextPage;
    private final static String TAG="ArticleDetailActivity";
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
        loadCommentData();

    }


    private void recoreHistory() {
        History h=new History();
        h.setHistory_Article(Integer.parseInt(ids));
        h.setHistory_Persion(Integer.parseInt(SpUtil.getUserFromSp("UserId")));
        h.setHistory_Type(1);

        h.setHistory_Time(DateUtil.formatDate_getCurrentDate());
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "HistoryService").insertHistory(h)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })

                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer comment) {
                        //mPbLoading.setVisibility(View.GONE);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
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
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

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
                commentAdater.addComments(videoCommentListModel);
                mCommentValue.setText("");
                break;
        }
    }

    private void loadMoreCommentData(){
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL,"CommentService").getCOmment(ids,nextPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<VideoCommentsModel, VideoCommentsModel>() {
                    @Override
                    public VideoCommentsModel call(VideoCommentsModel guoKrList) {
                        return guoKrList;
                    }
                })
                .subscribe(new Action1<VideoCommentsModel>() {
                    @Override
                    public void call(VideoCommentsModel comment) {
                        //mPbLoading.setVisibility(View.GONE);
                        if (comment==null){
                            // mTvLoadEmpty.setVisibility(View.VISIBLE);
                        }else{

                            if (comment.getData()!=null)
                                commentAdater.changeData(comment.getData());
                            nextPage=comment.getNextpage();
                            //mTvLoadEmpty.setVisibility(View.GONE);
                        }
                       /* mLoadLatestSnackbar.dismiss();
                        refreshLayout.setRefreshing(false);
                        mTvLoadError.setVisibility(View.GONE);*/
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                       /* mLoadLatestSnackbar.show();
                        refreshLayout.setRefreshing(false);
                        mLoadLatestSnackbar.show();
                        mTvLoadError.setVisibility(View.VISIBLE);
                        mTvLoadEmpty.setVisibility(View.GONE);*/

                    }
                });

    }
    private void loadCommentData() {

        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL,"CommentService").getCOmment("304120449",1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<VideoCommentsModel, VideoCommentsModel>() {
                    @Override
                    public VideoCommentsModel call(VideoCommentsModel guoKrList) {
                        return guoKrList;
                    }
                })
                .subscribe(new Action1<VideoCommentsModel>() {
                    @Override
                    public void call(VideoCommentsModel comment) {
                        //mPbLoading.setVisibility(View.GONE);
                        if (comment==null){
                            // mTvLoadEmpty.setVisibility(View.VISIBLE);
                        }else{

                            if (comment.getData()!=null)
                                commentAdater.changeData(comment.getData());
                            nextPage=comment.getNextpage();
                            //mTvLoadEmpty.setVisibility(View.GONE);
                        }
                       /* mLoadLatestSnackbar.dismiss();
                        refreshLayout.setRefreshing(false);
                        mTvLoadError.setVisibility(View.GONE);*/
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                       /* mLoadLatestSnackbar.show();
                        refreshLayout.setRefreshing(false);
                        mLoadLatestSnackbar.show();
                        mTvLoadError.setVisibility(View.VISIBLE);
                        mTvLoadEmpty.setVisibility(View.GONE);*/

                    }
                });

    }

}
