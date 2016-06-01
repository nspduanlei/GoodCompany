package com.apec.android.domain;

import com.android.volley.VolleyError;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface GetDataCallback<T> {
    void onRepose(T response);
    void onErrorResponse(VolleyError error);
}
