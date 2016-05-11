package com.apec.android.mvp.presenters;

import com.apec.android.config.Constants;
import com.apec.android.domain.entities.goods.Goods;
import com.apec.android.domain.usercase.GetGoodsUseCase;
import com.apec.android.mvp.views.GoodsListView;
import com.apec.android.mvp.views.View;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/5/10.
 */
public class GoodsListPresenter implements Presenter {

    //View
    private GoodsListView mGoodsListView;

    //get Observable
    private final GetGoodsUseCase mGetGoodsUseCase;

    //观察者订阅返回类，用于取消订阅
    private Subscription mGoodsSubscription;

    @Inject
    public GoodsListPresenter(GetGoodsUseCase getGoodsUseCase) {
        mGetGoodsUseCase = getGoodsUseCase;
    }

    @Override
    public void onCreate() {
        getGoods();
    }

    private void getGoods() {
        mGoodsListView.hideErrorView();
        mGoodsListView.showLoadingView();

        mGoodsSubscription = mGetGoodsUseCase.execute()
                .subscribe(this::onGoodsReceived, this::manageGoodsError);
    }

    //getGoods 返回成功
    private void onGoodsReceived(Goods goods) {
        mGoodsListView.hideLoadingView();
        if (goods.getH().getCode() == Constants.SUCCESS_CODE) {
            mGoodsListView.bindGoods(goods.getB().getData());
        } else {
            mGoodsListView.showErrorView(goods.getH().getMsg());
        }
    }

    //发生错误
    private void manageGoodsError(Throwable error) {
        mGoodsListView.showErrorView(error.toString());
        mGoodsListView.hideLoadingView();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        //activity stop 取消订阅
        if (!mGoodsSubscription.isUnsubscribed()) {
            mGoodsSubscription.unsubscribe();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mGoodsListView = (GoodsListView) v;
    }

    public void onElementClick(int position) {

    }

    public void GetGoodsById(int cityId) {
        mGetGoodsUseCase.setCityId(cityId);
        getGoods();
    }
}
