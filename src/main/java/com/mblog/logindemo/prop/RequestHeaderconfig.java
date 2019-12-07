package com.mblog.logindemo.prop;

import java.util.HashMap;

/**
 * @Author: lc
 * @Description:
 * @Date: Create in 15:31 2019/11/11
 */
public class RequestHeaderconfig {
    public static String UserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Safari/537.36";
    public static HashMap<String,String> headMap = new HashMap<String,String>();
    public static HashMap<String,String> loginMap = new HashMap<String,String>();
    static {
        headMap.put("encoding","UTF-8");
        headMap.put("entry","weibo");
        headMap.put("from","");
        headMap.put("gateway","1");
//        无验证码
        headMap.put("pagerefer","https://login.sina.com.cn/crossdomain2.php?action=logout&r=https%3A%2F%2Fweibo.com%2Flogout.php%3Fbackurl%3D%252F");
//        headMap.put("pagerefer","https://login.sina.com.cn/crossdomain2.php?action=logout&r=https%3A%2F%2Fpassport.weibo.com%2Fwbsso%2Flogout%3Fr%3Dhttps%253A%252F%252Fweibo.com%26returntype%3D1");
        headMap.put("prelt","22");
        headMap.put("pwencode","rsa2");
        headMap.put("qrcode_flag","false");
        headMap.put("returntype","META");
        headMap.put("savestate","7");
        headMap.put("service","miniblog");
        headMap.put("sr","1920*1080");
        headMap.put("gateway","1");
        headMap.put("url","https://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack");
        headMap.put("useticket","1");
        headMap.put("vsnf","1");


        loginMap.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        loginMap.put("Accept-Encoding","gzip, deflate, br");
        loginMap.put("Accept-Language","zh-CN,zh;q=0.9");
        loginMap.put("Cache-Control","max-age=0");
        loginMap.put("Connection","keep-alive");
        loginMap.put("Content-Type","application/x-www-form-urlencoded");
        loginMap.put("Host","login.sina.com.cn");
        loginMap.put("Origin","https://weibo.com");
        loginMap.put("Referer","https://weibo.com/");
        loginMap.put("Upgrade-Insecure-Requests","1");
//        loginMap.put("service","miniblog");
//        loginMap.put("sr","1920*1080");
//        loginMap.put("gateway","1");
//        loginMap.put("url","https://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack");
//        loginMap.put("useticket","1");
//        loginMap.put("vsnf","1");

    }
}
