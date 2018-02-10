package com.glut.news.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.glut.news.R;

/**
 * Created by yy on 2018/1/24.
 */
public class ArticleDetailActivity extends AppCompatActivity{
    private TextView title;
    private TextView author;
    private TextView time;
    private WebView mWebView;
    private final static String TAG="ArticleDetailActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        String id=getIntent().getStringExtra("id");
        initView();
        initWebView();
        loadData();

    }

    private void loadData() {

        mWebView.post(new Runnable() {
            @Override
            public void run() {
           String content="<p>娱乐圈中，不少人成名之后都厌倦目前的生活，选择开启一段新的生活，这已然是娱乐圈的常态了。因为成名后，自己也是有一定知名度的明星了，感觉目前的生活肯定不符自己的身份。而今天小编要和大家介绍的这位便是成名后抛弃二十年发妻，因戏生情娶了新欢，他就是侯勇。</p><p><img src=\\\"http://p0.ifengimg.com/pmop/2017/0718/9ACA34DF84F0A6EA9B53F144FC753535A5DEE346_size11_w450_h250.jpeg\\\"></p><p>我们先来介绍一下侯勇，1967年2月23日出生于江苏省连云港市，毕业于江苏省戏剧学校，国家一级演员、军人。想必大家对侯勇都应该非常熟悉吧，2002年时凭借电影《冲出亚马逊》成名，此后红极一时片约不断。也许这部电影有些读者没有看过，那前段时间火热的《人民的名义》大家应该看过吧，侯勇在剧中饰演处长赵德汉，给观众们留下了深刻的印象。</p><p><img src=\\\"http://p0.ifengimg.com/pmop/2017/0718/33C9290AF98C1A76FC2C2885EDC4202A72F6FC7A_size15_w500_h345.jpeg\\\"></p><p>侯勇有过两段婚姻，发妻是演员沈蓉，二妻是演员潘雨辰。发妻是侯勇的大学时期表演系的同班同学，二人在大学的时候就已经相恋，毕业后的沈蓉凭借着自己的演技在影坛崭露头角有着自己的一席之地。此后沈蓉接了很多影视作品，而且还一举夺得飞天奖最佳女主角奖。然而这个时候的侯勇发展的却不是那么顺利，一直默默无闻。1992年的时候，对爱情忠贞不渝的沈蓉还是选择了与侯勇结婚。结婚之后的沈蓉在各个方面都帮助自己的老公，最终侯勇迎来了绽放异彩的一天。</p><p><img src=\\\"http://d.ifengimg.com/w600/p0.ifengimg.com/pmop/2017/0718/AFA6DD92FB273D84F9EF747EF677071225E1BDF3_size28_w636_h483.jpeg\\\"></p><p>成名之后的侯勇曾公开表示：“我非常感谢妻子沈蓉，如果没有他，那么就不可能有今天的侯勇。”二人结婚二十年，最大的遗憾莫过于没有孩子，而侯勇也表示和妻子商量好了决定不要孩子了。</p><p>然而让人意想不到的事情还是发生了，十几年来夫妻二人因为拍戏聚少离多，而且加上侯勇与80后演员的绯闻，夫妻二人的关系最终发生了变故最终二人分道扬镳，结束了长达二十年的爱情长跑。</p><p><img src=\\\"http://p0.ifengimg.com/pmop/2017/0718/67A7AA921DB9698B3073853B445039B9EAAC56D7_size20_w407_h291.jpeg\\\"></p><p>侯勇和潘雨辰在拍片时相识，二人一起合作拍摄了电视剧《不能没有你》和《沙场点兵》，拍戏期间二人因戏生情，2011年在潘雨辰的老家举办了豪华的婚礼，随后二人一起搬到了北京东城区，而潘雨辰此时也背负了“小三”的骂名。</p><p><img src=\\\"http://p0.ifengimg.com/pmop/2017/0718/86DE751D3B0004826E570A8D43EE436C371B5C1F_size21_w460_h287.jpeg\\\"></p><p>5年之后，潘雨辰承认自己和侯勇缘分已尽，称：“我一个人带着孩子已经生活了很久了。其实分开也没什么，生活就是这样，该来的总会来，该结束的就会结束。”如今，潘雨辰独自带着孩子生活在老家丹东。</p><p><img src=\\\"http://p0.ifengimg.com/pmop/2017/0718/2DF17EAC9C2D988856F18912F365EA707B1CD30C_size18_w426_h210.jpeg\\\"></p><p>虽然潘雨辰并没有明确表示二人是否真的离婚，但是从二人的状态来看，这分爱情却没有能够经受住时间的考验，最终仍是以失败告终。</p>";
                String url="javascript:show_content(\'"+content+"\')";

                mWebView.loadUrl(url);

            }
        });
    }

    private void initWebView() {

        //addJs(mWebView);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setVerticalScrollbarOverlay(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setHorizontalScrollbarOverlay(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.loadUrl("file:///android_asset/ifeng/post_detail.html");
        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadData();
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


    }
}
