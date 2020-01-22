package com.mblog.logindemo.login;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CnLogin {
    static String loginUrl = "https://passport.weibo.cn/sso/login";

    public static void main(String[] args) throws IOException {
        String userName = "username";
        String pwd = "password";
        Connection con = Jsoup.connect(loginUrl);
        con.header("Accept", "*/*");
        con.header("Accept-Language", "zh-CN,zh;q=0.9");
        con.header("Connection", "keep-alive");
//		con.header("Content-Length", "172");
        con.header("Content-Type", "application/x-www-form-urlencoded");
        con.header("Host", "passport.weibo.cn");
        con.header("Origin", "https://passport.weibo.cn");
        con.header("Referer", " https://passport.weibo.cn/signin/login?entry=mweibo&r=https%3A%2F%2Fweibo.cn%2F%3Fluicode%3D10000011%26lfid%3D102803&backTitle=%CE%A2%B2%A9&vt=");
        con.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Safari/537.36");

        con.data("username", userName);
        con.data("password", pwd);
        con.data("savestate", "1");
        con.data("r", "https://weibo.cn");
        con.data("ec", "0");
        con.data("entry", "mweibo");
        con.data("wentry", "");
        con.data("loginfrom", "");
        con.data("client_id", "");
        con.data("code", "");
        con.data("qq", "");
        con.data("mainpageflag", "");
        con.data("hff", "");
        con.data("hfp", "");
        con.method(Connection.Method.POST);
        Connection.Response resp = con.execute();
        Map<String, String> map = resp.cookies();
        System.out.println(map);
    }
}
