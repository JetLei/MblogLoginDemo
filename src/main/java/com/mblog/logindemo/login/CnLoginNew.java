package com.mblog.logindemo.login;

import com.mblog.logindemo.utils.HttpClientUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CnLoginNew {
    public static void main(String[] args) throws  Exception {
        String url = "https://login.sina.com.cn/sso/login.php";
        url = "https://login.sina.com.cn/sso/login.php?url=https%3A%2F%2Fweibo.cn%2F&_rand=1579855156.9382&gateway=1&service=sinawap&entry=sinawap&useticket=1&returntype=META&sudaref=&_client_version=0.6.33";
        String userName = "15183313767";
        String pwd = "sz814531";
        Connection con = Jsoup.connect(url);
        con.header("Accept", "*/*");
        con.header("Accept-Language", "zh-CN,zh;q=0.9");
        con.header("Connection", "keep-alive");
//        con.header("Content-Length", "172");
        con.header("Content-Type", "application/x-www-form-urlencoded");
        con.header("Host", "login.sina.com.cn");
//        con.header("Origin", "https://passport.weibo.cn");
        con.header("Cookie", "ALC=ac%3D27%26bt%3D1580382434%26cv%3D5.0%26et%3D1611918434%26ic%3D-546806125%26login_time%3D1580382434%26scf%3D%26uid%3D7365715865%26vf%3D0%26vs%3D0%26vt%3D0%26es%3D8883059bdfd43dec28ebfd4203a232a1;ALF=1611918434;LT=1580382434;SCF=AtiPOoft1XxoUQCSX9PZSK-V-iQZCgN-gl-WxZgooofs_nzbv0W3f4509J2X3iThbUSGY_kMhWDpvNZqmHhTBaA.;SUB=_2A25zNsiyDeRhGeFN7VcW8SvEzTmIHXVQRb16rDV_PUNbm9ANLVrwkW9NQ-CKU2BxtD0PhIOdxClkBUqrL1LABaU7;SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFqwFCTbsB03laAZq8AdxKq5NHD95QNe0qfS02f1hqfWs4DqcjUi--fi-2Xi-2Ni--fi-88i-zcUsvj9PiydrBfe05t;login=1312c9c574002583683776fe4e1c1b2a;sso_info=v02m6alo5qztKWRk5SlkJOQpZCTnKWRk5SlkJSUpZCjmbSbtrGlnKaFrp2DlLOMgpm1mpaQvY2zjLaNk5yxjZOgto2QwMA=;tgc=TGT-NzM2NTcxNTg2NQ==-1580382434-yf-4EE09F0D020B7801226BEC74E114C351-1;");
        con.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Safari/537.36");

//        con.data("username", userName);
//        con.data("password", pwd);
//        con.data("savestate", "1");
//        con.data("r", "https://weibo.cn");
//        con.data("ec", "0");
//        con.data("entry", "mweibo");
//        con.data("wentry", "");
//        con.data("loginfrom", "");
//        con.data("client_id", "");
//        con.data("code", "");
//        con.data("qq", "");
//        con.data("mainpageflag", "");
//        con.data("hff", "");
//        con.data("hfp", "");
        con.method(Connection.Method.GET);
        Connection.Response resp = con.execute();
        StringBuffer buffer = new StringBuffer();
        Map<String, String> map = resp.cookies();
//        System.out.println(map);
        Set<String> key = map.keySet();
        for(String k : key){
            buffer.append(k+"="+map.get(k)+";");
        }
        System.out.println(buffer);
//        Document doc = resp.parse();
//        System.out.println(doc.text());
    } 
}
