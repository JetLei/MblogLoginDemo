package com.mblog.logindemo.login;

import com.alibaba.fastjson.JSONObject;
import com.mblog.logindemo.extractor.Extractor;
import com.mblog.logindemo.prop.RequestHeaderconfig;
import com.mblog.logindemo.utils.FileUtil;
import com.mblog.logindemo.utils.HttpClientUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import sun.misc.BASE64Encoder;
import com.mblog.logindemo.pojo.PreLogin;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * @Author: lc
 * @Description: 微博登录
 * @Date: Create in 15:05 2019/11/11
 */
public class ComLogin {
    public static final Logger logger = Logger.getLogger(ComLogin.class);
//    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36";

    public static void main(String[] args) {
        PropertyConfigurator.configureAndWatch("config" + File.separator + "log4j.properties");
        String preUrl = "https://login.sina.com.cn/sso/prelogin.php?entry=weibo&callback=sinaSSOController.preloginCallBack&su=<username>&rsakt=mod&checkpin=1&client=ssologin.js(v1.4.19)";
        String username = "13467429999";
        String pwd = "lnwxea443";
        String su = new BASE64Encoder().encode(username.getBytes());
        PreLogin preLogin = preLogin(preUrl, su);
        System.out.println(preLogin);
        String s = rsaPassword(pwd, preLogin.getNonce(), preLogin.getPubkey());
        logger.info(s);
        JSONObject cookieJson = LoginAcoount(preLogin, su, pwd);
    }

    /**
     * 密码加密
     * @param pwd
     * @param nonce
     * @param rsaPubkey
     * @return
     */
    private static String rsaPassword(String pwd, String nonce, String rsaPubkey) {
        long serviceTime = System.currentTimeMillis() / 1000;
        String content = FileUtil.readfile("encrypt.js");
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        String encryptPassword = null;
        try{
            engine.eval(content);
            if (engine instanceof Invocable) {
                Invocable in = (Invocable) engine;
                encryptPassword = in.invokeFunction("getpassword", pwd, serviceTime + "", nonce, rsaPubkey).toString();

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return encryptPassword;
    }

    /**
     * 登录
     * @param preLogin
     * @param su
     */
    private static JSONObject LoginAcoount(PreLogin preLogin,String su,String pwd) {
        String loginUrl = "https://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.19)";
        String encryptPwd = rsaPassword(pwd, preLogin.getNonce(), preLogin.getPubkey());
        long servertime = System.currentTimeMillis() / 1000;
        HashMap<String, String> paramMap = RequestHeaderconfig.headMap;
        paramMap.put("nonce",preLogin.getNonce());
        paramMap.put("rsakv",preLogin.getRsakv());
        paramMap.put("servertime",servertime+"");
        paramMap.put("sp",encryptPwd);
        paramMap.put("su",su);
        String pcid = preLogin.getPcid();
        if(null != pcid && pcid.length() > 0){
            //需要输入验证码
            String captchaStr = delphi(pcid);
            paramMap.put("door",captchaStr);
            paramMap.put("pcid",pcid);
        }
//        HashMap<String, String> loginMap = RequestHeaderconfig.loginMap;
        HashMap<String, String> loginMap = new HashMap<String,String>();
        loginMap.put("User-Agent", userAgent);
        JSONObject result = HttpClientUtil.post(loginMap, paramMap, null, loginUrl, 3);
//        System.out.println(result);
        return result;
    }

    /**
     * 获取验证码
     * @param pcid
     */
    private static String delphi(String pcid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("User-Agent", userAgent);
        map.put("Referer", "https://weibo.com");
        String captchaUrl = "https://login.sina.com.cn/cgi/pin.php?r=86006630&s=0&p="+pcid;
        HttpClientUtil.httpGetImg(map,null,captchaUrl,3);
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream("captcha.png");
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String len_min = "0";
//        下面几行是打码平台的验证码识别接口，可以根据自己的方式修改
//        String pic = ChaoJiYing.PostPic();
//        JSONObject json = JSONObject.parseObject(pic);
//        String pic_str = json.getString("pic_str");
        String pic_str = null;
        return pic_str;
    }

    /**
     * 预登陆
     * @param preUrl
     * @return
     */
    private static PreLogin preLogin(String preUrl,String username) {
        preUrl = preUrl.replace("<username>",username);
        HashMap<String, String> map = new HashMap<>();
        map.put("User-Agent", userAgent);
        String resp = HttpClientUtil.get(map, null, preUrl, 3);
        PreLogin preLogin = Extractor.preLoginExtra(resp);
        return preLogin;
    }
}
