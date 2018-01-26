package com.glut.news.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.glut.news.R;

/**
 * Created by yy on 2018/1/24.
 */
public class ArticleDetailActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        String id=getIntent().getStringExtra("id");
        TextView textView= (TextView) findViewById(R.id.id);
        textView.setText(id);

    }
}
