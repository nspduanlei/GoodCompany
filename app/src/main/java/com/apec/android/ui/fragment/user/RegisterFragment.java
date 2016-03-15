package com.apec.android.ui.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.domain.user.Area;
import com.apec.android.ui.activity.goods.GoodsActivity;
import com.apec.android.ui.fragment.BaseFragment;
import com.apec.android.ui.presenter.user.RegisterPresenter;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

/**
 * 注册流程，填写资料
 * Created by Administrator on 2016/2/26.
 */
public class RegisterFragment extends BaseFragment<RegisterPresenter.IView,
        RegisterPresenter> implements RegisterPresenter.IView, View.OnClickListener {

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_register;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        //mPresenter.obtainArea(1);
    }

    /**
     * 地区选择
     */
//    ArrayList<Area> areaData;
//    BaseAdapter mAdapter;
//    View headView;
//    TextView selectAreaName;
//    StringBuffer strAreaName = new StringBuffer("");
//    int level = 0;

    /**
     * 表单数据
     */
    TextView tvArea;

    DialogPlus dialog;

    private void initView(View view) {
        Button finish = (Button) view.findViewById(R.id.btn_finish);
        finish.setOnClickListener(this);

        ImageView back = (ImageView) view.findViewById(R.id.iv_back);
        back.setOnClickListener(this);

        TextView title = (TextView) view.findViewById(R.id.tv_top_title);
        title.setText("填写资料");

        LinearLayout selectArea = (LinearLayout) view.findViewById(R.id.ll_select_area);
        selectArea.setOnClickListener(this);

//        areaData = new ArrayList<>();
//        mAdapter =  new CommonAdapter<Area>(getActivity(), areaData,
//                R.layout.select_city_item) {
//            @Override
//            public void convert(ViewHolder holder,
//                                Area area) {
//                holder.setText(R.id.tv_area_name, area.getAreaName());
//            }
//        };
//
//        headView = View.inflate(getActivity(), R.layout.select_city_head, null);
//        selectAreaName = (TextView) headView.findViewById(R.id.tv_area);

        tvArea = (TextView) view.findViewById(R.id.tv_area);

        dialog = new DialogPlus.Builder(getActivity())
                .setContentHolder(new ViewHolder(R.layout.dialog_content))
                .setCancelable(true)
                .setGravity(DialogPlus.Gravity.BOTTOM)
                .create();
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

    DialogPlus mDialog;

    //@Override
    //public void getAreaBack(ArrayList<Area> areas) {
//        if (areas.size() == 0 || level > 1) {
//            mDialog.dismiss();
//            tvArea.setText(strAreaName.toString());
//
//            level = 0;
//            mPresenter.obtainArea(1);
//            strAreaName.delete(0, selectAreaName.toString().length());
//            return;
//        }
//        level++;
//        areaData.clear();
//        areaData.addAll(areas);
//        mAdapter.notifyDataSetChanged();
//
//        if (!StringUtils.isNullOrEmpty(strAreaName.toString())) {
//            selectAreaName.setText(strAreaName.toString());
//        }
 //  }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_finish:
                Intent intent = new Intent(getActivity(), GoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.ll_select_area:
                //TODO test 测试弹窗
//                Holder holder = new ListHolder();
//                DialogUtils.showTestDialog(
//                        getActivity(),
//                        holder,
//                        DialogPlus.Gravity.BOTTOM,
//                        mAdapter,
//                        new OnItemClickListener() {
//                            @Override
//                            public void onItemClick(DialogPlus dialog, Object item,
//                                                    View view, int position) {
//                                if (position == 0) {
//                                    return;
//                                }
//                                mDialog = dialog;
//                                mPresenter.obtainArea(((Area) item).getId());
//                                strAreaName.append(((Area) item).getAreaName());
//                            }
//                        }, headView);



//                DialogUtils.showTest2Dialog(
//                        getActivity(),
//                        new com.orhanobut.dialogplus.ViewHolder()
//                );
                dialog.show();


                break;
        }
    }
}
