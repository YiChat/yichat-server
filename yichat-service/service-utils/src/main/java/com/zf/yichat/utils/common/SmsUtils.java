package com.zf.yichat.utils.common;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:34 2019/7/16 2019
 */
public class SmsUtils {

    private static final String apikey = "37cb968b6f5d9a73c302a89555749aa2";
    public static String CODE_TEXT = "您的验证码是{}。如非本人操作，请忽略本短信";
    //发送单挑短信
    private static String sendUrl = "https://sms.yunpian.com/v2/sms/single_send.json";
    private static String Url = "http://106.ihuyi.com/webservice/sms.php?method=Submit";

    public static boolean send(String mobile, String content) {

        content = SmsUtils.CODE_TEXT.replace("{}", content);
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;

        Map<String, String> paramsMap = new HashMap();

        paramsMap.put("apikey", apikey);
        paramsMap.put("mobile", mobile);
        paramsMap.put("text", content);

        try {
            HttpPost method = new HttpPost(sendUrl);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<
                        NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(),
                            param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList,
                        "utf-8"));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity, "utf-8");
                return JSON.parseObject(responseText).getString("code").equals("0");
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {


    }


}
