package com.zf.yichat.api.config.security;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 加密注解
 *
 * @author yichat
 * @date create in 15:34 2019/5/30 2019
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
/*@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)*/
public @interface SecurityParameter {

    /**
     * 入参是否解密，默认解密
     */
    boolean inDecode() default true;

    /**
     * 出参是否加密，默认加密
     */
    boolean outEncode() default true;
}