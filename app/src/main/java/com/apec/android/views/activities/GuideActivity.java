package com.apec.android.views.activities;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.views.activities.core.BaseActivity;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.Holder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by duanlei on 2016/6/10.
 */
public class GuideActivity extends BaseActivity {

    @BindView(R.id.convenientBanner)
    ConvenientBanner mConvenientBanner;

    List mLocalImages;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_guide, -1, 2);
    }

    @Override
    protected void initUi() {
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
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        mConvenientBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



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
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }
}
