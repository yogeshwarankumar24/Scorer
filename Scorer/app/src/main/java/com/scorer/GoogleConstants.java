package com.scorer;

/**
 * Created by Android on 11/27/2017.
 */

public class GoogleConstants {
    public static String CLIENT_ID = "956762772902-7bbv4eete1tnsilk6j1tiqaifqu44th0.apps.googleusercontent.com";
    // Use your own client id

    public static String CLIENT_SECRET = "zR4NQ70JfcUz7eX1eZvGAYdG";
    // Use your own client secret

    public static String REDIRECT_URI = "https://scorer-186811.firebaseapp.com/__/auth/handler";
    public static String GRANT_TYPE = "authorization_code";
    public static String TOKEN_URL = "https://accounts.google.com/o/oauth2/token";
    public static String OAUTH_URL = "https://accounts.google.com/o/oauth2/auth";
    public static String OAUTH_SCOPE = "https://www.googleapis.com/auth/contacts.readonly";

    public static final String CONTACTS_URL = "https://www.google.com/m8/feeds/contacts/default/full";
    public static final int MAX_NB_CONTACTS = 1000;
    public static final String APP = "com.scorer";

   // https://accounts.google.com/o/oauth2/auth?redirect_uri=https://scorer-186811.firebaseapp.com/__/auth/handler&response_type=code&client_id=956762772902-sn8h7qtiu5j7ellvcfth3jviu4ddu5p5.apps.googleusercontent.com&scope=https://www.googleapis.com/auth/contacts.readonly

}
