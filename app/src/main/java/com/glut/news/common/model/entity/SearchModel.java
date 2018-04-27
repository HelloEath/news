package com.glut.news.common.model.entity;

import java.util.List;

/**
 * Created by yy on 2018/4/23.
 */

public class SearchModel {

   /* nextpage	2
    data	[…]
    stus	"ok"*/
    private String nextpage;
    private List<SearchModelList> data;
    private String stus;

    public class SearchModelList {

        //private List<SearchModelListData>
    }
}
