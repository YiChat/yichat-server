package com.zf.yichat.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.zf.yichat.mapper.SmsMapper;
import com.zf.yichat.model.Sms;
import com.zf.yichat.service.SmsService;
import com.zf.yichat.service.config.ConfigSet;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.OKHttpUtil;
import com.zf.yichat.utils.common.SmsUtils;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import com.zf.yichat.vo.ApiConst;
import com.zf.yichat.vo.SmsClient;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:01 2019/7/31 2019
 */
@Service
public class SmsServiceImpl implements SmsService {

    private static Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
    @Autowired
    private SmsMapper smsMapper;
    @Autowired
    private ConfigSet configSet;

    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = reader.readLine();
            if (strRead != null) {
                sbf.append(strRead);
                while ((strRead = reader.readLine()) != null) {
                    sbf.append("\n");
                    sbf.append(strRead);
                }
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String md5(String plainText) {
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    public static String encodeUrlString(String str, String charset) {
        String strret = null;
        if (str == null)
            return str;
        try {
            strret = java.net.URLEncoder.encode(str, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return strret;
    }

    @Override
    public void save(String mobile, String code) {

        Sms sms = new Sms();
        sms.setMobile(mobile);
        sms.setCode(code);
        sms.setStatus(0);

        smsMapper.insertSelective(sms);
    }

    @Override
    public FsResponse check(String mobile, String code) {

        Example example = new Example(Sms.class);
        example.createCriteria().andEqualTo("mobile", mobile).andEqualTo("status", 0);
        example.setOrderByClause(" ctime desc limit 1");
        Sms sms = smsMapper.selectOneByExample(example);

        if (Objects.nonNull(sms)) {
            if (Optional.ofNullable(sms.getCheckTime()).orElse(0) > 2) {
                return FsResponseGen.successData(YiChatMsgCode.SMS_CHECK_TIME);
            }

            //5分钟内未可用日期
            if ((sms.getCtime().getTime() + 5 * 60 * 1000) > System.currentTimeMillis()) {
                return FsResponseGen.successData(YiChatMsgCode.SMS_OVERTIME);
            }

            //验证次数+1
            sms.setCheckTime(Optional.ofNullable(sms.getCheckTime()).orElse(0) + 1);


            //5分钟内未可用日期
            if (StringUtils.equals(sms.getCode(), code)) {
                smsMapper.updateByPrimaryKeySelective(sms);
                return FsResponseGen.successData(YiChatMsgCode.SMS_CHECK_VALID);
            }

            sms.setStatus(1);
            smsMapper.updateByPrimaryKeySelective(sms);
            return FsResponseGen.success();

        } else {
            //为空则没有可验证短信
            return FsResponseGen.successData(YiChatMsgCode.SMS_CHECK_FAILED);
        }

    }

    @Override
    public boolean send(String mobile, String content) {
        if (ApiConst.client == null) {
            System.out.println("没有初始化可用的短信平台");
            return false;
        }
        switch (ApiConst.client) {
            case yunpian:

                content = SmsUtils.CODE_TEXT.replace("{}", content);
                CloseableHttpClient client = HttpClients.createDefault();
                String responseText = "";
                CloseableHttpResponse response = null;

                Map<String, String> paramsMap = new HashMap();

                paramsMap.put("apikey", configSet.getSms().getAppKey());
                paramsMap.put("mobile", mobile);
                paramsMap.put("text", content);

                try {
                    HttpPost method = new HttpPost(ApiConst.client.getUrl());
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
            case huyi:
                HttpClient cc = new HttpClient();
                PostMethod method = new PostMethod(ApiConst.client.getUrl());

                cc.getParams().setContentCharset("GBK");
                method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");

                content = new String("您的验证码是：" + content + "。请不要把验证码泄露给其他人。");

                org.apache.commons.httpclient.NameValuePair[] data = {//提交短信
                        new org.apache.commons.httpclient.NameValuePair("account", configSet.getSms().getAppId()), //查看用户名 登录用户中心->验证码通知短信>产品总览->API接口信息->APIID
                        new org.apache.commons.httpclient.NameValuePair("password", configSet.getSms().getAppKey()), //查看密码 登录用户中心->验证码通知短信>产品总览->API接口信息->APIKEY
                        //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
                        new org.apache.commons.httpclient.NameValuePair("mobile", mobile),
                        new org.apache.commons.httpclient.NameValuePair("content", content),
                };
                method.setRequestBody(data);

                try {
                    cc.executeMethod(method);

                    String SubmitResult = method.getResponseBodyAsString();

                    //System.out.println(SubmitResult);

                    Document doc = DocumentHelper.parseText(SubmitResult);
                    Element root = doc.getRootElement();

                    String code = root.elementText("code");
                    String msg = root.elementText("msg");
                    String smsid = root.elementText("smsid");

                    System.out.println(code);
                    System.out.println(msg);
                    System.out.println(smsid);

                    if ("2".equals(code)) {
                        System.out.println("短信提交成功");
                        return true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            case aliyun:
                DefaultProfile profile = DefaultProfile.getProfile(configSet.getSms().getRegion(), configSet.getSms().getAppId(), configSet.getSms().getAppKey());
                IAcsClient alc = new DefaultAcsClient(profile);

                CommonRequest request = new CommonRequest();
                request.setMethod(MethodType.POST);
                request.setDomain("dysmsapi.aliyuncs.com");
                request.setVersion("2017-05-25");
                request.setAction("SendSms");
                request.putQueryParameter("RegionId", configSet.getSms().getRegion());
                request.putQueryParameter("PhoneNumbers", mobile);
                request.putQueryParameter("SignName", configSet.getSms().getSign());
                request.putQueryParameter("TemplateCode", configSet.getSms().getTemplate());
                request.putQueryParameter("TemplateParam", "{\"code\":\"" + content + "\"}");
                try {
                    CommonResponse alre = alc.getCommonResponse(request);
                    if (StringUtils.equals(JSON.parseObject(alre.getData()).getString("Code"), "OK")) {
                        return true;
                    } else {
                        logger.error("阿里云短信发送失败:{}", JSON.toJSONString(alre));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            case yunzhixun:
                String url = SmsClient.yunzhixun.getUrl().replace("{}", configSet.getSms().getAppId());
                String requestData = "{\"clientid\":\"" + configSet.getSms().getAppId() + "\",\"password\":\"" + configSet.getSms().getAppKey() + "\",\"mobile\":\"" + mobile + "\",\"smstype\":0,\"content\":\"" + configSet.getSms().getSign() + "您的验证码是" + content + "，如非本人操作，请忽略此条短信。\"}";
                String res = OKHttpUtil.httpPostJson(url, requestData, null);
                if (StringUtils.equals(JSON.parseObject(res).getJSONArray("data").getJSONObject(0).getString("code"), "0")) {
                    return true;
                } else {
                    logger.error("云之讯短信发送失败", res);
                    return false;
                }
            case duanxinbao:

                StringBuffer httpArg = new StringBuffer();
                httpArg.append("u=").append(configSet.getSms().getAppId()).append("&");
                httpArg.append("p=").append(md5(configSet.getSms().getAppKey())).append("&");
                httpArg.append("m=").append(mobile).append("&");
                httpArg.append("c=").append(encodeUrlString("【" + configSet.getSms().getSign() + "】您的验证码是" + content + ".若非本人操作请忽略此消息。", "UTF-8"));

                String result = request(SmsClient.duanxinbao.getUrl(), httpArg.toString());

                if (StringUtils.equals("0", result)) {
                    return true;
                } else {
                    logger.error("短信宝短信发送失败,{}", result);
                    return false;
                }


            default:
                return false;
        }


    }
}
