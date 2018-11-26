package com.hordiienko.weeteamshop.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private int id;
    private int defaultImageId;
    private String reference;
    private float price;
    private String name;
    private String description;

    public Product(int id, int defaultImageId, String reference, float price, String name, String description) {
        this.id = id;
        this.defaultImageId = defaultImageId;
        this.reference = reference;
        this.price = price;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getDefaultImageId() {
        return defaultImageId;
    }

    public String getReference() {
        return reference;
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Product(Parcel in) {
        id = in.readInt();
        defaultImageId = in.readInt();
        reference = in.readString();
        price = in.readFloat();
        name = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(defaultImageId);
        dest.writeString(reference);
        dest.writeFloat(price);
        dest.writeString(name);
        dest.writeString(description);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
