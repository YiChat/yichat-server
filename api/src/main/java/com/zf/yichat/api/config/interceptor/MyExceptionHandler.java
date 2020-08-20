package com.zf.yichat.api.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zf.yichat.service.config.ConfigSet;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.AesUtil;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 09:53 2019/5/30 2019
 */
@ControllerAdvice
public class MyExceptionHandler {

    @Autowired
    private ConfigSet configSet;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleServiceException(Exception exception) {
        exception.printStackTrace();
        if (exception.getMessage().contains("HV000116: ")) {

            String result = exception.getMessage().substring("HV000116: ".length());
            try {
                String code = result.split("=")[0];
                String msg = result.split("=")[1];
                return new ResponseEntity(JSON.toJSONString(AesUtil.encrypt(JSON.toJSONString(FsResponseGen.fail(code, msg), SerializerFeature.WriteMapNullValue), configSet.getSecurity().getAesKey().getAuth())), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    return new ResponseEntity(JSON.toJSONString(AesUtil.encrypt(JSON.toJSONString(FsResponseGen.fail("-1", result), SerializerFeature.WriteMapNullValue), configSet.getSecurity().getAesKey().getAuth())), HttpStatus.OK);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    return null;
                }
            }
        }

        String msg = exception.getMessage();
        try {
            if (StringUtils.equals("Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported", msg)) {

                return new ResponseEntity(JSON.toJSONString(AesUtil.encrypt(JSON.toJSONString(FsResponseGen.fail(YiChatMsgCode.SYSTEM_PARAM_HEADER_ERROR), SerializerFeature.WriteMapNullValue), configSet.getSecurity().getAesKey().getAuth())), HttpStatus.OK);

            } else if (msg.contains("No content to map due to end-of-input")) {
                return new ResponseEntity(JSON.toJSONString(AesUtil.encrypt(JSON.toJSONString(FsResponseGen.fail(YiChatMsgCode.SYSTEM_AES_ERROR), SerializerFeature.WriteMapNullValue), configSet.getSecurity().getAesKey().getAuth())), HttpStatus.OK);
            } else if (msg.contains("Cannot deserialize value of type")) {
                return new ResponseEntity(JSON.toJSONString(AesUtil.encrypt(JSON.toJSONString(FsResponseGen.fail(YiChatMsgCode.SYSTEM_PARAM_TYPE_ERROR), SerializerFeature.WriteMapNullValue), configSet.getSecurity().getAesKey().getAuth())), HttpStatus.OK);

            } else if (msg.contains("Required request body is missing")) {
                return new ResponseEntity(JSON.toJSONString(AesUtil.encrypt(JSON.toJSONString(FsResponseGen.fail(YiChatMsgCode.SYSTEM_BODY_MISS), SerializerFeature.WriteMapNullValue), configSet.getSecurity().getAesKey().getAuth())), HttpStatus.OK);

            }

            return new ResponseEntity(JSON.toJSONString(AesUtil.encrypt(JSON.toJSONString(FsResponseGen.fail(YiChatMsgCode.SYSTEM_ERROR), SerializerFeature.WriteMapNullValue), configSet.getSecurity().getAesKey().getAuth())), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity(null, HttpStatus.OK);

    }

    //其他异常
    //@org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<FsResponse> hadleServerException(Exception exception) {
        exception.printStackTrace();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String msg = "server error, please try again later";
        Class exceptionClazz = exception.getClass();
        if (Objects.equals(MissingServletRequestParameterException.class, exceptionClazz)) {
            msg = "incorrect parameter";
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (Objects.equals(HttpRequestMethodNotSupportedException.class, exceptionClazz)) {
            httpStatus = HttpStatus.BAD_REQUEST;
            msg = exception.getMessage();
        }
        return new ResponseEntity(FsResponseGen.failMsg("12313"), httpStatus);
    }
}
