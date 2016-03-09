package com.apec.android.ui.fragment.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.apec.android.R;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.presenter.user.RegisterPresenter;
import com.apec.android.ui.presenter.user.SelectCityPresenter;

/**
 * Created by Administrator on 2016/2/26.
 */
public class SelectCityFragment extends BaseListFragment<SelectCityPresenter.IView,
        SelectCityPresenter> implements SelectCityPresenter.IView {

    String[] test = {
            "Dwight D. Eisenhower",
            "John F. Kennedy",
            "Lyndon B. Johnson",
            "Richard Nixon",
            "Gerald Ford",
            "Jimmy Carter",
            "Ronald Reagan",
            "George H. W. Bush",
            "Bill Clinton",
            "George W. Bush",
            "Barack Obama"
    };

    public static SelectCityFragment newInstance() {
        SelectCityFragment fragment = new SelectCityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, test));
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_select_city;
    }

    @Override
    protected SelectCityPresenter createPresenter() {
        return new SelectCityPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }


    private void initView(View view) {
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
}
