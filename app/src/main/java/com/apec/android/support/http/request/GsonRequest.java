package com.apec.android.support.http.request;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.apec.android.domain.NoBody;
import com.apec.android.support.http.Listener;
import com.apec.android.support.http.RequestHelper;
import com.apec.android.util.AppUtils;
import com.apec.android.util.L;
import com.apec.android.util.NetUtils;
import com.apec.android.util.SPUtils;
import com.apec.android.util.StringUtils;
import com.apec.android.util.T;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/25.
 */
public class GsonRequest<T> extends Request<T> {

    private final Listener<T> mListener;
    private Gson mGson;
    //请求参数
    private Map<String, String> mMap;
    private Class<T> mClass;
    private RequestHelper mHelper;
    private Context mContext;

    public GsonRequest(Context context, int method, String url, Class<T> clazz, Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        mMap = listener.getRequestParams();
        mGson = new Gson();
        mClass = clazz;
        mListener = listener;
        mContext = context;
        this.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String token = response.headers.get("x-auth-token");
            if (!StringUtils.isNullOrEmpty(token)) {
                SPUtils.put(mContext, SPUtils.SESSION_ID, token);
            }
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            L.e("test001", jsonString);
            return Response.success(mGson.fromJson(jsonString, mClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mMap;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        mHelper = new RequestHelper(mContext);
        Map<String, String> params = new HashMap<>();
        params.put("_c", "android");
        params.put("IMEI", AppUtils.getDeviceId(mContext));
        params.put("UA", mHelper.getHeaderUserAgent());
        params.put("x-auth-token", (String)
                SPUtils.get(mContext, SPUtils.SESSION_ID, "0"));
        L.e("test002", "token-->" +
                SPUtils.get(mContext, SPUtils.SESSION_ID, "0"));

        return params;
    }
}
