package com.apec.android.ui.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.ui.activity.goods.GoodsActivity;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.user.RegisterPresenter;

/**
 * 注册流程，填写资料
 * Created by Administrator on 2016/2/26.
 */
public class RegisterFragment extends BaseFragment<RegisterPresenter.IView,
        RegisterPresenter> implements RegisterPresenter.IView, View.OnClickListener {

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_register;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        Button finish = (Button) view.findViewById(R.id.btn_finish);
        finish.setOnClickListener(this);

        ImageView back = (ImageView) view.findViewById(R.id.iv_back);
        back.setOnClickListener(this);

        TextView title = (TextView) view.findViewById(R.id.tv_top_title);
        title.setText("填写资料");
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmptyCase() {

    }

    @Override
    public boolean isReady() {
        return isAdded();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_finish:
                Intent intent = new Intent(getActivity(), GoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_back:
                getActivity().finish();
                break;
        }

    }
}
