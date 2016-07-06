package com.apec.android.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.domain.entities.user.Message;
import com.apec.android.util.DateUtil;
import com.apec.android.util.StringUtils;
import com.apec.android.views.view.ListClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.GoodsViewHolder> {

    private final ListClickListener mListClickListener;
    private final List<Message> mMessageList;
    private Context mContext;

    public MessageListAdapter(List<Message> messages, Context context,
                              ListClickListener recyclerClickListener) {
        mMessageList = messages;
        mContext = context;
        mListClickListener = recyclerClickListener;
    }

    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(
                R.layout.item_message_list, parent, false);
        return new GoodsViewHolder(rootView, mListClickListener);
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder holder, int position) {
        holder.bindGood(mMessageList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_message)
        TextView mTvMessage;

        public GoodsViewHolder(View itemView, final ListClickListener recyclerClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bindListener(itemView, recyclerClickListener);
        }

        public void bindGood(Message message) {
            mTvTitle.setText(message.getTitle());
            mTvMessage.setText(message.getContent());
            mTvTime.setText(message.getTime());
        }

        private void bindListener(View itemView,
                                  final ListClickListener recyclerClickListener) {
            itemView.setOnClickListener(v ->
                    recyclerClickListener.onElementClick(getAdapterPosition()));
        }
    }
}
