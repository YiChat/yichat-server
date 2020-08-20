package com.zf.yichat.api.config.interceptor;

import com.zf.yichat.service.config.ConfigSet;
import com.zf.yichat.utils.common.AesUtil;
import com.zf.yichat.utils.common.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URLDecoder;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:30 2019/5/30 2019
 */
@ControllerAdvice
public class DecodeRequestBodyAdvice implements RequestBodyAdvice {


    private static Logger log = LoggerFactory.getLogger(DecodeRequestBodyAdvice.class);

    /**
     * aes key
     */
    @Autowired
    private ConfigSet configSet;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {

        //机器人接口不解密 方法包含Robot
        if (methodParameter.getMethod().getName().contains("Robot")) {
            return false;
        }
        //这里设置成false 它就不会再走这个类了
        return configSet.getSecurity().isDecode();
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        try {
            log.info("开始对接受值进行解密操作");
            // 定义是否解密
            HttpHeaders headers = httpInputMessage.getHeaders();
            log.info("对方法method :【" + methodParameter.getMethod().getName() + "】数据进行解密!");

            // 解密操作

            InputStream body = null;
            try {
                String result = AesUtil.decrypt(URLDecoder.decode(IOUtil.stream2str(httpInputMessage.getBody())), configSet.getSecurity().getAesKey().getAuth());
                log.info("解密结果 :【" + result + "】");
                //aes解密失败
                if (result == null) {
                    throw new Exception("aes解密失败");
                }
                body = IOUtil.str2Stream(result);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return new MyHttpInputMessage(headers, body);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("对方法method :【" + methodParameter.getMethod().getName() + "】数据进行解密出现异常：" + e.getMessage());
            throw new RuntimeException("对方法method :【" + methodParameter.getMethod().getName() + "】数据进行解密出现异常：" + e.getMessage());
        }
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }


    //这里实现了HttpInputMessage 封装一个自己的HttpInputMessage
    class MyHttpInputMessage implements HttpInputMessage {
        HttpHeaders headers;
        InputStream body;

        public MyHttpInputMessage(HttpHeaders headers, InputStream body) {
            this.headers = headers;
            this.body = body;
        }

        @Override
        public InputStream getBody() {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }

}
