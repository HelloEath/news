package com.glut.news.home.presenter.impl;

import com.glut.news.common.utils.manager.RetrofitManager;
import com.glut.news.common.utils.service.RetrofitService;
import com.glut.news.home.model.entity.ArticleModel;
import com.glut.news.home.presenter.IHomeTypeFragmentPresenter;
import com.glut.news.home.view.fragment.HomeTypeFragment;
import com.glut.news.home.view.fragment.IHomeTypeFragmentView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yy on 2018/4/20.
 */

public class HomeTypeFragmentPresenterImpl implements IHomeTypeFragmentPresenter {
    private IHomeTypeFragmentView mIHomeTypeFragmentView;
    private int nextPage;
    public HomeTypeFragmentPresenterImpl(HomeTypeFragment homeTypeFragment) {
      mIHomeTypeFragmentView=homeTypeFragment;
    }


    @Override
    public void loadData(final String f, String contentType) {
        if ("fp".equals(f)){

            nextPage=1;
        }


        if (contentType.equals("推荐")) {
            RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "ArticleService").getTuiJianArticle(contentType,nextPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
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
                                if ("fp".equals(f)){

                                    mIHomeTypeFragmentView.onLoadDataFail();

                                }else {


                                    mIHomeTypeFragmentView.onLoadMoreDataFail();

                                }
                            } else {

                                if (!articleModel.isHaveNextPage()){
                                    mIHomeTypeFragmentView.onLoadEmptyData();

                                }else {
                                    if ("fp".equals(f)){//加载第一页


                                        mIHomeTypeFragmentView.onloadDataSuccess(articleModel.getData());
                                    }else {//加载更多


                                        mIHomeTypeFragmentView.onloadMoreDataSuccess(articleModel.getData());

                                    }

                                    nextPage= articleModel.getNextpage();

                                }

                            }

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {


                        }
                    });
        } else {
            RetrofitManager.builder(RetrofitService.VIDEO_BASE_URL, "ArticleService").getTypeArticle(contentType, nextPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
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
                            if (articleModel == null) {

                                if ("fp".equals(f)){//加载第一页


                                    mIHomeTypeFragmentView.onLoadDataFail();
                                }else {//加载更多

                                    mIHomeTypeFragmentView.onLoadMoreDataFail();

                                }                //mTvLoadEmpty.setVisibility(View.VISIBLE);
                            } else {

                                if (articleModel.getStus().equals("error")){
                                    mIHomeTypeFragmentView.onLoadEmptyData();


                                }else {
                                    if (!articleModel.isHaveNextPage()){
                                        mIHomeTypeFragmentView.onLoadEmptyData();
                                        mIHomeTypeFragmentView.onloadMoreDataSuccess(articleModel.getData());

                                    }else {
                                        if ("fp".equals(f)){//加载第一页


                                            mIHomeTypeFragmentView.onloadDataSuccess(articleModel.getData());
                                        }else {//加载更多


                                            mIHomeTypeFragmentView.onloadMoreDataSuccess(articleModel.getData());

                                        }

                                        nextPage= articleModel.getNextpage();


                                    }

                                }

                            }


                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {


                        }
                    });
        }

    }


}
