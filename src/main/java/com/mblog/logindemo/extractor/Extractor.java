package com.mblog.logindemo.extractor;

import com.alibaba.fastjson.JSONObject;
import com.mblog.logindemo.pojo.PreLogin;

/**
 * @Author: lc
 * @Description:
 * @Date: Create in 15:53 2019/11/11
 */
public class Extractor {

    /**
     * 预登陆抽取
     * @param html
     * @return
     */
    public static PreLogin preLoginExtra(String html){
        PreLogin preLogin = new PreLogin();
        String resp = html.substring(html.indexOf("{"),html.indexOf("}")+1);
        JSONObject respJson = JSONObject.parseObject(resp);
        String pcid = respJson.getString("pcid");
        if(null != pcid && pcid.length() > 0){
            preLogin = new PreLogin(respJson.getString("nonce"), respJson.getString("pubkey"),
                    respJson.getString("rsakv"),1,respJson.getString("pcid"));
        } else {
            preLogin = new PreLogin(respJson.getString("nonce"), respJson.getString("pubkey"),
                    respJson.getString("rsakv"),0,null);
        }

        return preLogin;
    }
}
