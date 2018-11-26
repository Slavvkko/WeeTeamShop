package com.hordiienko.weeteamshop.network;

import com.hordiienko.weeteamshop.Constants;
import com.hordiienko.weeteamshop.model.Product;
import com.hordiienko.weeteamshop.model.ProductResponse;

import java.util.List;

import io.reactivex.Single;

public class NetworkHelper {
    private static NetworkHelper instance;

    public static NetworkHelper getInstance() {
        if (instance == null) {
            instance = new NetworkHelper();
        }

        return instance;
    }

    private ProductsInterface productsInterface;

    private NetworkHelper() {
        productsInterface = RetrofitClient.getRetrofit().create(ProductsInterface.class);
    }

    public Single<List<Product>> getProducts(int offset) {
        return productsInterface.getProducts(offset + "," + Constants.API_PAGE_SIZE)
                .map(ProductResponse::getProducts);
    }
}
