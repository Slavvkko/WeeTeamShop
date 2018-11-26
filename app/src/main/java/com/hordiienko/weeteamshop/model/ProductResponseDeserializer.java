package com.hordiienko.weeteamshop.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductResponseDeserializer implements JsonDeserializer<ProductResponse> {
    @Override
    public ProductResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonArray products = json.getAsJsonObject().get("products").getAsJsonArray();

            List<Product> list = new ArrayList<>();

            if (products.size() > 0) {
                for (JsonElement element : products) {
                    Product product = context.deserialize(element, Product.class);
                    list.add(product);
                }
            }

            return new ProductResponse(list);

        } catch (IllegalStateException e) {
            return new ProductResponse();
        }
    }
}
