package com.apec.android.views.activities;

import android.os.Bundle;
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
        mQuestions.add(new Question(1, "下单后，货物什么时候送达？", "我是回答，我来给你解答，我是回答，" +
                "我来给你解答我是回答，我来给你解答我是回答，我来给你解答我是回答，我来给你解答"));

        mQuestions.add(new Question(2, "下单后，货物什么时候送达？", "我是回答，我来给你解答，我是回答，" +
                "我来给你解答我是回答，我来给你解答我是回答，我来给你解答我是回答，我来给你解答"));

        mQuestions.add(new Question(3, "下单后，货物什么时候送达？", "我是回答，我来给你解答，我是回答，" +
                "我来给你解答我是回答，我来给你解答我是回答，我来给你解答我是回答，我来给你解答"));

        mQuestions.add(new Question(4, "下单后，货物什么时候送达？", "我是回答，我来给你解答，我是回答，" +
                "我来给你解答我是回答，我来给你解答我是回答，我来给你解答我是回答，我来给你解答"));

        mQuestions.add(new Question(5, "下单后，货物什么时候送达？", "我是回答，我来给你解答，我是回答，" +
                "我来给你解答我是回答，我来给你解答我是回答，我来给你解答我是回答，我来给你解答"));


        mLvQuestion.setAdapter(new CommonAdapter<Question>(this, mQuestions,
                R.layout.item_question) {
            @Override
            public void convert(MyViewHolder holder, Question question) {
                holder.setText(R.id.tv_num, String.format("%d. ", question.getId()))
                        .setText(R.id.tv_question, question.getQuestion())
                        .setText(R.id.tv_content, question.getContent());
            }
        });

    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

}
