package com.apec.android.support.http;

import java.util.Map;

/**
 * Created by duanlei on 2016/3/2.
 */
public interface Listener<T> {

    void onResponse(T response);

    Map getRequestParams();
}
