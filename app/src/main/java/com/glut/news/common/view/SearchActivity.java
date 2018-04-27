package com.glut.news.common.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.glut.news.AppApplication;
import com.glut.news.R;
import com.glut.news.common.model.adater.SearchAdater;
import com.glut.news.common.presenter.impl.SearchActivityPresenterImpl;
import com.glut.news.common.view.searchview.SearchView;
import com.glut.news.home.model.entity.ArticleModel;
import com.glut.news.home.view.activity.ArticleDetailActivity;
import com.glut.news.video.view.activity.VideoDetailActivity;
import com.mingle.widget.LoadingView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2018/3/19.
 */

public class SearchActivity extends AppCompatActivity implements ISearchActivityView {
    private Toolbar toolbar;
    private SearchView mSearchView;
    private RecyclerView recyclerView;
    private TextView textView;
    private List<ArticleModel.ArticleList> searchData;
    private SearchAdater searchAdater;
    private SearchActivityPresenterImpl s = new SearchActivityPresenterImpl(this);

    private SmartRefreshLayout mRefreshLayout;
    private String SearchValue;
    private LoadingView mLoadingView;
    private FloatingSearchView mFloatingSearchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_search);
        initView();
        initData();
        AppApplication.getInstance().addActivity(this);
    }

    private void initData() {
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);
    }

    private void initView() {
        mFloatingSearchView=findViewById(R.id.floating_search_view);
        mLoadingView=findViewById(R.id.loadView);
        mRefreshLayout=findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.search_result);
        LinearLayoutManager l = new LinearLayoutManager(this);
        l.setOrientation(LinearLayoutManager.VERTICAL);
        searchData = new ArrayList<>();
        searchAdater = new SearchAdater(this, searchData);
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(searchAdater);
        searchAdater.setOnItemClickListener(new SearchAdater.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int articleId, String contentType, String searchType) {
                if ("1".equals(searchType)) {//点击的是图文
                    Intent i = new Intent(SearchActivity.this, ArticleDetailActivity.class);
                    i.putExtra("id", articleId+"");
                    i.putExtra("ContentType", contentType);
                    startActivity(i);
                } else {//点击的是视频
                    Intent i = new Intent(SearchActivity.this, VideoDetailActivity.class);
                    i.putExtra("id", articleId+"");
                    i.putExtra("ContentType", contentType);

                    startActivity(i);
                }
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                s.search(SearchValue,"null");//执行搜索

            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                s.search(SearchValue,"fp");//执行搜索

            }
        });
        //监听返回按钮
        mFloatingSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                finish();
            }
        });

        //字符变化监听
        mFloatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
               /* if (!oldQuery.equals("") && newQuery.equals("")) {
                    mFloatingSearchView.clearSuggestions();
                } else {

                    //this shows the top left circular progress
                    //you can call it where ever you want, but
                    //it makes sense to do it when loading something in
                    //the background.
                    mFloatingSearchView.showProgress();

                    //simulates a query call to a data source
                    //with a new query.
                    DataHelper.findSuggestions(getActivity(), newQuery, 5,
                            FIND_SUGGESTION_SIMULATED_DELAY, new DataHelper.OnFindSuggestionsListener() {

                                @Override
                                public void onResults(List<ColorSuggestion> results) {

                                    //this will swap the data and
                                    //render the collapse/expand animations as necessary
                                    mSearchView.swapSuggestions(results);

                                    //let the users know that the background
                                    //process has completed
                                    mSearchView.hideProgress();
                                }
                            });
                }

                Log.d(TAG, "onSearchTextChanged()");
            }*/
            }
        });
        //点击搜索监听
        mFloatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            //点击建议栏
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            //点击键盘搜索
            @Override
            public void onSearchAction(String currentQuery) {
                SearchValue=currentQuery;
                s.search(currentQuery,"fp");
                mLoadingView.setVisibility(View.VISIBLE);
            }
        });

        //获得焦点监听
        mFloatingSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {

                    // 展示历史搜索项
                    //mSearchView.swapSuggestions(DataHelper.getHistory(getActivity(), 3));


            }

            @Override
            public void onFocusCleared() {
                //你也可以将已经打上的搜索字符保存，以致在下一次点击的时候，搜索栏内还保存着之前输入的字符
                //mSearchView.setSearchText(searchSuggestion.getBody());
            }
        });
    }



    @Override
    public void onSearchSuccess(ArticleModel h) {
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.finishRefresh();
        searchAdater.changeDta(h.getData());
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void onMoreSearchSuccess(ArticleModel h) {
        mRefreshLayout.finishLoadMore(true);
        searchAdater.addData(h.getData());
    }

    @Override
    public void onSearchFail() {
        mRefreshLayout.finishRefresh();
        Toast.makeText(this, "搜索失败", Toast.LENGTH_SHORT).show();
        mLoadingView.setVisibility(View.GONE);

    }

    @Override
    public void noMoreData() {
        mRefreshLayout.setNoMoreData(true);
        mRefreshLayout.setEnableLoadMore(false);
    }
}
