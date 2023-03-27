package com.itstep.yuliandr.socNtW.soc_network.security;

public class ConstanceSec {

public static final String SIGN_UP_PATH="api/auth/**";//путь к авторизации
    public static final String KEY = "SecretKeyGenJWT";//ключ для шифрования токена
    public static final String TOKEN_PREFIX = "Bearer ";//на предъявителя
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final long EXPIRATION_TIME = 900_000; //15 мин до обновления токена - log out

}
