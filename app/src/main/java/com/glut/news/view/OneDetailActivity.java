package com.glut.news.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.widget.TextView;

import com.glut.news.R;
import com.glut.news.adapter.OneDetailAdater;
import com.glut.news.entity.Comments;
import com.glut.news.entity.OneDetail;
import com.glut.news.net.manager.RetrofitManager;
import com.glut.news.net.service.RetrofitService;
import com.glut.news.utils.HttpUtil;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/2/9.
 */
public class OneDetailActivity  extends AppCompatActivity {

    private String id;
    private TextView mTitle;
    private TextView mAuthor_name;
    private WebView mContent;
    private RecyclerView mComment;
    private String author_name;
    private List<Comments.CommentsDataList> commentsList=new ArrayList<>();
    private OneDetailAdater oneDetailAdater;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onedetail);
        id=getIntent().getStringExtra("id");
        author_name=getIntent().getStringExtra("author");
        initView();
        loadArticleData();
        loadCommentData();

    }

    private void loadCommentData() {

        RetrofitManager.builder(RetrofitService.ONE_MOMENT_BASE,"OneService").getOneComment(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .map(new Func1<Comments, Comments>() {
                    @Override
                    public Comments call(Comments comments) {
                        return comments;
                    }
                })
                .subscribe(new Action1<Comments>() {
                    @Override
                    public void call(Comments comments) {

                        if (comments==null){

                        }
                        oneDetailAdater.changeData(comments.getData().getData());

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void loadArticleData() {
        RetrofitManager.builder(RetrofitService.ONE_MOMENT_BASE,"OneService").getOneDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .map(new Func1<OneDetail, OneDetail>() {
                    @Override
                    public OneDetail call(OneDetail oneDetail) {
                        return oneDetail;
                    }
                })
                .subscribe(new Action1<OneDetail>() {
                    @Override
                    public void call(OneDetail oneDetail) {
                        mTitle.setText(oneDetail.getData().getHp_title());
                        String htmlData = HttpUtil.create2(oneDetail.getData().getHp_content().toString());
                        mContent.loadData(htmlData, HttpUtil.MIME_TYPE,HttpUtil.ENCODING);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    private void initView() {
        mAuthor_name= (TextView) findViewById(R.id.author_name);
        mTitle= (TextView) findViewById(R.id.title);
        mComment= (RecyclerView) findViewById(R.id.comments);
        mContent= (WebView) findViewById(R.id.content);
        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        //解决滑动卡顿问题
        mAuthor_name.setText(author_name);
        mComment.setNestedScrollingEnabled(false);

        mComment.setLayoutManager(lm);
        oneDetailAdater=new OneDetailAdater(this,commentsList);
        mComment.setAdapter(oneDetailAdater);
        //设置自适应屏幕，两者合用
      /* mContent.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        mContent.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小*/

        oneDetailAdater.addFooterView(LayoutInflater.from(this).inflate(R.layout.item_dicover_onedetail_footer,null));
        oneDetailAdater.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_dicover_onedetail_header,null));
        mComment.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


    }

}
