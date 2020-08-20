package com.zf.yichat.vo;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:17 2019/8/28 2019
 */
public enum SmsClient {
    yunpian("https://sms.yunpian.com/v2/sms/single_send.json", "云片"),
    huyi("http://106.ihuyi.com/webservice/sms.php?method=Submit", "互亿"),
    aliyun("http://106.ihuyi.com/webservice/sms.php?method=Submit", "阿里云短信平台"),
    yunzhixun("http://Request.ucpaas.com/sms-partner/access/{}/sendsms", "云之讯短信"),
    duanxinbao("http://api.smsbao.com/sms", "短信宝");

    private String url;
    private String desc;


    SmsClient(String url, String desc) {
        this.url = url;
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}