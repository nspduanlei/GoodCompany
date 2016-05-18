package com.apec.android.mvp.presenters;

import com.apec.android.domain.entities.goods.GoodDetail;
import com.apec.android.domain.usercase.DoAddCartUseCase;
import com.apec.android.domain.usercase.GetAllGoodAttrsUseCase;
import com.apec.android.domain.usercase.GetArriveTimeUseCase;
import com.apec.android.domain.usercase.GetGoodDetailUseCase;
import com.apec.android.mvp.views.GoodDetailView;
import com.apec.android.mvp.views.View;
import com.apec.android.util.L;

import javax.inject.Inject;

/**
 * Created by duanlei on 2016/5/16.
 */
public class GoodDetailPresenter implements Presenter {

    GoodDetailView mGoodDetailView;

    //所有属性
    GetAllGoodAttrsUseCase mGetAllGoodAttrsUseCase;
    //到货时间
    GetArriveTimeUseCase mGetArriveTimeUseCase;
    //加入购入车
    DoAddCartUseCase mDoAddCartUseCase;
    //商品详情
    GetGoodDetailUseCase mGetGoodDetailUseCase;

    @Inject
    public GoodDetailPresenter(GetAllGoodAttrsUseCase getAllGoodAttrsUseCase,
                               GetArriveTimeUseCase getArriveTimeUseCase,
                               DoAddCartUseCase doAddCartUseCase,
                               GetGoodDetailUseCase getGoodDetailUseCase) {

        mGetAllGoodAttrsUseCase = getAllGoodAttrsUseCase;
        mGetArriveTimeUseCase = getArriveTimeUseCase;
        mDoAddCartUseCase = doAddCartUseCase;
        mGetGoodDetailUseCase = getGoodDetailUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mGoodDetailView = (GoodDetailView) v;
    }

    @Override
    public void onCreate() {
        mGetAllGoodAttrsUseCase.execute()
                .flatMap(getAllAttribute -> {
                    //获取到商品全部属性
                    //TODO 存储全部属性
                    return mGetGoodDetailUseCase.execute();
                }).subscribe(this::onGoodDetailReceived, this::manageGoodDetailError);
    }

    private void onGoodDetailReceived(GoodDetail goodDetail) {
        mGoodDetailView.bindGood(goodDetail.getB());
    }

    private void manageGoodDetailError(Throwable error) {

    }
}
