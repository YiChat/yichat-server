package com.zf.yichat.utils.common;

import com.squareup.okhttp.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * 利用okhttp进行get和post的访问
 *
 * @author cp
 */
public class OKHttpUtil {

    private static OkHttpClient client = new OkHttpClient();

    /**
     * 发起get请求
     *
     * @param url
     * @return
     */
    public static String httpGet(String url) {
        String result = null;
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发起get请求
     *
     * @param request
     * @return
     */
    public static String httpGet(Request request) {
        String result = null;
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发起get请求
     *
     * @param url
     * @return
     */
    public static InputStream httpGetStream(String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().byteStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送httppost请求
     *
     * @param url
     * @param data 提交的参数为key=value&key1=value1的形式
     * @return
     */
    public static String httpPost(String url, String data) {
        String result = null;
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/html;charset=utf-8"), data);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送httppost JSON请求
     *
     * @param url
     * @param data 提交的参数为key=value&key1=value1的形式
     * @return
     */
    public static String httpPostJson(String url, String data) {
        String result = null;
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), data);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送httppost JSON请求
     *
     * @param url
     * @param data 提交的参数为key=value&key1=value1的形式
     * @return
     */
    public static String httpPostJson(String url, String data, Map<String, String> header) {
        String result = null;
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), data);
        Request.Builder builder = new Request.Builder();
        if (Objects.nonNull(header)) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送httppost JSON请求
     */
    public static String httpPostJson(Request request) {
        String result = null;
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {

        Map<String, String> head = new HashMap<>();
        Map<String, String> result = new HashMap<>();
        //String data = "{\"command\":{\"fields\":[{\"value\":\"14001414\",\"var\":\"accountjid\"},{\"value\":\"678749\",\"var\":\"password\"}],\"node\":\"http://jabber.org/protocol/admin#add-user\",\"success\":false}}";
        String dada = "{\"action\":\"checkUserLogin\",\"username\":\"checkUserLogin\",\"action\":\"checkUserLogin\"}";


        for (int i = 0; i < 20; i++) {
            System.out.println(httpPostJson("" +
                            "http://60.173.202.196:1010/v6engine/login.do"
                    , dada, result));
        }

    }

}