package com.mblog.logindemo.login;

import com.alibaba.fastjson.JSONObject;
import com.mblog.logindemo.utils.HttpClientUtil;

import java.util.HashMap;

public class CnLogin {
    static String loginUrl = "https://passport.weibo.cn/sso/login";
    public static void main(String[] args) {
        String userName = "13716772721";
        String pwd = "284868LCqaz";
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Accept", "*/*");
        headerMap.put("Accept-Language", "zh-CN,zh;q=0.9");
        headerMap.put("Connection", "keep-alive");
//		headerMap.put("Content-Length", "172");
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");
        headerMap.put("Host", "passport.weibo.cn");
        headerMap.put("Origin", "https://passport.weibo.cn");
        headerMap.put("Referer", " https://passport.weibo.cn/signin/login?entry=mweibo&r=https%3A%2F%2Fweibo.cn%2F%3Fluicode%3D10000011%26lfid%3D102803&backTitle=%CE%A2%B2%A9&vt=");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Safari/537.36");
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("username", userName);
        paramMap.put("password", pwd);
        paramMap.put("savestate", "1");
        paramMap.put("r", "https://weibo.cn");
        paramMap.put("ec", "0");
        paramMap.put("entry", "mweibo");
        paramMap.put("wentry", "");
        paramMap.put("loginfrom", "");
        paramMap.put("client_id", "");
        paramMap.put("code", "");
        paramMap.put("qq", "");
        paramMap.put("mainpageflag", "");
        paramMap.put("hff", "");
        paramMap.put("hfp", "");
        JSONObject json = HttpClientUtil.post(headerMap, paramMap, null, loginUrl, 0);
        System.out.println(json);
    }
}
