package com.glut.news.common.presenter.impl;

import com.glut.news.common.presenter.ISearchActivityPresenter;
import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.common.view.ISearchActivityView;
import com.glut.news.home.model.entity.ArticleModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/3/19.
 */

public class SearchActivityPresenterImpl implements ISearchActivityPresenter {
   private ISearchActivityView iSearchActivityView;
    private int NextPage;

    public SearchActivityPresenterImpl(ISearchActivityView iSearchActivityView) {
        this.iSearchActivityView = iSearchActivityView;
    }

    @Override
    public void search(String searchValue, final String fp) {
        if ("fp".equals(fp)){
            NextPage=1;

        }
        RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "SearchService").doSearch(searchValue,NextPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .map(new Func1<ArticleModel, ArticleModel>() {
                    @Override
                    public ArticleModel call(ArticleModel h) {
                        return h;
                    }
                })
                .subscribe(new Action1<ArticleModel>() {
                    @Override
                    public void call(ArticleModel articleModel) {
                        if (articleModel.getStus().equals("ok")){
                            if (articleModel.isHaveNextPage()){
                                if ("fp".equals(fp)){
                                    iSearchActivityView.onSearchSuccess(articleModel);

                                }else {
                                    iSearchActivityView.onMoreSearchSuccess(articleModel);
                                }
                                NextPage= articleModel.getNextpage();

                            }else {
                                iSearchActivityView.noMoreData();
                                iSearchActivityView.onMoreSearchSuccess(articleModel);

                            }



                        }else {
                            iSearchActivityView.onSearchFail();
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        iSearchActivityView.onSearchFail();

                    }
                });
    }




}
