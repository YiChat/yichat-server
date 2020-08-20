package com.zf.yichat.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:08 2019/6/3 2019
 */
@Component
@ConfigurationProperties(prefix = "config")
public class ConfigSet {


    private Security security;
    private Push push;
    private Sms sms;
    private Integer createGroupAuth;
    private boolean mobileAppIdStatus;
    private boolean supportAppIdLogin;
    private Boolean friendListSize;
    private Key key;
    private Search search;
    private Oss oss;
    private Packet packet;

    public Boolean getFriendListSize() {
        return friendListSize;
    }

    public void setFriendListSize(Boolean friendListSize) {
        this.friendListSize = friendListSize;
    }

    public boolean isSupportAppIdLogin() {
        return supportAppIdLogin;
    }

    public void setSupportAppIdLogin(boolean supportAppIdLogin) {
        this.supportAppIdLogin = supportAppIdLogin;
    }

    public boolean isMobileAppIdStatus() {
        return mobileAppIdStatus;
    }

    public void setMobileAppIdStatus(boolean mobileAppIdStatus) {
        this.mobileAppIdStatus = mobileAppIdStatus;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public Oss getOss() {
        return oss;
    }

    public void setOss(Oss oss) {
        this.oss = oss;
    }

    public Search getSearch() {
        return Optional.ofNullable(search).orElse(new Search());
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Integer getCreateGroupAuth() {
        return createGroupAuth;
    }

    public void setCreateGroupAuth(Integer createGroupAuth) {
        this.createGroupAuth = createGroupAuth;
    }

    public Sms getSms() {
        return sms;
    }

    public void setSms(Sms sms) {
        this.sms = sms;
    }

    public Push getPush() {
        return push;
    }

    public void setPush(Push push) {
        this.push = push;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public static class Oss {
        private String endpoint;
        private String appKey;
        private String secretKey;
        private String bucketName;

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }
    }

    public static class Security {

        //请求是否解密 调试专用
        private boolean decode;

        private AesKey aesKey;

        public boolean isDecode() {
            return decode;
        }

        public void setDecode(boolean decode) {
            this.decode = decode;
        }

        public AesKey getAesKey() {
            return aesKey;
        }

        public void setAesKey(AesKey aesKey) {
            this.aesKey = aesKey;
        }
    }


    public static class Search {
        private boolean closeAppId;

        public boolean isCloseAppId() {
            return closeAppId;
        }

        public void setCloseAppId(boolean closeAppId) {
            this.closeAppId = closeAppId;
        }
    }

    public static class AesKey {

        //请求数据加密
        private String auth;
        //登陆鉴权使用
        private String token;

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public static class Push {//推送数据
        private boolean status;
        private String key;
        private String secret;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }

    //校验数据
    public static class Key {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class Packet {
        private Integer effectHour;

        public Integer getEffectHour() {
            return effectHour;
        }

        public void setEffectHour(Integer effectHour) {
            this.effectHour = effectHour;
        }
    }

    public static class Sms {//短信平台
        private String client;
        private String appId;
        private String appKey;
        private String sign;
        private String region;
        private String template;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

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
    }
}
