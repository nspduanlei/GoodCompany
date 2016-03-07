package com.apec.android.support.http.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.apec.android.support.http.Listener;
import com.apec.android.support.test;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
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

    public GsonRequest(int method, String url, Class<T> clazz, Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mGson = new Gson();
        mClass = clazz;
        mMap = listener.getRequestParams();
        mListener = listener;
    }

    public GsonRequest(String url, Class<T> clazz, Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(Method.GET, url, clazz, listener, errorListener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
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



        return super.getHeaders();
    }
}
