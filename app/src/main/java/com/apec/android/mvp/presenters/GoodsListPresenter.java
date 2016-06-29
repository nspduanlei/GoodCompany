package com.apec.android.mvp.presenters;

import com.apec.android.config.Constants;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.entities.goods.Sku;
import com.apec.android.domain.entities.goods.SkuData;
import com.apec.android.domain.entities.goods.SkuList;
import com.apec.android.domain.entities.user.ShopCartData;
import com.apec.android.domain.usercase.CreateOneOrderUseCase;
import com.apec.android.domain.usercase.CreateOrderUseCase;
import com.apec.android.domain.usercase.GetGoodsUseCase;
import com.apec.android.mvp.views.GoodsListView;
import com.apec.android.mvp.views.View;
import com.apec.android.util.L;
import com.apec.android.views.utils.ShopCartUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by duanlei on 2016/5/10.
 */
public class GoodsListPresenter implements Presenter {

    //View
    private GoodsListView mGoodsListView;

    //get Observable
    private GetGoodsUseCase mGetGoodsUseCase;
    private CreateOneOrderUseCase mCreateOrderUseCase;

    //观察者订阅返回类，用于取消订阅
    private Subscription mGoodsSubscription;

    List<Sku> mSkus = new ArrayList<>();

    @Inject
    public GoodsListPresenter(GetGoodsUseCase getGoodsUseCase,
                              CreateOneOrderUseCase createOneOrderUseCase) {
        mGetGoodsUseCase = getGoodsUseCase;
        mCreateOrderUseCase = createOneOrderUseCase;
    }

    @Override
    public void onCreate() {
        //getGoods();
    }

    public void getGoods() {
        mGoodsListView.hideErrorView();
        mGoodsListView.showLoadingView();

        mGoodsSubscription = mGetGoodsUseCase.execute()
                .observeOn(Schedulers.io())
                .doOnNext(skuList -> {
                    if (skuList.getB() != null) {

                        for (Sku sku : skuList.getB().getData()) {
                            SkuData skuData = ShopCartUtil.querySkuById(String.valueOf(sku.getId()));
                            if (skuData != null) {
                                sku.setCount(skuData.getCount());
                            }
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
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
        if (mGoodsSubscription != null) {
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

    public void createOrder(int skuId, int addressId, int num) {
        mCreateOrderUseCase.setData(skuId, addressId, num);
        mCreateOrderUseCase.execute()
                .subscribe(this::onCreateOrderReceived, this::manageCreateOrderError);
    }

    private void manageCreateOrderError(Throwable throwable) {

    }

    private void onCreateOrderReceived(NoBody noBody) {

    }

    /**
     * 修改商品数量
     * @param sku
     */
    public void updateCount(Sku sku, int num) {
        Observable.create((Observable.OnSubscribe<Integer>) subscriber -> {

            //操作数据库，数量+1
            ShopCartUtil.updateCount(String.valueOf(sku.getId()), num);

            //获取购物车数量
            int allCount = ShopCartUtil.querySkuNum();

            subscriber.onNext(allCount);

        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(this::onAddCountReceived, this::manageError);
    }

    private void manageError(Throwable throwable) {
        L.e("error");
    }

    private void onAddCountReceived(int allCount) {
        mGoodsListView.updateCountSuccess(allCount);
    }
}
