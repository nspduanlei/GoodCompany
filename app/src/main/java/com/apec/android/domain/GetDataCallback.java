package com.apec.android.domain;

import com.android.volley.VolleyError;
import com.apec.android.support.test;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface GetDataCallback<T> {
    void onRepose(T response);
    void onErrorResponse(VolleyError error);
}
