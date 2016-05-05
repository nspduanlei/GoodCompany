package com.apec.android.ui.presenter.user;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.config.ErrorCode;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.NoBody;
import com.apec.android.domain.transport.GoodsReceipt;
import com.apec.android.domain.transport.ReceiptList;
import com.apec.android.domain.transport.interator.TransportInteract;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/26.
 */
public class ManageAddrPresenter extends BasePresenter<ManageAddrPresenter.IView> {


    public ManageAddrPresenter(Context context) {
        super(context);
    }

    /**
     * 获取全部地址
     */
    public void getAllAddress() {
        if (isViewAttached()) {
            getView().showLoading();
        }
        TransportInteract.getAllAddress(
                mContext, new GetDataCallback<ReceiptList>() {
                    @Override
                    public void onRepose(ReceiptList response) {
                        if (!isViewAttached()) {
                            return;
                        }

                        getView().hideLoading();
                        int code = response.getH().getCode();
                        if (code == 200) {
                            getView().getAllAddressSuccess(response.getB());
                        } else if (code == ErrorCode.ERROR_NEED_LOGIN) {
                            //需要登录
                            getView().needLogin();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    /**
     * 设置默认收货地址
     *
     * @param addressId
     */
    public void setDefaultAddress(int addressId) {
//        if (isViewAttached()) {
//            getView().showLoading();
//        }
        TransportInteract.setDefaultAddress(
                mContext, new GetDataCallback<NoBody>() {
                    @Override
                    public void onRepose(NoBody response) {
                        if (!isViewAttached()) {
                            return;
                        }

//                        if (isViewAttached()) {
//                            getView().hideLoading();
//                        }
                        int code = response.getH().getCode();
                        if (code == 200) {
                            getView().setDefaultSuccess();
                        } else if (code == ErrorCode.ERROR_NEED_LOGIN) {
                            //需要登录
                            getView().needLogin();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, addressId);
    }

    /**
     * 删除地址
     *
     * @param addressId
     */
    public void deleteAddress(int addressId) {
//        if (isViewAttached()) {
//            getView().showLoading();
//        }
        TransportInteract.deleteAddress(
                mContext, new GetDataCallback<NoBody>() {
                    @Override
                    public void onRepose(NoBody response) {
                        if (!isViewAttached()) {
                            return;
                        }
//                        if (isViewAttached()) {
//                            getView().hideLoading();
//                        }
                        int code = response.getH().getCode();
                        if (code == 200) {
                            getView().deleteSuccess();
                        } else if (code == ErrorCode.ERROR_NEED_LOGIN) {
                            //需要登录
                            getView().needLogin();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, addressId);
    }

    public interface IView extends BaseViewInterface {
        void deleteSuccess();

        void needLogin();

        void setDefaultSuccess();

        void getAllAddressSuccess(ArrayList<GoodsReceipt> receipts);

        boolean isReady();
    }
}
