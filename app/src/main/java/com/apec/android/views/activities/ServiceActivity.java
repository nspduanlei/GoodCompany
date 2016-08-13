package com.apec.android.views.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ListView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.domain.entities.user.Question;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.adapter.listView.CommonAdapter;
import com.apec.android.views.adapter.listView.MyViewHolder;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/6/10.
 */
public class ServiceActivity extends BaseActivity {

    @BindView(R.id.lv_question)
    ListView mLvQuestion;

    ArrayList<Question> mQuestions = new ArrayList<>();

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_service, R.string.service_title);
    }

    @Override
    protected void initUi() {
        mQuestions.add(new Question(1, "多定一些有优惠吗？", "客户您好，目前商城的商品都是统一定价，" +
                "暂时不接收议价。"));

        mQuestions.add(new Question(2, "收到多久可以退换货？", "客户您好，建议您在签收时做好验货工作，" +
                "由于商城销售的属于食品类，对于安全性要求极高。一般签收后不支持退换货。"));

        mQuestions.add(new Question(3, "有赠品吗？", "客户您好，商城销售的商品一般都是以页面显示的商品为准，" +
                "如页面未显示有赠品，那么该商品暂时无额外赠送。"));

        mQuestions.add(new Question(4, "是否支持试用？", "客户您好，目前商城暂未开通试用渠道，" +
                "如后续有此服务，我们会及时通知到您。"));

        mQuestions.add(new Question(5, "支持什么付款方式？", "客户您好，目前商城支付方式为货到付款，" +
                "支持您在收到货之后支付现金或者转账付款。"));


        mLvQuestion.setAdapter(new CommonAdapter<Question>(this, mQuestions,
                R.layout.item_question) {
            @Override
            public void convert(MyViewHolder holder, Question question) {
                holder.setText(R.id.tv_num, String.format("%d. ", question.getId()))
                        .setText(R.id.tv_question, question.getQuestion())
                        .setText(R.id.tv_content, question.getContent());

                holder.setOnClickLister(R.id.tv_question, view -> {
                    if (holder.getVisibility(R.id.tv_content) == View.VISIBLE) {
                        holder.setVisibility(R.id.tv_content, View.GONE);
                    } else if (holder.getVisibility(R.id.tv_content) == View.GONE) {
                        holder.setVisibility(R.id.tv_content, View.VISIBLE);
                    }
                });
            }
        });

    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

    @OnClick(R.id.tv_phone_number)
    void OnPhoneNumberClicked(View view) {
        String mobile = "4001000747";
        // 使用系统的电话拨号服务，必须去声明权限，在AndroidManifest.xml中进行声明
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                + mobile));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        startActivity(intent);
    }
}
