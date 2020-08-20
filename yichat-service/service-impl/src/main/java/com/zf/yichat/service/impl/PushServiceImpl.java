package com.zf.yichat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zf.yichat.service.PushService;
import com.zf.yichat.service.config.ConfigSet;
import com.zf.yichat.utils.common.OKHttpUtil;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.validator.internal.util.Contracts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:00 2019/8/26 2019
 */
@Service
public class PushServiceImpl implements PushService {


    private String url = "https://bjapi.push.jiguang.cn/v3/push";


    @Autowired
    private ConfigSet configSet;


    private Logger logger = LoggerFactory.getLogger(PushServiceImpl.class);

    public static void main(String[] args) {
        //new PushServiceImpl().sendSingle(14012455L, "测试推送", "我是推送内容！");
        //new PushServiceImpl().sendGroup(10025, "测试群推送", "群我是推送内容！");
        //System.out.println(URLDecoder.decode("%7B%22type%22%3A2000%2C%22data%22%3A%7B%22body%22%3A%7B%22content%22%3A%22%E6%B5%8B%E8%AF%95%E8%81%8A%E5%A4%A9%E8%AE%B0%E5%BD%95%E4%B8%8A%E4%BC%A0%EF%BC%8C%E5%8D%95%E8%81%8A%22%2C%22videoDuration%22%3A0%2C%22timestamp%22%3A0%2C%22longitude%22%3A0%2C%22latitude%22%3A0%7D%2C%22timestamp%22%3A1566815120923%2C%22from%22%3A%2214012445%22%2C%22msgType%22%3A2001%2C%22downLoadState%22%3A2%2C%22chatType%22%3A%221%22%2C%22ext%22%3A%7B%22nick%22%3A%22%E5%81%A5%E5%81%A5%E5%BA%B7%E5%BA%B7%E4%BA%86%E5%93%88%E5%93%88%22%2C%22userId%22%3A%2214012445%22%2C%22avatar%22%3A%22http%3A%5C%2F%5C%2Ffanxin-file-server.oss-cn-shanghai.aliyuncs.com%5C%2FE99FBF84-070E-4EC7-9FF2-58055B524AD1.png%22%7D%2C%22sendState%22%3A2%2C%22isSender%22%3Atrue%2C%22msgId%22%3A%220A77F7D2-0AE9-44B8-A68F-EB3AC15C60FF%22%2C%22to%22%3A%2214014449%22%7D%7D"));
    }

    @Async
    @Override
    public void sendAll(String title, String content) {

        if (!configSet.getPush().isStatus()) {
            logger.debug("推送状态关闭");
            return;
        }

        String data = "{\n" +
                "   \"platform\": [\"ios\"]," +
                "   \"audience\" : \"all\"," +
                "   \"options\" : {\"apns_production\":\"True\"}," +
                "   \"notification\" : {\n" +
                "      \"alert\" :{\"title\":\"" + title + "\",\"body\":\"" + content + "\"}," +
                "      \"ios\" : {" +
                "         \"badge\" :  1," +
                "         \"sound\" :  \"default\"" +
                "} \n" +

                "   }\n" +
                "}";
        Map<String, String> header = new HashMap<>();
        try {
            header.put("Content-Type", "application/json");
            header.put("Authorization", "Basic " + new Base64().encodeToString((configSet.getPush().getKey() + ":" + configSet.getPush().getSecret()).getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String res = OKHttpUtil.httpPostJson(url, data, header);
        JSONObject object = JSON.parseObject(res);
        Contracts.assertTrue(org.apache.commons.lang3.StringUtils.isBlank(object.getString("error")), "发送失败！" + object.getJSONObject("error").getString("code"));
    }

    @Override
    @Async
    public void sendGroup(Integer groupId, String title, String content) {
        if (!configSet.getPush().isStatus()) {
            logger.debug("推送状态关闭");
            return;
        }
        String data = "{\n" +
                "   \"platform\": [\"ios\"]," +
                "   \"options\" : {\"apns_production\":\"True\"}," +
                "   \"audience\" : {\"tag_and\":[\"" + groupId + "\"]}," +
                "   \"notification\" : {\n" +
                "      \"alert\" :{\"title\":\"" + title + "\",\"body\":\"" + content + "\"}," +
                "      \"ios\" : {" +
                "         \"badge\" :  1," +
                "         \"sound\" :  \"default\"" +
                "} \n" +
                "   }\n" +
                "}";
        Map<String, String> header = new HashMap<>();
        try {
            header.put("Content-Type", "application/json");
            header.put("Authorization", "Basic " + new Base64().encodeToString((configSet.getPush().getKey() + ":" + configSet.getPush().getSecret()).getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String res = OKHttpUtil.httpPostJson(url, data, header);

        System.out.println(res);
        JSONObject object = JSON.parseObject(res);

        //Contracts.assertTrue(!object.containsKey("error"), "群推送发送失败");


    }

    @Async
    @Override
    public void sendSingle(Long userId, String title, String content) {
        if (!configSet.getPush().isStatus()) {
            logger.debug("推送状态关闭");
            return;
        }
        //title = userService.selectById(userId).getNick();
        String data = "{\n" +
                "   \"platform\": [\"ios\"]," +
                "   \"options\" : {\"apns_production\":\"True\"}," +
                "   \"audience\" : {\"alias\":[\"" + userId + "\"]}," +
                "   \"notification\" : {\n" +
                "      \"alert\" :{\"title\":\"" + title + "\",\"body\":\"" + content + "\"}," +
                "      \"ios\" : {" +
                "         \"badge\" :  1," +
                "         \"sound\" :  \"default\"" +
                "} \n" +

                "   }\n" +
                "}";
        Map<String, String> header = new HashMap<>();
        try {
            header.put("Content-Type", "application/json");
            header.put("Authorization", "Basic " + new Base64().encodeToString((configSet.getPush().getKey() + ":" + configSet.getPush().getSecret()).getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String res = OKHttpUtil.httpPostJson(url, data, header);
        System.out.println(res);

        JSONObject object = JSON.parseObject(res);

        //Contracts.assertTrue(!object.containsKey("error"), "单人推送失败");

    }
}
