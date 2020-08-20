package com.zf.yichat.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:08 2019/6/3 2019
 */
@Component
@ConfigurationProperties(prefix = "pay")
public class ConfigPay {


    private Weixin weixin;
    private Alipay alipay;

    public Weixin getWeixin() {
        return weixin;
    }

    public void setWeixin(Weixin weixin) {
        this.weixin = weixin;
    }

    public Alipay getAlipay() {
        return alipay;
    }

    public void setAlipay(Alipay alipay) {
        this.alipay = alipay;
    }

    public static class Weixin {
        private String appId;
        private String appKey;
        private String mchId;
        private String notifyUrl;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getMchId() {
            return mchId;
        }

        public void setMchId(String mchId) {
            this.mchId = mchId;
        }

        public String getNotifyUrl() {
            return notifyUrl;
        }

        public void setNotifyUrl(String notifyUrl) {
            this.notifyUrl = notifyUrl;
        }
    }

    public static class Alipay {

        private String appId;

        private String privateKey;
        private String publicKey;
        private String appPublicKey;
        private String notifyUrl;
        private String redirectUrl;

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        public String getAppPublicKey() {
            return appPublicKey;
        }

        public void setAppPublicKey(String appPublicKey) {
            this.appPublicKey = appPublicKey;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getNotifyUrl() {
            return notifyUrl;
        }

        public void setNotifyUrl(String notifyUrl) {
            this.notifyUrl = notifyUrl;
        }
    }
}
