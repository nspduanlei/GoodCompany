package com.apec.android.support.rest.interceptors;

import android.content.Context;

import com.apec.android.views.utils.FileUtils;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by duanlei on 2016/6/28.
 */
public class MockInterceptor implements Interceptor {

    Context mContext;

    public MockInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        String responseString = createResponseBody(chain);

        Response response = new Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                .addHeader("content-type", "application/json")
                .build();
        return response;
    }

    /**
     * 读文件获取json字符串，生成ResponseBody
     *
     * @param chain
     * @return
     */
    private String createResponseBody(Chain chain) {

        String responseString = null;

        HttpUrl uri = chain.request().url();
        String path = uri.url().getPath();

        //if (path.matches("^(/users/)+[^/]*+(/repos)$")) {//匹配/users/{username}/repos

        if (path.equals("/testapi/orders")) {//匹配/users/{username}/repos
            responseString = getResponseString("order.json");
        }
        return responseString;
    }

    /**
     * 根据文件名获取json
     * @param fileName
     * @return
     */
    private String getResponseString(String fileName) {
        String res="";
        try{
            //得到资源中的asset数据流
            InputStream in = mContext.getResources().getAssets().open(fileName);

            int length = in.available();
            byte [] buffer = new byte[length];

            in.read(buffer);
            in.close();
            res = new String(buffer, "UTF-8");

        }catch(Exception e){

            e.printStackTrace();

        }

        return res;
    }
}