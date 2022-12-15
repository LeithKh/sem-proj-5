package com.example.semproj.server;

public class Const {
    public static final String USER_TABLE = "users";
    public static final String USERS_ID = "idUsers";
    public static final String USERS_FIRSTNAME = "firstname";
    public static final String USERS_LASTNAME = "lastname";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";
    public static final String USER_ADMINCODE = "admincode";
    public static final String USER_BALANCE = "balance";
    public static final String USER_LOCKED = "locked";

    public static final String PRODUCT_TABLE = "Products";
    public static final String PRODUCTS_ID = "idProducts";
    //public static final String PRODUCTS_CARNAME = "CarName";
    public static final String PRODUCTS_SEGMENT = "Segment";
    public static final String PRODUCTS_COST = "Cost";
    public static final String PRODUCTS_COUNT = "Count";
    public static final String PRODUCTS_GRADE = "Grade";

    public static final String WISHLIST_TABLE = "UserWishList";
    //public static final String WISHLIST_ID = "idWishList";
    public static final String WISHLIST_USERSID = "idUsers";
    public static final String WISHLIST_PRODUCTSID = "idProducts";

    public static final String COMMENT_TABLE = "Comments";
   // public static final String COMMENTS_ID = "idComments";
    public static final String COMMENTS_USERSID = "idUsers";
    public static final String COMMENTS_PRODUCTSID = "idProducts";
    public static final String COMMENTS_COMMENT= "Comment";

    public static final String REQUEST_TABLE = "RequestUpBalance";
    //public static final String REQUESTS_ID = "idRequest";
    public static final String REQUESTS_USERSID = "idUsers";
    public static final String REQUESTS_REQUESTBALANCE = "RequestBalance";


}
