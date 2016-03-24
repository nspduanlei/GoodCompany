package com.apec.android.ui.fragment.goods;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import com.apec.android.R;
import com.apec.android.config.Constants;
import com.apec.android.domain.goods.Good;
import com.apec.android.domain.goods.Sku;
import com.apec.android.domain.goods.SkuAttrValue;
import com.apec.android.domain.goods.SkuAttribute;
import com.apec.android.ui.activity.user.RegisterActivity;
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
    private ArrayList<SkuAttribute> datas;
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

        datas = new ArrayList<>();
        mAdapter = new MyAdapter();
        setListAdapter(mAdapter);
    }

    private TextView totalPrice;
    private EditText goodsCount;
    private ImageButton addButton, cutButton;

    private void initFootView() {
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

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public SkuAttribute getItem(int position) {
            return datas.get(position);
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

                addSkuAttrValue(viewHolder.radioGroup,
                        getItem(position).getAttributeValues(), position);

                viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_attr_name);
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
                radioButton.setButtonDrawable(null);
                radioButton.setBackgroundResource(R.drawable.radio_selector);
                radioButton.setTextAppearance(getActivity(), R.style.radio_text_color);
                radioButton.setText(value.getName());
                radioButton.setLayoutParams(layoutParams);
                radioButton.setId(value.getId());
                radioGroup.addView(radioButton);
            }

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    //找到选择的id属于哪个sku
                    List<Sku> listSku = mGood.getSkus();
                    for (Sku skuItem : listSku) {
                        //遍历sku中的 attr
                        List<SkuAttribute> listAttr = skuItem.getAttributeNames();
                        for (SkuAttribute attrLItem : listAttr) {
                            if (attrLItem.getAttributeValues().get(0).getId() == checkedId) {
                                //找到了该id，则sku匹配成功
                                mSkuId = skuItem.getId();

                                List<SkuAttribute> list = skuItem.getAttributeNames();

                                for (int i = 0; i < list.size(); i++) {
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

                                //TODO 填充相关数据
                                mPrice = skuItem.getPrice();
                                totalPrice.setText(String.format(getString(R.string.add_order_total),
                                        skuItem.getPrice()));
                                goodsCount.setText("1");
                                return;
                            }
                        }

                    }
                }
            });
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
    public void getAllAttrSuccess(ArrayList<SkuAttribute> attrs) {
        if (attrs.size() == 0) {
            return;
        }
        datas.clear();
        datas.addAll(attrs);
        mAdapter.notifyDataSetChanged();
        getListView().addFooterView(footerView, null, false);
        mPresenter.queryGoodsDetail(goodsId);
    }

    private Good mGood;

    @Override
    public void getGoodsDetail(Good good) {
        mGood = good;
        RadioButton rb = (RadioButton) getListView().getChildAt(0)
                .findViewById(datas.get(0).getAttributeValues().get(0).getId());
        rb.setChecked(true);
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

                mPresenter.addShoppingCart(mSkuId, goodsCount.getText().toString());
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
