package com.mblog.logindemo.pojo;

/**
 * @Author: lc
 * @Description:
 * @Date: Create in 16:04 2019/11/11
 */
public class PreLogin {
    private String nonce;
    private String pubkey;
    private String rsakv;
    private int showpin;
    private String pcid;

    public PreLogin() {
    }

    @Override
    public String toString() {
        return "PreLogin{" +
                "nonce='" + nonce + '\'' +
                ", pubkey='" + pubkey + '\'' +
                ", rsakv='" + rsakv + '\'' +
                ", showpin=" + showpin +
                ", pcid='" + pcid + '\'' +
                '}';
    }

    public String getNonce() {
        return nonce;
    }

    public String getPubkey() {
        return pubkey;
    }

    public String getRsakv() {
        return rsakv;
    }

    public int getShowpin() {
        return showpin;
    }

    public String getPcid() {
        return pcid;
    }

    public PreLogin(String nonce, String pubkey, String rsakv, int showpin, String pcid) {
        this.nonce = nonce;
        this.pubkey = pubkey;
        this.rsakv = rsakv;
        this.showpin = showpin;
        this.pcid = pcid;
    }
}
