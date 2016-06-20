package com.apec.android.mvp.presenters;

import com.apec.android.domain.entities.goods.SkuData;
import com.apec.android.domain.entities.user.ShopCart;
import com.apec.android.domain.entities.user.ShopCartBack;
import com.apec.android.domain.entities.user.ShopCartData;
import com.apec.android.domain.usercase.CreateOrderUseCase;
import com.apec.android.domain.usercase.DeleteCartUseCase;
import com.apec.android.domain.usercase.DoAddBatchCartUseCase;
import com.apec.android.domain.usercase.DoAddCartUseCase;
import com.apec.android.domain.usercase.GetAllCartUseCase;
import com.apec.android.mvp.views.ShoppingCartView;
import com.apec.android.mvp.views.View;
import com.apec.android.views.utils.ShopCartUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by duanlei on 2016/6/7.
 */
public class ShoppingCartPresenter implements Presenter {

    ShoppingCartView mView;

    Subscription mGetAllSubscription;

    @Inject
    public ShoppingCartPresenter() {
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mGetAllSubscription != null) {
            mGetAllSubscription.unsubscribe();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mView = (ShoppingCartView) v;
    }

    @Override
    public void onCreate() {
        //getData();
    }

    public void getData() {
        mView.showLoadingView();

        mGetAllSubscription = Observable.create((Observable.OnSubscribe<ShopCartData>) subscriber -> {

            List<SkuData> list = ShopCartUtil.queryAll();
            //获取购物车数据
            ShopCartData shopCartData = new ShopCartData();
            shopCartData.setSkus(list);
            shopCartData.setTotal(ShopCartUtil.queryAllNum());

            double totalPrice = 0.00;
            for (SkuData skuData:list) {
                if (skuData.isSelect()) {
                    totalPrice = totalPrice + Double.valueOf(skuData.getPrice()) * skuData.getCount();
                }
            }

            shopCartData.setTotalPrice(String.valueOf(totalPrice));

            subscriber.onNext(shopCartData);

        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
        .subscribe(this::onSaveSkuReceived, this::manageSaveSkuError);
    }

    private void manageSaveSkuError(Throwable throwable) {
        mView.hideLoadingView();
    }

    private void onSaveSkuReceived(ShopCartData shopCartData) {
        mView.hideLoadingView();
        mView.hideEmpty();
        if (shopCartData.getTotal() == 0) {
            mView.onDataEmpty();
        } else {
            mView.bindCart(shopCartData);
        }

    }

    public void createOrders() {

    }
}
