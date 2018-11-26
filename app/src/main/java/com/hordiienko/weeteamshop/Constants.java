package com.hordiienko.weeteamshop;

import okhttp3.Credentials;

public class Constants {
    public static final String API_BASE_URL = "http://ps1722.weeteam.net/";
    public static final String API_IMAGE_URL = "http://ps1722.weeteam.net/api/images/products/%d/%d";
    private static final String API_USER = "XHKM6A6BLCA5MNYZQBX2GXBAAKSTPMK2";
    private static final String API_PASSWORD = "";
    public static final String API_CREDENTIALS = Credentials.basic(API_USER, API_PASSWORD);
    public static final int API_PAGE_SIZE = 20;
}
