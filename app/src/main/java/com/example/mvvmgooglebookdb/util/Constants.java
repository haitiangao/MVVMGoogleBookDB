package com.example.mvvmgooglebookdb.util;

public class Constants {

    //Error Messages
    static final String TAG = "TAG_H";
    static final String ERROR_PREFIX = "Error: ";
    public static final String RESULTS_NULL = "Results were null";

    //Retrofit call
    //"https://www.googleapis.com/books/v1/volumes?q={search term}&key={YOUR_API_KEY}”
    public static final String API_KEY = "AIzaSyDcbI1nywoh1Yud7irvxJ5FPImZl0KIWzg";
    public static final String GET_URL_POSTFIX = "/books/v1/volumes"; //
    public static final String BASE_URL = "https://www.googleapis.com/";
}
