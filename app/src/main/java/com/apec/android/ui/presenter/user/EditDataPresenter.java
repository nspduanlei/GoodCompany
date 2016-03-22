package com.apec.android.ui.presenter.user;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.config.ErrorCode;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.transport.interator.TransportInteract;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

/**
 * Created by Administrator on 2016/2/26.
 */
public class EditDataPresenter extends BasePresenter<EditDataPresenter.IView> {


    public EditDataPresenter(Context context) {
        super(context);
    }

    /**
     * 保存收货信息
     * @param takeGoodsPhone
     * @param takeGoodsUser
     * @param addreCity
     * @param addreAreacounty
     * @param addreDetailAddress
     */
    public void saveAddress(String takeGoodsPhone, String takeGoodsUser,
                            int addreCity, int addreAreacounty, String addreDetailAddress) {

        if (isViewAttached()) {
            getView().showLoading();
        }
        TransportInteract.addDeliveryManInfo(
                mContext, new GetDataCallback<NoBody>() {
                    @Override
                    public void onRepose(NoBody response) {
                        getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            //修改商品数量成功


                        } else if (code == ErrorCode.ERR_NEED_LOGIN) {
                            //需要登录
                            getView().needLogin();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, takeGoodsPhone, takeGoodsUser,
       addreCity, addreAreacounty, addreDetailAddress);

    }

    public interface IView extends BaseViewInterface {
        void needLogin();
        boolean isReady();
    }
}
