package com.apec.android.ui.activity.goods;

import android.support.v4.app.Fragment;

import com.apec.android.ui.activity.SingleFragmentActivity;
import com.apec.android.ui.fragment.goods.GoodsDetailFragment;

/**
 * 商品详情
 * Created by duanlei on 2016/3/2.
 */
public class GoodsDetailActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return GoodsDetailFragment.newInstance(
                getIntent().getIntExtra(GoodsDetailFragment.EXTRA_GOODS_ID, 0));
    }
}
