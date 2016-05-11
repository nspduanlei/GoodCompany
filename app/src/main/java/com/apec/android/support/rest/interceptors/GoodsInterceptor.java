package com.apec.android.support.rest.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class GoodsInterceptor implements Interceptor {
	private final String mApiKey;
	private final String mApiSecret;

	public GoodsInterceptor(String apiKey, String apiSecret) {
		mApiKey = apiKey;
		mApiSecret = apiSecret;
	}

	@Override public Response intercept(Chain chain) throws IOException {
//		String marvelHash = MarvelApiUtils.generateMarvelHash(mApiKey, mApiSecret);
//		Request oldRequest = chain.request();
//
//		HttpUrl.Builder authorizedUrlBuilder = oldRequest.httpUrl().newBuilder()
//			.scheme(oldRequest.httpUrl().scheme())
//			.host(oldRequest.httpUrl().host());
//
//		authorizedUrlBuilder.addQueryParameter(RestDataSource.PARAM_API_KEY, mApiKey)
//			.addQueryParameter(RestDataSource.PARAM_TIMESTAMP, MarvelApiUtils.getUnixTimeStamp())
//			.addQueryParameter(RestDataSource.PARAM_HASH, marvelHash);
//
//		Request newRequest = oldRequest.newBuilder()
//			.method(oldRequest.method(), oldRequest.body())
//			.url(authorizedUrlBuilder.build())
//			.build();

		return chain.proceed(null);
	}
}

