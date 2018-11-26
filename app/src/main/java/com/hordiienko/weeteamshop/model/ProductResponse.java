package com.hordiienko.weeteamshop.model;

import java.util.ArrayList;
import java.util.List;

public class ProductResponse {
    private List<Product> products;

    public ProductResponse(List<Product> products) {
        this.products = products;
    }

    public ProductResponse() {
        products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }
}
