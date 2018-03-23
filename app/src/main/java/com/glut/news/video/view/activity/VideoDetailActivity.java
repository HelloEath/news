package com.glut.news.video.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.glut.news.BaseActivity;
import com.glut.news.R;
import com.glut.news.common.model.entity.Comment;
import com.glut.news.common.utils.DateUtil;
import com.glut.news.common.utils.SpUtil;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.my.model.entity.History;
import com.glut.news.my.model.entity.Star;
import com.glut.news.video.model.adater.VideoDatailAdater;
import com.glut.news.video.model.entity.VideoCommentListModel;
import com.glut.news.video.model.entity.VideoCommentsModel;
import com.glut.news.video.presenter.VideoPresenterImpl;
import com.ldoublem.thumbUplib.ThumbUpView;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/1/28.
 */
public class VideoDetailActivity extends BaseActivity implements View.OnClickListener,IVideoDetailView{
    private RecyclerView mComments;
    private NestedScrollView mNestRefresh;
    private VideoDatailAdater vda;
    private JZVideoPlayerStandard j;
    private List<VideoCommentListModel> clist;
    private RelativeLayout btn_sendComment;
    private EditText mCommentValue;
    private Boolean isloading=false;
    private Boolean isLastPage=false;
    private Boolean isComentSuccess=false;
    private ImageView btn_star;

    String  id;
    private int nextPage;
    private ThumbUpView thumbUpView;
    private VideoPresenterImpl videoPresenter=new VideoPresenterImpl(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        initView();
        initData();
        loadData();

    }

    private void loadData() {
        loadCommentData();//加载评论数据

    }

    private void recoreHistory() {
        History h=new History();
        h.setHistory_Article(Integer.parseInt(id));
        String x=SpUtil.getUserFromSp("UserId");
        h.setHistory_Persion(Integer.parseInt(x));
        h.setHistory_Type(2);

        h.setHistory_Time(DateUtil.formatDate_getCurrentDate());
        videoPresenter.recordHistory(h);//记录浏览历史
    }

    private void initData() {
        thumbUpView.setLikeType(ThumbUpView.LikeType.broken);

        thumbUpView.setOnThumbUp(new ThumbUpView.OnThumbUp() {
            @Override
            public void like(boolean like) {
                if (like){

                    recordStar();
                    Toast.makeText(VideoDetailActivity.this,"ddd",Toast.LENGTH_SHORT).show();
                }

            }


        });

         id=getIntent().getStringExtra("id");
        String player=getIntent().getStringExtra("player");
        String abstracts=getIntent().getStringExtra("abstract");
        j.setUp(player,JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,"");

        //Glide.with(this).load(R.drawable.btn_star).into(btn_star);
        recoreHistory();//记录到历史
        videoPresenter.updatePlays();//更新播放量
    }

    private void loadMoreCommentData(){
        videoPresenter.loadMoreComment();


    }
    private void loadCommentData() {

        videoPresenter.loadComment(Integer.parseInt(id));

    }

