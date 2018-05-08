package com.glut.news.video.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.glut.news.AppApplication;
import com.glut.news.BaseActivity;
import com.glut.news.R;
import com.glut.news.common.model.entity.Comment;
import com.glut.news.common.utils.DateUtil;
import com.glut.news.common.utils.NetUtil;
import com.glut.news.common.utils.SpUtil;
import com.glut.news.common.utils.UserUtil;
import com.glut.news.common.view.customview.VideoPlayer;
import com.glut.news.login.view.fragment.LoginActivity;
import com.glut.news.my.model.entity.Star;
import com.glut.news.video.model.adater.RecommentVideoAdater;
import com.glut.news.video.model.adater.VideoDatailAdater;
import com.glut.news.video.model.entity.VideoCommentListModel;
import com.glut.news.video.model.entity.VideoCommentsModel;
import com.glut.news.video.model.entity.VideoModel;
import com.glut.news.video.presenter.impl.VideoDetailActivityPresenterImpl;
import com.ldoublem.thumbUplib.ThumbUpView;
import com.mingle.widget.LoadingView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by yy on 2018/1/28.
 */
public class VideoDetailActivity extends BaseActivity implements View.OnClickListener,IVideoDetailActivityView {
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
    private RelativeLayout relativeLayout;

    private  String  id;
    private String title;

    private ThumbUpView thumbUpView;
    private TextView mLoadError;
    private TextView mLoadEmpty;
    private String contentType;
    private RecyclerView mRecyclerView_recomment;
    private RecommentVideoAdater mRecommentVideoAdater;
    private List<VideoModel.VideoList> mVideoLists;

    private ImageView videoAuthorLogo;
    private TextView videoAuthorName;
    private TextView videoAbstract;
    private LinearLayout mLinearLayout_tuijian;
    private LinearLayout mLinearLayout_comment;
    private TextView mTextView_time;
    private SmartRefreshLayout mRefreshLayout;
    private LoadingView loadView;
    private String UserId;
    private Comment mComment;
    private LinearLayout linearLayout1;
    private TextView textView;
    private  LinearLayout linearLayout;

    private VideoDetailActivityPresenterImpl videoPresenter=new VideoDetailActivityPresenterImpl(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        //隐藏状态栏
        Window window=getWindow();
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        window.setFlags(flag,flag);
        AppApplication.getInstance().addActivity(this);
        initView();
        initData();
        loadData();


    }

