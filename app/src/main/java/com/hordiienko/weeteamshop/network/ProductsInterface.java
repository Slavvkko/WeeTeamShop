package com.hordiienko.weeteamshop.network;

import com.hordiienko.weeteamshop.model.ProductResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductsInterface {
    // http://ps1722.weeteam.net/api/products?output_format=JSON&display=[name,description,id_default_image,price,reference]&limit=2
    @GET("api/products?output_format=JSON&display=[id,id_default_image,reference,price,name,description]")
    Single<ProductResponse> getProducts(@Query("limit") String limit);
}
