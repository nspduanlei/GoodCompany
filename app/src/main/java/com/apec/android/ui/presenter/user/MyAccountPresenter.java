package com.apec.android.ui.presenter.user;

import android.content.Context;

import com.android.volley.VolleyError;
import com.apec.android.domain.GetDataCallback;
import com.apec.android.domain.user.Area;
import com.apec.android.domain.user.Areas;
import com.apec.android.domain.user.interator.UserInteract;
import com.apec.android.ui.presenter.BasePresenter;
import com.apec.android.ui.presenter.BaseViewInterface;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/26.
 */
public class MyAccountPresenter extends BasePresenter<MyAccountPresenter.IView> {


    public MyAccountPresenter(Context context) {
        super(context);
    }

    public void obtainArea(int id) {
        UserInteract.obtainArea(mContext, new GetDataCallback<Areas>() {
            @Override
            public void onRepose(Areas response) {
                if (response.getH().getCode() == 200) {
                    getView().getAreaBack(response.getB());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, String.valueOf(id));
    }

    public interface IView extends BaseViewInterface {
        boolean isReady();
        void getAreaBack(ArrayList<Area> areas);
    }
}