    private void initView() {
        thumbUpView=findViewById(R.id.btn_star);
        mComments=findViewById(R.id.recycler_comment);
        mNestRefresh=findViewById(R.id.nested_view);
        btn_sendComment=findViewById(R.id.btn_sendcomment);
        mCommentValue=findViewById(R.id.comment_value);
        j=findViewById(R.id.custom_videoplayer_standard);
       // btn_star=findViewById(R.id.btn_star);
        clist=new ArrayList<>();
        LinearLayoutManager l = new LinearLayoutManager(this);
        l.setOrientation(OrientationHelper.VERTICAL);
        //mNestRefresh.setOnRefreshListener(this);
        vda=new VideoDatailAdater(this,clist);
        mComments.setLayoutManager(l);
        View FooterView= LayoutInflater.from(this).inflate(R.layout.item_video_detailfoot,mComments,false);
        vda.addFootView(FooterView);
        vda.addHeadView(FooterView);
        mComments.setAdapter(vda);
        btn_sendComment.setOnClickListener(this);
       // btn_star.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sendcomment:

                SendComment();

                break;

        }

    }


    private void  recordStar(){
        Star s=new Star();
        String c=SpUtil.getUserFromSp("UserId");
        s.setStar_UserId(Integer.parseInt(c));
        s.setStar_ContentId(Integer.parseInt(id));
        s.setStar_Time(DateUtil.formatDate_getCurrentDate());
        s.setStar_Type(2);
        videoPresenter.recordStar(s);

    }
    private void sendSuccess(Comment c){

        Toast.makeText(this,"发送成功",Toast.LENGTH_SHORT).show();
        VideoCommentListModel videoCommentListModel =new VideoCommentListModel();

        isComentSuccess=false;
        videoCommentListModel.setAuthor_logo(R.drawable.logo+"");
        videoCommentListModel.setAuthor_name("地球人");
        videoCommentListModel.setComment_Content(c.getComment_Content());

        videoCommentListModel.setComment_Time(c.getComment_Time());
        videoCommentListModel.setLikes(0);
        vda.addComments(videoCommentListModel);
        mComments.scrollToPosition(0);

        mCommentValue.setText("");
        View vv=  mComments.getChildAt(0);

        LinearLayout linearLayout= (LinearLayout) vv;
        final LinearLayout linearLayout1= linearLayout.findViewById(R.id.delete);
        final TextView textView=new TextView(this);

        textView.setText("删除");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String UserId=SpUtil.getUserFromSp("UserId");
                String ArticleId=SpUtil.getUserFromSp("ArticleId");
                DeleteComment(ArticleId,UserId,linearLayout1,textView);



            }
        });
        linearLayout1.addView(textView);
    }
    private void SendComment() {

//点击发表后收起虚拟键盘
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mCommentValue.getWindowToken(), 0);
        //Toast.makeText(this,mCommentValue.getText(),Toast.LENGTH_SHORT).show();
        //videoCommentListModel.setAuthor_logo(R.drawable.logo);
        String userName= SpUtil.getUserFromSp("userName");
        String userLogo=SpUtil.getUserFromSp("userLogo");
        String comment=mCommentValue.getText()+"";
        String time=DateUtil.formatDate_getCurrentDate();
        Comment c=new Comment();
        //c.setAuthor_logo();
        c.setAuthor_name("地球人");
        c.setComment_Content(comment);
        c.setComment_Likes(0);
        c.setComment_Time(time);
        c.setComment_Author(Integer.parseInt(SpUtil.getUserFromSp("UserId")));
        c.setComment_Article(Integer.parseInt(SpUtil.getUserFromSp("ArticleId")));
        // c.setAuthor_logo();
        videoPresenter.sendComment(c);

    }


    private boolean DeleteComment(String  ArticleId,String UserId,final LinearLayout linearLayout,final TextView t) {
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "CommentService").deleteComment(ArticleId,UserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //mPbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .map(new Func1<Comment, Comment>() {
                    @Override
                    public Comment call(Comment guoKrList) {
                        return guoKrList;
                    }
                })
                .subscribe(new Action1<Comment>() {
                    @Override
                    public void call(Comment comment) {
                        //mPbLoading.setVisibility(View.GONE);
                        if (comment == null) {
                            // mTvLoadEmpty.setVisibility(View.VISIBLE);
                        } else {

                            if ("ok".equals(comment.getStus()) ){


                                vda.removeComments();
                                linearLayout.removeView(t);
                                Toast.makeText(VideoDetailActivity.this,"删除成功",Toast.LENGTH_SHORT).show();


                            }else {
                                Toast.makeText(VideoDetailActivity.this,"删除失败",Toast.LENGTH_SHORT).show();

                            }
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

        if (isComentSuccess){

            return true;
        }else {
            return false;
        }

    }

    @Override
    public void addCommentAdater(VideoCommentsModel v) {
        vda.addData(v.getData());
    }

    @Override
    public void changeCommentAdater(VideoCommentsModel v) {
        vda.changeData(v.getData());
    }

    @Override
    public void onSendCommentSuccess(Comment c) {
        sendSuccess(c);
        videoPresenter.updateComments();//视频评论数加一
        Toast.makeText(this,"发送成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendCommentFail() {
        Toast.makeText(this,"发送失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initListenter() {
        //上拉加载更多
        mComments.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                //获取所有item数目
                int totalItemCount = layoutManager.getItemCount();

                //获取最后一个item位置
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();


                if (!isloading && totalItemCount < (lastVisibleItem + 2)) {
                    isloading=true;
                    loadMoreCommentData();


                }

            }
        });
    }

    @Override
    public void onStarSuccess() {
        Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
        Glide.with(this).load(R.drawable.btn_isstar).into(btn_star);

    }

    @Override
    public void onStarFali() {
        Toast.makeText(this,"收藏失败",Toast.LENGTH_SHORT).show();

    }
}