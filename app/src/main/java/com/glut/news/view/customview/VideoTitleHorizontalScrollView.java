package com.glut.news.view.customview;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glut.news.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2018/1/26.
 */

public class VideoTitleHorizontalScrollView extends HorizontalScrollView implements View.OnClickListener{

    private LayoutInflater inflater;//加载布局
    private View view;//加载view
    private LinearLayout titles;
    private List<TextView> titlesList=new ArrayList<>();//标题数组
    private ImageView imageView;//下划线
    private static int mlastPosition;//当前选中的item
    private OnItemClickListener monItemClickListener;//监听函数


    public VideoTitleHorizontalScrollView(Context context) {
        super(context);
    }

    public VideoTitleHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }



    public VideoTitleHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(View v) {
        int x=titles.indexOfChild(v);//得到该v在父容器的下标
        monItemClickListener.onClick(x);

    }


    public interface OnItemClickListener {
        void onClick(int position);
    }
    //传入回调接口
    public void setOnItemClickListener(OnItemClickListener monItemClickListener){
        this.monItemClickListener=monItemClickListener;
    }

/*初始化加载进来的布局*/
    private void initView() {
        this.inflater=LayoutInflater.from(getContext());
        view=this.inflater.inflate(R.layout.news_title_horizontalscroll,null);
        this.titles=view.findViewById(R.id.news_title_horizontalscrollview_titletxt_layout);
        //获得当前布局下所有孩子,并放进titleList中,并设置监听
        for (int i=0;i<this.titles.getChildCount();i++){
            this.titlesList.add((TextView) this.titles.getChildAt(i));
            this.titles.getChildAt(i).setOnClickListener(this);
        }
        this.imageView=view.findViewById(R.id.iv_nav_indicator);
        int w=MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        int h=MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        this.titlesList.get(0).measure(w,h);
        LinearLayout.LayoutParams imageParams= (LinearLayout.LayoutParams) this.imageView.getLayoutParams();
        imageParams.width=this.titlesList.get(0).getMeasuredWidth()+20;
        imageParams.height=5;
        this.imageView.setLayoutParams(imageParams);
        addView(view);


    }



    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp==dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
 /*  viewPager跳转之下划线联动
 * pos 跳转后的索引
 * */

    public void setPagerChangeListenerToTextView(int pos){
        int scrollStartX=0;//动画起始位置
        int scrollendX=0;//动画结束位置
       // 设置其他未选中标题颜色
        for (int i=0;i<this.titlesList.size();i++){
            if (i==pos){
                //判断滑动方向，里面为左向有
                if (mlastPosition<i){
                 scrollBy(100,0);
                  for (int j=0;j<mlastPosition;j++){
                      scrollStartX+=this.titlesList.get(j).getWidth()+dip2px(getContext(), 20);
                  }
                    for (int j=0;j<i;j++){
                        scrollendX = scrollendX + this.titlesList.get(j).getWidth() + dip2px(getContext(), 20);
                    }
                    slideview(scrollStartX,scrollendX);
                }else{
                    scrollBy(0-100,0);
                    for (int j = 0; j <mlastPosition; j++) {
                        scrollStartX += this.titlesList.get(j).getWidth() + dip2px(getContext(), 20);
                    }
                    for (int j=0;j<i;j++){
                        scrollendX = scrollendX + this.titlesList.get(j).getWidth() + dip2px(getContext(), 20);
                    }
                    Log.i("liyuanjinglyjfanxiang", "scrollStartX=" + String.valueOf(scrollStartX) + "----scrollEndX" + String.valueOf(scrollendX));
                    slideview(scrollStartX, scrollendX);
                }


                LinearLayout.LayoutParams imageParams = (LinearLayout.LayoutParams) this.imageView.getLayoutParams();
                imageParams.width = this.titlesList.get(i).getWidth() +dip2px(getContext(), 20);
                imageParams.height = 5;
                this.imageView.setLayoutParams(imageParams);
                this.titlesList.get(i).setTextColor(Color.RED);
                this.mlastPosition = pos;//设置当前的标题索引
            }else{

                //未选标题全部为黑色
                this.titlesList.get(i).setTextColor(Color.BLACK);

            }
        }
    }

    /**
     * 滑动动画
     * @param p1 起始
     * @param p2 终点
     */
    public void slideview(float p1, float p2) {
        TranslateAnimation animation = new TranslateAnimation(p1, p2, 0, 0);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(300);
        animation.setFillEnabled(true);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);
    }

    /*动态添加标题
    * text 标题文字
    * context 上下文
    * */

    public void addTextViewTitle(String title,Context c){

        TextView textView=new TextView(c);
        textView.setText(title);
        textView.setTextColor(Color.BLACK);
        textView.setClickable(true);
        textView.setTextSize(20.0f);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 0, 20, 0);
        textView.setLayoutParams(params);
        textView.setOnClickListener(this);
        this.titles.addView(textView);
        this.titlesList.add(textView);
        new Handler().post(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(10);
                    int left = titles.getMeasuredWidth() - getWidth();

                    Log.d("getMeasuredWidth()",titles.getMeasuredWidth()+"");
                    Log.d("getWidth()",getWidth()+"");
                    if (left < 0) {
                        left = 0;
                    }
                    scrollTo(left, 0);
                    Log.d("left2",left+"");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