    private void loadData() {
        if (NetUtil.isNetworkConnected()){//有网状态
            loadVideoInfoData();//加载视频详情信息
            loadCommentData();//加载评论数据
            loadRecommenData();//加载推荐视频
            thumbUpView.setOnThumbUp(new ThumbUpView.OnThumbUp() {
                @Override
                public void like(boolean like) {
                    if (like){
                        if (UserUtil.isUserLogin()){
                            recordStar();

                        }else {
                            Snackbar s= Snackbar.make(relativeLayout,"登录才有的特权",Snackbar.LENGTH_LONG).setAction("点我立即登录", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(VideoDetailActivity.this, LoginActivity.class));

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



        }else {
            mLoadError.setVisibility(View.VISIBLE);//无网时显示错误界面

        }


    }

    private void loadVideoInfoData() {
        videoPresenter.getVideoDetailInfo(Integer.parseInt(id));
    }


    private void initData() {
        contentType=getIntent().getStringExtra("ContentType");
         id=getIntent().getStringExtra("id");

    }
    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_from_app) + title + "，http://daily.zhihu.com/story/" +id);
        startActivity(Intent.createChooser(intent, title));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_share, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_action_share:
                share();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadCommentData() {

        videoPresenter.loadComment("fp");

    }
    public void loadRecommenData(){

        videoPresenter.loadRecommenData(contentType);
    }

    private void initView() {
        loadView= (LoadingView) findViewById(R.id.loadView);

        mRefreshLayout=findViewById(R.id.refreshLayout);
        mTextView_time=findViewById(R.id.video_time);
        mLinearLayout_comment=findViewById(R.id.comment_lay);
        mLinearLayout_tuijian=findViewById(R.id.tuijian_lay);
        videoAuthorLogo=findViewById(R.id.videoDetail_AuthorLogo);
        videoAuthorName=findViewById(R.id.videoDetail_AuthorName);
        videoAbstract=findViewById(R.id.videoDetail_videoAbstract);
        mRecyclerView_recomment=findViewById(R.id.recycler_Recomment);
        mLoadError=findViewById(R.id.tv_load_error);
        relativeLayout=findViewById(R.id.relativeLayout);
        thumbUpView=findViewById(R.id.btn_star);
        mComments=findViewById(R.id.recycler_comment);
        mNestRefresh=findViewById(R.id.nested_view);
        btn_sendComment=findViewById(R.id.btn_sendcomment);
        mCommentValue=findViewById(R.id.comment_value);
        j=findViewById(R.id.custom_videoplayer_standard);
       // btn_star=findViewById(R.id.btn_star);
        clist=new ArrayList<>();
        mVideoLists=new ArrayList<>();
        LinearLayoutManager l = new LinearLayoutManager(this);
        l.setOrientation(OrientationHelper.VERTICAL);
        vda=new VideoDatailAdater(this,clist);
        mRecommentVideoAdater=new RecommentVideoAdater(this,mVideoLists);
        mComments.setLayoutManager(l);
        l = new LinearLayoutManager(this);
        l.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView_recomment.setNestedScrollingEnabled(false);
        mComments.setNestedScrollingEnabled(false);
        mRecyclerView_recomment.setLayoutManager(l);

        mComments.setAdapter(vda);

        mRecyclerView_recomment.setAdapter(mRecommentVideoAdater);
        btn_sendComment.setOnClickListener(this);
        mRecommentVideoAdater.setItemClickListener(new RecommentVideoAdater.OnItemClickListener() {


            @Override
            public void clickItem(View v, String id, String contentType) {
                Intent i = new Intent(VideoDetailActivity.this, VideoDetailActivity.class);
                i.putExtra("id",id);
                i.putExtra("ContentType",contentType);

                startActivity(i);
                finish();
            }
        });
        //评论加载
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                videoPresenter.loadComment("fp");
            }
        });
        //加载更多评论
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                videoPresenter.loadComment("null");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideoPlayer.releaseAllVideos();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KEYCODE_BACK){
            VideoPlayer.releaseAllVideos();

            finish();

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sendcomment:

                if (UserUtil.isUserLogin()){
                    SendComment();
                }else {
                    Snackbar s= Snackbar.make(relativeLayout,"登录才有的特权",Snackbar.LENGTH_LONG).setAction("点我立即登录", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(VideoDetailActivity.this, LoginActivity.class));

                        }
                    });
                    View sv=s.getView();
