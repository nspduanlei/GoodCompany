package com.apec.android.mvp.presenters;

import com.apec.android.config.Constants;
import com.apec.android.domain.entities.goods.Sku;
import com.apec.android.domain.entities.goods.SkuList;
import com.apec.android.domain.usercase.GetGoodsUseCase;
import com.apec.android.mvp.views.GoodsListView;
import com.apec.android.mvp.views.View;

import java.util.ArrayList;
import java.util.List;

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

    List<Sku> mSkus = new ArrayList<>();

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
    private void onGoodsReceived(SkuList skus) {
        mGoodsListView.hideLoadingView();

        int code = skus.getH().getCode();
        if (code == Constants.SUCCESS_CODE) {
            mSkus = skus.getB().getData();
            mGoodsListView.bindGoods(mSkus);
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
        if (mGoodsSubscription.isUnsubscribed()) {
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
        if (mSkus.size() == 0) {
            return;
        }
        int goodId = mSkus.get(position).getId();
        mGoodsListView.showDetailScreen(goodId);
    }

    public void GetGoodsById(int cityId) {
        //设置参数
        mGetGoodsUseCase.setCityId(cityId);
        //发送请求
        getGoods();
    }
}
