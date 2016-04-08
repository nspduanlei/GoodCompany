package com.apec.android.ui.fragment.goods;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apec.android.R;
import com.apec.android.config.Constants;
import com.apec.android.domain.goods.Good;
import com.apec.android.domain.goods.Sku;
import com.apec.android.domain.goods.SkuAttrValue;
import com.apec.android.domain.goods.SkuAttribute;
import com.apec.android.domain.user.Skus;
import com.apec.android.ui.activity.user.RegisterFActivity;
import com.apec.android.ui.activity.user.ShoppingCartActivity;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.presenter.goods.GoodsDetailPresenter;
import com.apec.android.util.DensityUtils;
import com.apec.android.util.L;
import com.apec.android.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情
 * Created by duanlei on 2016/3/2.
 */
public class GoodsDetailFragment extends BaseListFragment<GoodsDetailPresenter.IView,
        GoodsDetailPresenter> implements GoodsDetailPresenter.IView, View.OnClickListener {

    public final static String EXTRA_GOODS_ID = "goods_id";

    public static GoodsDetailFragment newInstance(int gId) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_GOODS_ID, gId);
        GoodsDetailFragment fragment = new GoodsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private int goodsId;
    private ArrayList<SkuAttribute> mData;
    private BaseAdapter mAdapter;
    private View footerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goodsId = getArguments().getInt(EXTRA_GOODS_ID);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_goods_detail;
    }

    @Override
    protected GoodsDetailPresenter createPresenter() {
        return new GoodsDetailPresenter(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        mPresenter.fetchGoodsAttrs(goodsId);
    }

    private FrameLayout loading;

    private void initView(View view) {
        /**
         * 导航栏初始化
         */
        ImageView back = (ImageView) view.findViewById(R.id.iv_back);
        back.setOnClickListener(this);

        TextView title = (TextView) view.findViewById(R.id.tv_top_title);
        title.setText("订单");

        loading = (FrameLayout) view.findViewById(R.id.fl_loading);

        view.findViewById(R.id.iv_shopping_cart).setOnClickListener(this);

        footerView = getActivity().getLayoutInflater()
                .inflate(R.layout.goods_detail_bottom, null);

        initFootView();

        mData = new ArrayList<>();
        mAdapter = new MyAdapter();
        setListAdapter(mAdapter);
    }

    private TextView totalPrice;
    private EditText goodsCount;
    private ImageButton addButton, cutButton;
    private TextView arrivalTime;

    //净含量 fl_net_content tv_goods_net tv_net_title
    private FrameLayout flNetContent;
    private TextView tvGoodsNet;
    private TextView tvNetTitle;

    private void initFootView() {
        tvGoodsNet = (TextView) footerView.findViewById(R.id.tv_goods_net);
        flNetContent = (FrameLayout) footerView.findViewById(R.id.fl_net_content);
        tvNetTitle = (TextView) footerView.findViewById(R.id.tv_net_title);

        arrivalTime = (TextView) footerView.findViewById(R.id.tv_arrival_time);
        totalPrice = (TextView) footerView.findViewById(R.id.tv_total_price);
        addButton = (ImageButton) footerView.findViewById(R.id.btn_add);
        cutButton = (ImageButton) footerView.findViewById(R.id.btn_cut);
        addButton.setOnClickListener(this);
        cutButton.setOnClickListener(this);
        footerView.findViewById(R.id.btn_add_shopping_cart).setOnClickListener(this);

        goodsCount = (EditText) footerView.findViewById(R.id.et_goods_count);
        goodsCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                L.e("test00", "--------->" + s.toString());

                if (StringUtils.isNullOrEmpty(s.toString()) ||
                        Integer.valueOf(s.toString()) < 1) {
                    goodsCount.setText("1");
//                    Toast.makeText(getActivity(),
//                            "最小包数为1", Toast.LENGTH_SHORT).show();
                } else if (Integer.valueOf(s.toString()) > Constants.MAX_GOODS_COUNT) {
                    goodsCount.setText(String.valueOf(Constants.MAX_GOODS_COUNT));
                    Toast.makeText(getActivity(),
                            "最多只能买" + Constants.MAX_GOODS_COUNT + "包",
                            Toast.LENGTH_SHORT).show();
                }

                if (!StringUtils.isNullOrEmpty(s.toString()) && Integer.valueOf(s.toString()) > 0) {
                    int textCount = Integer.valueOf(s.toString());
                    Double textPrice = textCount * Double.parseDouble(mPrice);
                    totalPrice.setText(String.format(getString(R.string.add_order_total),
                            String.valueOf(textPrice)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //记录选择的skuValue
    //private Map<Integer, Integer> selects = new HashMap<>();
    private int mSkuId;
    private String mPrice;
    private String mNetContent;
    private String mNetTitle;

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public SkuAttribute getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder viewHolder;
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.sku_attr_item, null);
                viewHolder = new MyViewHolder();
                viewHolder.radioGroup = (RadioGroup) convertView.findViewById(R.id.rg_attrs);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_attr_name);

                addSkuAttrValue(viewHolder.radioGroup,
                        getItem(position).getAttributeValues(), position);


                convertView.setTag(viewHolder);
            } else {
                viewHolder = (MyViewHolder) convertView.getTag();
            }

            viewHolder.textView.setText(getItem(position).getName());

            return convertView;
        }

        private void addSkuAttrValue(RadioGroup radioGroup, ArrayList<SkuAttrValue> values,
                                     final int position) {
            RadioButton radioButton;
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(0, 0, DensityUtils.dp2px(getActivity(), 10), 0);

            for (SkuAttrValue value : values) {
                radioButton = new RadioButton(getActivity());
                radioButton.setTextAppearance(getActivity(), R.style.btn_style_radio);
                radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
                radioButton.setBackgroundResource(R.drawable.radio_selector);
                radioButton.setTextAppearance(getActivity(), R.style.radio_text_color);
                radioButton.setText(value.getName());
                radioButton.setLayoutParams(layoutParams);
                radioButton.setId(value.getId());

                radioButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        //找到选择的id属于哪个sku
                        List<Sku> listSku = mGood.getSkus();
                        for (Sku skuItem : listSku) {
                            //遍历sku中的 attr
                            List<SkuAttribute> listAttr = skuItem.getAttributeNames();

                            for (SkuAttribute attrLItem : listAttr) {
                                if (attrLItem.getAttributeValues().get(0).getId() == view.getId()) {
                                    //找到了该id，则sku匹配成功
                                    mSkuId = skuItem.getId();

                                    //自动将该sku选中
                                    List<SkuAttribute> list = skuItem.getAttributeNames();
                                    for (int i = 0; i < list.size(); i++) {
                                        //属性的sku，type = 2 代表尽含量
                                        if (list.get(i).getType().equals("2")) {
                                            break;
                                        }

                                        //如果要选择的项是隐藏的，则显示
                                        View itemView = getListView().getChildAt(i);
                                        if (itemView.getVisibility() == View.GONE) {
                                            itemView.setVisibility(View.VISIBLE);
                                        }

                                        int attrValue = list.get(i).getAttributeValues().get(0).getId();
                                        RadioButton rb = (RadioButton) itemView.findViewById(attrValue);
                                        rb.setChecked(true);
                                    }

                                    //选择完了如果还有项没选择，则隐藏
                                    for (int i = list.size(); i < getListView().getCount() - 1; i++) {
                                        getListView().getChildAt(i).setVisibility(View.GONE);
                                    }

                                    //填充相关数据
                                    mPrice = skuItem.getPrice();
                                    totalPrice.setText(String.format(getString(R.string.add_order_total),
                                            skuItem.getPrice()));
                                    goodsCount.setText("1");

                                    if (mNetTitle != null) {
                                        tvGoodsNet.setText(String.format(getString(R.string.net_content),
                                                mPrice, mNetContent));
                                        tvNetTitle.setText(mNetTitle);
                                    }
                                    return;
                                }
                            }
                        }
                    }
                });
                radioGroup.addView(radioButton);
            }
        }

        public final class MyViewHolder {
            public RadioGroup radioGroup;
            public TextView textView;
        }
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyCase() {

    }

    @Override
    public void getArrivalTimeSuccess(String time) {
        //到货时间获取成功，填充
        arrivalTime.setText(String.format(getString(R.string.arrival_time), time));
    }

    @Override
    public void getAllAttrSuccess(ArrayList<SkuAttribute> attrs) {
        mData.clear();

        for (SkuAttribute skuAttribute : attrs) {
            if (skuAttribute.getType().equals("1")) {
                mData.add(skuAttribute);
            } else if (skuAttribute.getType().equals("2")) {
                mNetContent = skuAttribute.getAttributeValues().get(0).getName();
                mNetTitle = skuAttribute.getName();
                flNetContent.setVisibility(View.VISIBLE);
            }
        }

        mAdapter.notifyDataSetChanged();
        getListView().addFooterView(footerView, null, false);

        mPresenter.queryGoodsDetail(goodsId);
    }

    private Good mGood;

    @Override
    public void getGoodsDetail(Good good) {
        mGood = good;

        if (good.getSkus().size() > 0) {
            RadioButton rb = (RadioButton) getListView().getChildAt(0)
                    .findViewById(mData.get(0).getAttributeValues().get(0).getId());
            rb.setChecked(true);
            rb.performClick();
        }

        //获取到货时间
        mPresenter.getArrivalTime();
    }

    @Override
    public void needLogin() {
        Toast.makeText(getActivity(), R.string.please_login, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), RegisterFActivity.class);
        startActivity(intent);
    }

    @Override
    public void addShoppingCartSuccess() {
        Toast.makeText(getActivity(), "加入购物车成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isReady() {
        return isAdded();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.btn_add_shopping_cart: //加入购物车
                if (mSkuId != 0) {
                    mPresenter.addShoppingCart(mSkuId, goodsCount.getText().toString());
                }
                break;

            case R.id.btn_add:
                int textAdd = Integer.valueOf(goodsCount.getText().toString());
                goodsCount.setText(String.valueOf(textAdd + 1));
                break;

            case R.id.btn_cut:
                int textCut = Integer.valueOf(goodsCount.getText().toString());
                goodsCount.setText(String.valueOf(textCut - 1));
                break;

            case R.id.iv_shopping_cart:
                //进入购物车
                Intent intent = new Intent(getActivity(), ShoppingCartActivity.class);
                startActivity(intent);
                break;
        }
    }
}
