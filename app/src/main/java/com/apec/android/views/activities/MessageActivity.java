package com.apec.android.views.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.apec.android.R;
import com.apec.android.app.MyApplication;
import com.apec.android.domain.entities.user.Message;
import com.apec.android.views.activities.core.BaseActivity;
import com.apec.android.views.adapter.MessageListAdapter;
import com.apec.android.views.utils.MessageUtils;
import com.apec.android.views.view.ListClickListener;
import com.apec.android.views.view.RecyclerInsetsDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by duanlei on 2016/6/10.
 */
public class MessageActivity extends BaseActivity implements ListClickListener {

    @BindView(R.id.rl_message)
    RecyclerView mRlMessage;

    MessageListAdapter mAdapter;
    List<Message> mMessageList;


    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_message, R.string.message_title);
    }

    MessageUtils mMessageUtils;

    @Override
    protected void initUi() {

        mMessageUtils = new MessageUtils();
        mMessageList = new ArrayList<>();
        //TODO
//        for(int i = 0; i < 10; i++) {
//            mMessageList.add(new Message(1, "消息提醒", 100, "嘎嘎啊发嘎嘎韩国哈哈哈哈哈哈就经济结构"));
//        }

        mMessageList.addAll(mMessageUtils.select());

        mAdapter = new MessageListAdapter(mMessageList, this, this);

        mRlMessage.addItemDecoration(new RecyclerInsetsDecoration(this));
        mRlMessage.setLayoutManager(new LinearLayoutManager(this));

        mRlMessage.setAdapter(mAdapter);
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void onElementClick(int position) {

    }
}
