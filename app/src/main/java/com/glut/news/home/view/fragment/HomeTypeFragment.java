package com.glut.news.home.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.glut.news.R;
import com.glut.news.home.model.adater.HomeRecyclerAdater;
import com.glut.news.home.model.entity.ArticleModel;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.home.view.activity.ArticleDetailActivity;
import com.glut.news.common.view.customview.VideoTitleHorizontalScrollView;
import com.mingle.widget.LoadingView;

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
public class HomeTypeFragment extends Fragment {

    private boolean isloading = false;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private HomeRecyclerAdater adapter;
    private SwipeRefreshLayout refresh;
    private VideoTitleHorizontalScrollView titleScroll;
    private LinearLayout addTitleLayout;
    private List<ArticleModel.ArticleList> newslist = new ArrayList<>();
    private static final String URL = "http://v.juhe.cn/toutiao/index?key=1b679531f9e0beb0d6cbc93ea73b4ac8";

    private int nextPage;
    private String Article_Type;

    public HomeTypeFragment(String s) {
        Article_Type=s;
    }
    private LoadingView loadingView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_type, container, false);
        initRefresh(v);
        loadData();
        initRecycleVIew(v);
        return v;
    }

    //加载更多数据
    private void loadMoreData() {

        if (Article_Type.equals("推荐")) {

        } else {
            RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "ArticleService").getTypeArticle(Article_Type, nextPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            //mPbLoading.setVisibility(View.VISIBLE);
                        }
                    })
                    .map(new Func1<ArticleModel, ArticleModel>() {
                        @Override
                        public ArticleModel call(ArticleModel articleModel) {
                            return articleModel;
                        }
                    })
                    .subscribe(new Action1<ArticleModel>() {
                        @Override
                        public void call(ArticleModel articleModel) {
                            // mPbLoading.setVisibility(View.GONE);
                            if (articleModel == null) {
                                //mTvLoadEmpty.setVisibility(View.VISIBLE);
                            } else {

                                //判断是否使用缓存数据
                                if (nextPage== articleModel.getNextpage()){


                                }else {
                                    nextPage= articleModel.getNextpage();
                                    adapter.addData(articleModel.getData());
                                }
                                //mTvLoadEmpty.setVisibility(View.GONE);
                            }

                            isloading=false;
                            //mLoadLatestSnackbar.dismiss();
                            // refreshLayout.setRefreshing(false);
                            // mTvLoadError.setVisibility(View.GONE);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            // mLoadLatestSnackbar.show();
                            // refreshLayout.setRefreshing(false);
                            // mLoadLatestSnackbar.show();
                            // mTvLoadError.setVisibility(View.VISIBLE);
                            // mTvLoadEmpty.setVisibility(View.GONE);

                        }
                    });
            //videoRecyclerAdapter.addData(videoList);
        }


    }

    //加载视频列表数据
    private void loadData() {
        if (Article_Type.equals("推荐")) {

        } else {
            RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "ArticleService").getTypeArticle(Article_Type, 1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            //mPbLoading.setVisibility(View.VISIBLE);
                        }
                    })
                    .map(new Func1<ArticleModel, ArticleModel>() {
                        @Override
                        public ArticleModel call(ArticleModel articleModel) {
                            return articleModel;
                        }
                    })
                    .subscribe(new Action1<ArticleModel>() {
                        @Override
                        public void call(ArticleModel articleModel) {
                            // mPbLoading.setVisibility(View.GONE);
                            if (articleModel == null) {
                                //mTvLoadEmpty.setVisibility(View.VISIBLE);
                            } else {
                                nextPage = articleModel.getNextpage();
                                adapter.changeData(articleModel.getData());
                                //mTvLoadEmpty.setVisibility(View.GONE);
                            }
                            //mLoadLatestSnackbar.dismiss();
                            // refreshLayout.setRefreshing(false);
                            // mTvLoadError.setVisibility(View.GONE);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            // mLoadLatestSnackbar.show();
                            // refreshLayout.setRefreshing(false);
                            // mLoadLatestSnackbar.show();
                            // mTvLoadError.setVisibility(View.VISIBLE);
                            // mTvLoadEmpty.setVisibility(View.GONE);

                        }
                    });
        }
    }


    private void initRefresh(View v) {
        loadingView=v.findViewById(R.id.loadView);
        refresh = v.findViewById(R.id.refresh);
        //下拉刷新
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refresh.setRefreshing(true);

                loadData();
                refresh.setRefreshing(false);
                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initRecycleVIew(View v) {

        recyclerView = v.findViewById(R.id.recyclerview);
        //获取线性布局管理器
        linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置为垂直方向
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给recyclerview设置布局
        recyclerView.setLayoutManager(linearLayoutManager);
        //创建适配器
        //loadData();
        adapter = new HomeRecyclerAdater(getActivity(), newslist);
//添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        //上拉加载更多
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                //获取所有item数目
                int totalItemCount = layoutManager.getItemCount();

                //获取最后一个item位置
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!isloading && totalItemCount < (lastVisibleItem + 2)) {
                    isloading = true;
                    loadMoreData();

                }
            }
        });
        //recycler中每一项的点击事件
        adapter.setOnItemClickListener(new HomeRecyclerAdater.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int positoin) {
                Intent i = new Intent(getActivity(), ArticleDetailActivity.class);
                String id = positoin + "";

                i.putExtra("id", positoin);
                startActivity(i);
            }
        });

    }

   /* //解析从服务器返回的数据
    private void parseData(String data) {
        Gson g = new Gson();
        NewsModel n = new NewsModel();
        n = g.fromJson(data, NewsModel.class);
        newslist = n.result.datalist;
        Log.d("解析的新闻数据", newslist.toString());
        Message m=new Message();
        m.what=1;
        m.obj=newslist;
        handler.sendMessage(m);


    }

    //从服务器请求数据
    private void getDataFromServer() {
        OkHttpClient ok = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(URL)
                .build();
        //通过Client
        ok.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    String responseString = response.body().string();
                    Log.d("服务器数据", responseString);
                    parseData(responseString);
                }
            }
        });

    }

    public void loadData() {
        refresh.setRefreshing(true);
        Task t = new Task();
        t.execute();

    }

    class Task extends AsyncTask<List<NewsTest>, Integer, List<NewsTest>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (newslist != null && newslist.size() > 0) {
                newslist.add(null);

                // notifyItemInserted(int position)，这个方法是在第position位置
                // 被插入了一条数据的时候可以使用这个方法刷新，
                // 注意这个方法调用后会有插入的动画，这个动画可以使用默认的，也可以自己定义。
                adapter.notifyItemInserted(newslist.size() - 1);
            }
        }


        @Override
        protected List<NewsTest> doInBackground(List<NewsTest>... params) {

            final String url = "http://news.qq.com/world_index.shtml";

            Document document = null;
            try {
                document = Jsoup.connect(url)
                        .ignoreContentType(true).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Elements element=document.getElementsByClass("Q-tpList");
            Elements elements = document.select(".Q-tpList");

            for (int i = 0; i < 10; i++) {
                Element element1 = elements.get(i);
                String title = element1.getElementsByClass("linkto").text();
                String src = element1.getElementsByClass("picto").attr("src");
                String author = element1.getElementsByClass("from").text();
                String detail_url = element1.getElementsByClass("pic").text();

                System.out.print("title" + title);
                System.out.print("src" + src);
                System.out.print("author" + author);
                //newslist.add(new NewsTest(title, src, author, detail_url));


            }
            return newslist;

        }


        @Override
        protected void onPostExecute(List<NewsTest> newslist) {
            super.onPostExecute(newslist);

            if (newslist.size() == 0) {
                //newslist.addAll(moreArticles);

            } else {
                //删除 footer
                newslist.remove(newslist.size() - 1);
                //newslist.addAll(moreArticles);

                isloading = false;
            }
            adapter.notifyDataSetChanged();
            refresh.setRefreshing(false);
        }
        }
        */



}