//文字的颜色
                    ((TextView) sv.findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.side_1));
                    sv.setBackgroundColor(0xffffffff);
                    s.show();

                }


                break;

        }

    }


    private void  recordStar(){
        UserId=SpUtil.getUserFromSp("UserId");

        Star s=new Star();
        s.setStar_UserId(Integer.parseInt(UserId));
        s.setStar_ContentId(Integer.parseInt(id));
        s.setContent_type(contentType);
        s.setStar_Time(DateUtil.formatDate_getCurrentDate());
        s.setStar_Type(2);
        videoPresenter.recordStar(s);

    }
    private void sendSuccess(){
        VideoCommentListModel videoCommentListModel =new VideoCommentListModel();

        isComentSuccess=false;
        videoCommentListModel.setAuthor_logo(mComment.getAuthor_logo());
        videoCommentListModel.setAuthor_name(mComment.getAuthor_name());
        videoCommentListModel.setComment_Content(mComment.getComment_Content());

        videoCommentListModel.setComment_Time(mComment.getComment_Time());
        videoCommentListModel.setLikes(0);
        vda.addComments(videoCommentListModel);
        mComments.scrollToPosition(0);

        mCommentValue.setText("");
        View vv=  mComments.getChildAt(0);

        linearLayout= (LinearLayout) vv;
        linearLayout1= linearLayout.findViewById(R.id.delete);
        textView=new TextView(this);

        textView.setText("删除");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteComment(UserId);

            }
        });
        linearLayout1.addView(textView);
    }
    private void SendComment() {
        UserId=SpUtil.getUserFromSp("UserId");

//点击发表后收起虚拟键盘
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mCommentValue.getWindowToken(), 0);
        String userName= SpUtil.getUserFromSp("UserName");
        String userLogo=SpUtil.getUserFromSp("UserLogo");
        String comment=mCommentValue.getText()+"";
        String time=DateUtil.formatDate_getCurrentDate();
        mComment=new Comment();
        mComment.setAuthor_logo(userLogo);
        mComment.setAuthor_name(userName);
        mComment.setComment_Content(comment);
        mComment.setComment_Likes(0);
        mComment.setComment_Time(time);
        mComment.setComment_Author(Integer.parseInt(UserId));
        mComment.setComment_Article(Integer.parseInt(id));
        videoPresenter.sendComment(mComment);

    }


    private void DeleteComment(String UserId) {
      videoPresenter.deleteComment(UserId);

    }

    @Override
    public void addCommentAdater(VideoCommentsModel v) {
        vda.addData(v.getData());
        mRefreshLayout.finishLoadMore(true);
    }

    @Override
    public void changeCommentAdater(VideoCommentsModel v) {
        vda.changeData(v.getData());
        mLinearLayout_comment.setVisibility(View.VISIBLE);
        mRefreshLayout.finishRefresh(true);
    }

    @Override
    public void onSendCommentSuccess() {
        sendSuccess();
        videoPresenter.updateComments();//视频评论数加一
        Toast.makeText(this,"发送成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendCommentFail() {
        Toast.makeText(this,"发送失败",Toast.LENGTH_SHORT).show();
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

    @Override
    public void onLoadVideoDetailDataSuccess() {

    }

    @Override
    public void onLoadVideoDetailDataFail() {

    }

    @Override
    public void addRecommentData(List<VideoModel.VideoList> data) {

        mRecommentVideoAdater.changeData(data);
        mLinearLayout_tuijian.setVisibility(View.VISIBLE);
    }

    @Override
    public void onloadVideoDetailInfoFail() {


    }

    @Override
    public void onloadVideoDetailInfoSuccess(VideoModel videoModel) {
        loadView.setVisibility(View.GONE);

        VideoModel.VideoList v=videoModel.getData().get(0);
        Glide.with(this).load(v.getVideo_Author_Logo()).apply(new RequestOptions().circleCrop()).into(videoAuthorLogo);
        videoAuthorName.setText(v.getVideo_Author_Name());
        mTextView_time.setText("发表于"+v.getVideo_PutTime());
        videoAbstract.setText(v.getVideo_Abstract());
        j.setUp(v.getVideo_Player(),JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,v.getVideo_Title());
        j.backButton.setVisibility(View.VISIBLE);

        Glide.with(this).load(v.getVideo_Image()).into(j.thumbImageView);

        if (NetUtil.isWifiConnected()){//wifi网络自动播放视频
            boolean d=SpUtil.getSetingFromSp("video_auto_play");
            if (SpUtil.getSetingFromSp("video_auto_play"))
            j.startVideo();

        }else {//非wifi弹出对话框
            j.showWifiDialog();

        }

        mRefreshLayout.finishRefresh(true);
    }

    @Override
    public void onloadVideoCommentFail() {
        mRefreshLayout.finishRefresh(false);
    }

    @Override
    public void noMoreVideoComment() {
        loadView.setVisibility(View.GONE);
        mRefreshLayout.setNoMoreData(true);
    }

    @Override
    public void onDeleteCommentSuccess() {
        vda.removeComments();
        linearLayout.removeView(textView);
        Toast.makeText(VideoDetailActivity.this,"删除成功",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleteCommentFail() {
        Toast.makeText(VideoDetailActivity.this,"删除失败",Toast.LENGTH_SHORT).show();

    }
}
