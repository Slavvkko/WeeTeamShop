package com.hordiienko.weeteamshop.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hordiienko.weeteamshop.Constants;
import com.hordiienko.weeteamshop.model.Product;
import com.hordiienko.weeteamshop.model.ProductDeserializer;
import com.hordiienko.weeteamshop.model.ProductResponse;
import com.hordiienko.weeteamshop.model.ProductResponseDeserializer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = buildRetrofit();
        }

        return retrofit;
    }

    private static Retrofit buildRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request origin = chain.request();
                    Request request = origin.newBuilder()
                            .header("Authorization", Constants.API_CREDENTIALS)
                            .build();

                    return chain.proceed(request);
                })
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Product.class, new ProductDeserializer())
                .registerTypeAdapter(ProductResponse.class, new ProductResponseDeserializer())
                .create();

        return new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }
}
