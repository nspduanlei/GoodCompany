package com.apec.android.ui.activity.user;

import android.support.v4.app.Fragment;

import com.apec.android.domain.transport.GoodsReceipt;
import com.apec.android.ui.activity.SingleFragmentActivity;
import com.apec.android.ui.fragment.user.EditDataFragment;
import com.apec.android.ui.fragment.user.ManageAddrFragment;
import com.apec.android.ui.fragment.user.RegisterFragment;

/**
 * 用户信息编辑
 * Created by Administrator on 2016/2/26.
 */
public class EditDataActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        GoodsReceipt goodsReceipt =
                getIntent().getParcelableExtra(
                        ManageAddrFragment.EXTRA_EDIT_ADDRESS);

        return EditDataFragment.newInstance(goodsReceipt);
    }

}
