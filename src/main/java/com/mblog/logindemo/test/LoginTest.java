package com.mblog.logindemo.test;

import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class LoginTest {
    public static void main(String[] args) {
        LocalCookieJar cookieJar = new LocalCookieJar();
        OkHttpClient client = (OkHttpClient) new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse(" application/x-www-form-urlencoded");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("username", " 13716772721")
                .addFormDataPart("password", " 284868LCqaz")
                .addFormDataPart("savestate", " 1")
                .addFormDataPart("r", " https://weibo.cn/?luicode=10000011&lfid=102803")
                .addFormDataPart("ec", " 0")
                .addFormDataPart("pagerefer", " https://weibo.cn/pub/")
                .addFormDataPart("entry", " mweibo")
                .addFormDataPart("wentry", " ")
                .addFormDataPart("loginfrom", " ")
                .addFormDataPart("client_id", " ")
                .addFormDataPart("code", " ")
                .addFormDataPart("qq", " ")
                .addFormDataPart("mainpageflag", " 1")
                .addFormDataPart("hff", " ")
                .addFormDataPart("hfp", "")
                .build();
        Request request = new Request.Builder()
                .url("https://passport.weibo.cn/sso/login")
                .method("POST", body)
                .addHeader("Accept", " */*")
                .addHeader("Accept-Encoding", " gzip, deflate, br")
                .addHeader("Accept-Language", " zh-CN,zh;q=0.9")
                .addHeader("Connection", " keep-alive")
                .addHeader("Content-Length", " 243")
                .addHeader("Content-Type", " application/x-www-form-urlencoded")
                .addHeader("Host", " passport.weibo.cn")
                .addHeader("Origin", " https://passport.weibo.cn")
                .addHeader("Referer", " https://passport.weibo.cn/signin/login?entry=mweibo&r=https%3A%2F%2Fweibo.cn%2F%3Fluicode%3D10000011%26lfid%3D102803&backTitle=%CE%A2%B2%A9&vt=")
                .addHeader("User-Agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                .build();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody body1 = response.body();
            String str = body1.string();
            System.out.println(str);
            Headers  headers=response.headers();
            //获取cookie
            List<String> cookies=headers.values("Set-Cookie");
            System.out.println(cookies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class LocalCookieJar implements  CookieJar{

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            return null;
        }
    }
}
