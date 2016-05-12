package com.apec.android.ui.fragment.goods;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.config.Constants;
import com.apec.android.domain.goods.Good;
import com.apec.android.domain.goods.Sku;
import com.apec.android.domain.goods.SkuAttrValue;
import com.apec.android.domain.goods.SkuAttribute;
import com.apec.android.ui.activity.user.RegisterFActivity;
import com.apec.android.ui.activity.user.ShoppingCartActivity;
import com.apec.android.ui.fragment.BaseListFragment;
import com.apec.android.ui.presenter.goods.GoodsDetailPresenter;
import com.apec.android.util.DensityUtils;
import com.apec.android.util.SPUtils;
import com.apec.android.util.StringUtils;
import com.apec.android.util.T;

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
    //未读消息
    private View newCart;
    //快速下单
    private Button fastOrder;

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

        newCart = view.findViewById(R.id.view_new_cart);
        if ((Boolean) SPUtils.get(getActivity(), SPUtils.HAS_NEW_GOODS, false)) {
            newCart.setVisibility(View.VISIBLE);
        }

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

    private Button addShoppingCart;

    private void initFootView() {
        tvGoodsNet = (TextView) footerView.findViewById(R.id.tv_goods_net);
        flNetContent = (FrameLayout) footerView.findViewById(R.id.fl_net_content);
        tvNetTitle = (TextView) footerView.findViewById(R.id.tv_net_title);

        fastOrder = (Button) footerView.findViewById(R.id.btn_fast_order);
        fastOrder.setOnClickListener(this);

        arrivalTime = (TextView) footerView.findViewById(R.id.tv_arrival_time);
        totalPrice = (TextView) footerView.findViewById(R.id.tv_total_price);
        addButton = (ImageButton) footerView.findViewById(R.id.btn_add);
        cutButton = (ImageButton) footerView.findViewById(R.id.btn_cut);

        addShoppingCart = (Button) footerView.findViewById(R.id.btn_add_shopping_cart);

        addButton.setOnClickListener(this);
        cutButton.setOnClickListener(this);
        addShoppingCart.setOnClickListener(this);

        goodsCount = (EditText) footerView.findViewById(R.id.et_goods_count);
        goodsCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isNullOrEmpty(s.toString()) ||
                        Integer.valueOf(s.toString()) < 1) {
                    goodsCount.setText("1");
                } else if (Integer.valueOf(s.toString()) > Constants.MAX_GOODS_COUNT) {
                    goodsCount.setText(String.valueOf(Constants.MAX_GOODS_COUNT));
                    T.showShort(getActivity(),
                            "最多只能买" + Constants.MAX_GOODS_COUNT + "包");
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

    private SparseArray<Integer> mSelectAttrs = new SparseArray<>();

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

        private void addSkuAttrValue(final RadioGroup radioGroup, ArrayList<SkuAttrValue> values,
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
                        mSelectAttrs.put(position, view.getId());
                        List<Sku> skus = mGood.getSkus();

                        for (int j = 0; j < skus.size(); j++) {
                            boolean isSelect = true;

                            List<SkuAttribute> attrs = skus.get(j).getAttributeNames();
                            for (int k = 0; k < attrs.size(); k++) {
                                if (mSelectAttrs.get(k) != null &&
                                        mSelectAttrs.get(k) != attrs.get(k).getAttributeValues()
                                        .get(0).getId()) {
                                    isSelect = false;
                                }
                            }

                            if (isSelect) {
                                //找到了匹配的sku
                                mSkuId = skus.get(j).getId();
                                addShoppingCart.setEnabled(true);
                                fastOrder.setEnabled(true);
                                setSkuData(skus.get(j));
                                break;
                            } else {
                                mSkuId = 0;
                                addShoppingCart.setEnabled(false);
                                fastOrder.setEnabled(false);
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

    /**
     * 选择指定sku
     * @param skuItem
     */
    private void selectSku(Sku skuItem) {
        //自动将该sku选中
        List<SkuAttribute> list = skuItem.getAttributeNames();
        for (int i = 0; i < list.size(); i++) {
            //属性的sku，type = 2 代表尽含量
            if (list.get(i).getType().equals("2")) {
                break;
            }

            mSelectAttrs.put(i, list.get(i).getAttributeValues().get(0).getId());

            //如果要选择的项是隐藏的，则显示
            View itemView = getListView().getChildAt(i);

            int attrValue = list.get(i).getAttributeValues().get(0).getId();
            RadioButton rb = (RadioButton) itemView.findViewById(attrValue);
            rb.setChecked(true);
        }

        //填充相关数据
        setSkuData(skuItem);
    }

    /**
     * 填充相关数据
     * @param skuItem
     */
    private void setSkuData(Sku skuItem) {
        mPrice = skuItem.getPrice();
        totalPrice.setText(String.format(getString(R.string.add_order_total),
                skuItem.getPrice()));
        goodsCount.setText("1");

        if (skuItem.getNonkeyAttr().size() > 0) {
            mNetContent = skuItem.getNonkeyAttr().get(0).getAttributeValues().get(0).getName();
            mNetTitle = skuItem.getNonkeyAttr().get(0).getName();
            flNetContent.setVisibility(View.VISIBLE);

            if (mNetTitle != null) {
                tvGoodsNet.setText(String.format(getString(R.string.net_content),
                        mPrice, mNetContent));
                tvNetTitle.setText(mNetTitle);
            }
        } else {
            flNetContent.setVisibility(View.GONE);
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
    public void showNoConnection() {

    }

    @Override
    public void hideNoConnection() {

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
            //默认选择第一个sku
            mSkuId = good.getSkus().get(0).getId();
            selectSku(good.getSkus().get(0));

            //获取到货时间
            mPresenter.getArrivalTime();
        } else {
            addShoppingCart.setEnabled(false);
            fastOrder.setEnabled(false);
        }
    }

    @Override
    public void needLogin() {
        T.showShort(getActivity(), R.string.please_login);
        Intent intent = new Intent(getActivity(), RegisterFActivity.class);
        startActivity(intent);
    }

    @Override
    public void addShoppingCartSuccess() {
        if (isFast) {
            //TODO  跳转到购物车
            Intent intent = new Intent(getActivity(), ShoppingCartActivity.class);
            startActivity(intent);
        } else {
            T.showShort(getActivity(), "加入购物车成功");
            //购物车有新的商品
            SPUtils.put(getActivity(), SPUtils.HAS_NEW_GOODS, true);
            newCart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean isReady() {
        return isAdded();
    }

    //是否快速下单
    boolean isFast = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.btn_add_shopping_cart: //加入购物车
                if (mSkuId != 0) {
                    isFast = false;
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

            case R.id.btn_fast_order:
                //快速下单
                if (mSkuId != 0) {
                    isFast = true;
                    mPresenter.addShoppingCart(mSkuId, goodsCount.getText().toString());
                }
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (newCart == null) {
            return;
        }
        if ((Boolean) SPUtils.get(getActivity(), SPUtils.HAS_NEW_GOODS, false)) {
            newCart.setVisibility(View.VISIBLE);
        } else {
            newCart.setVisibility(View.GONE);
        }
    }
}
