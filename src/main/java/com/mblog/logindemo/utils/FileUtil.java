package com.mblog.logindemo.utils;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @Author: lc
 * @Description:
 * @Date: Create in 18:17 2019/11/11
 */
public class FileUtil {

    public static String readfile(String path){
        String content = "";
        BufferedReader brname;
        try {
            brname = new BufferedReader(new FileReader("encrypt.js"));
            String sname = null;
            StringBuilder bu = new StringBuilder();
            while ((sname = brname.readLine()) != null) {
                bu.append(sname.trim());
            }
            brname.close();
            content = bu.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
}
