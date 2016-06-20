package com.apec.android.views.activities;

import android.databinding.DataBindingUtil;
import android.view.MenuItem;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.databinding.ActivityGoodDetailBinding;
import com.apec.android.domain.entities.goods.Good;
import com.apec.android.injector.components.DaggerGoodDetailComponent;
import com.apec.android.injector.modules.ActivityModule;
import com.apec.android.injector.modules.GoodDetailModule;
import com.apec.android.mvp.presenters.GoodDetailPresenter;
import com.apec.android.mvp.views.GoodDetailView;
import com.apec.android.views.activities.core.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by duanlei on 2016/5/13.
 */
public class GoodDetailActivity extends BaseActivity implements GoodDetailView {

    public final static String EXTRA_GOODS_ID = "goods_id";
    int goodId;

    @Inject
    GoodDetailPresenter mGoodDetailPresenter;

    ActivityGoodDetailBinding mBinding;

    @Override
    protected void setUpContentView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_good_detail);
        ButterKnife.bind(this);
        setUpToolbar(R.string.title_good_detail, -1, MODE_BACK);
    }

    @Override
    protected void initUi() {

    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        goodId = getIntent().getIntExtra(EXTRA_GOODS_ID, 0);
        DaggerGoodDetailComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(application.getAppComponent())
                .goodDetailModule(new GoodDetailModule(goodId))
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mGoodDetailPresenter.attachView(this);
        mGoodDetailPresenter.onCreate();
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    protected void setUpMenu(int menuId) {
        super.setUpMenu(R.menu.menu_cart);
    }

    @Override
    protected void onStop() {
        mGoodDetailPresenter.onStop();
        super.onStop();
    }

    @Override
    public void bindGood(Good good) {
        mBinding.setGood(good);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cart:
//                Intent intent = new Intent(GoodDetailActivity.this, ShoppingCartActivity.class);
//                startActivity(intent);
                break;
        }
        return true;
    }
}
