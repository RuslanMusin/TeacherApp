package com.summer.itis.curatorapp.api;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.summer.itis.curatorapp.utils.Const.APP_JSON;
import static com.summer.itis.curatorapp.utils.Const.CONTENT_TYPE;


public final class ApiKeyInterceptor implements Interceptor {

    private ApiKeyInterceptor() {
    }

    @NonNull
    public static Interceptor create() {
        return new ApiKeyInterceptor();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        return chain.proceed(original.newBuilder()
                .addHeader(CONTENT_TYPE,APP_JSON)
                .build());
    }
}
