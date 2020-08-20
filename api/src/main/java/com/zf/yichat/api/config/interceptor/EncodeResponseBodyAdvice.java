package com.zf.yichat.api.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zf.yichat.service.config.ConfigSet;
import com.zf.yichat.utils.common.AesUtil;
import com.zf.yichat.utils.common.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 11:57 2019/8/26 2019
 */
@RestControllerAdvice(basePackages = "com.zf.yichat.api.controller")//要扫描的包
public class EncodeResponseBodyAdvice implements ResponseBodyAdvice {

    @Autowired
    private ConfigSet configSet;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        try {
            //如果header中含有 none_aes=1则加密
            if (serverHttpRequest.getHeaders().containsKey("none-aes")) {
                List<String> list = serverHttpRequest.getHeaders().get("none-aes");
                if (GeneralUtils.isNotNullOrEmpty(list) && StringUtils.equals(list.get(0), "1")) {
                    return AesUtil.encrypt(JSON.toJSONString(o, SerializerFeature.WriteMapNullValue), configSet.getSecurity().getAesKey().getAuth());
                }
            }

            //注意 web端是不需要加密的
            return o;
            //return AesUtil.encrypt(JSON.toJSONString(o), configSet.getSecurity().getAesKey().getAuth());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
