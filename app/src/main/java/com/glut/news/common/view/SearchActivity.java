package com.glut.news.common.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.glut.news.AppApplication;
import com.glut.news.R;
import com.glut.news.common.presenter.impl.SearchActivityPresenterImpl;
import com.glut.news.common.view.searchview.ICallBack;
import com.glut.news.common.view.searchview.SearchView;
import com.glut.news.my.model.adater.HistoryAdater;
import com.glut.news.my.model.entity.CommonData;
import com.glut.news.my.model.entity.HistoryWithStarModel;

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
    private List<CommonData> searchData;
private  HistoryAdater historyAdater;
    private SearchActivityPresenterImpl s=new SearchActivityPresenterImpl(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_search);
        initView();
        initData();
        AppApplication.getInstance().addActivity(this);
    }

    private void initData() {
       String v= getIntent().getStringExtra("v");
        s.search(v);
    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        mSearchView= (SearchView) findViewById(R.id.search);
        recyclerView= (RecyclerView) findViewById(R.id.search_result);
        textView= (TextView) findViewById(R.id.btn_search);
        LinearLayoutManager l=new LinearLayoutManager(this);
        l.setOrientation(LinearLayoutManager.VERTICAL);
        searchData=new ArrayList<>();
        historyAdater=new HistoryAdater(this,searchData);
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(historyAdater);

        mSearchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                s.search(string);//执行搜索
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
       setSupportActionBar(toolbar);
        ActionBar a=getSupportActionBar();
        if (a!=null){
            a.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;

        }
        return false;
    }

    @Override
    public void onSearchSuccess(HistoryWithStarModel h) {
        historyAdater.changeDta(h.getData());
    }

    @Override
    public void onMoreSearchSuccess(HistoryWithStarModel h) {
        historyAdater.addData(h.getData());
    }

    @Override
    public void onSearchFail() {
        Toast.makeText(this,"搜索失败",Toast.LENGTH_SHORT).show();
    }
}
