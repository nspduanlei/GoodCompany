package com.apec.android.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.views.fragments.core.BaseFragment;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/26.
 */
public class RecommendFragment extends BaseFragment {


    @BindView(R.id.convenientBanner)
    ConvenientBanner mConvenientBanner;

    List mLocalImages;


    @Override
    protected void initUI(View view) {
        mLocalImages = new ArrayList<>();
        mLocalImages.add(R.drawable.test0010);
        mLocalImages.add(R.drawable.test002);
        mLocalImages.add(R.drawable.test003);
        initBanner();
    }

    private void initBanner() {
        mConvenientBanner.setPages(
                () -> new LocalImageHolderView(), mLocalImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.shape_page_indicator,
                        R.drawable.shape_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);

    }

    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }
        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
