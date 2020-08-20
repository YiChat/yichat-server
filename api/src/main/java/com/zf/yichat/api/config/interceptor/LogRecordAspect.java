package com.zf.yichat.api.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.zf.yichat.service.config.ConfigSet;
import com.zf.yichat.utils.common.FsConst;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 */
@Aspect
@Configuration("logs")
public class LogRecordAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogRecordAspect.class);


    @Autowired
    private ConfigSet configSet;

    /**
     * 是否是查询列表的数据  结果不记录日志
     *
     * @return boolean
     * @date 08:59 2019/1/18
     * @author yichat
     */
    private static boolean isSelectList(String name) {
        return name.startsWith("SELECT_LIST");
    }

    private static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (Objects.isNull(obj)) {
            return map;
        }
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (Field f : fs) {
            // 设置些属性是可以访问的
            f.setAccessible(true);
            Object val;
            try {
                val = f.get(obj);
                // 得到此属性的值
                // 设置键值
                map.put(f.getName(), val);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return map;
    }

    // 定义切点Pointcut
    @Pointcut("execution(* com.zf.yichat.api.controller.*.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        if (Objects.isNull(ra)) {
            return pjp.proceed();
        }
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        Object[] args = pjp.getArgs();
        String params = "";

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method methodSign = methodSignature.getMethod();
        String[] parameters = new DefaultParameterNameDiscoverer().getParameterNames(methodSign);

        //获取请求参数集合并进行遍历拼接
        if (args.length > 0) {
            if ("POST".equals(method)) {
                int i = 0;
                for (String p : parameters) {
                    Object ag = args[i];
                    if (Objects.isNull(ag)) {
                        continue;
                    }
                    if (ag instanceof String && StringUtils.isBlank(ag.toString())) {
                        continue;
                    }
                    params = params + p + FsConst.Symbols.EQUAL + args[i] + FsConst.Symbols.COMMA;
                    i++;
                }
                params = "[" + params + "]";
            } else if ("GET".equals(method)) {
                params = queryString;
            }
        }


        logger.info("请求开始---------------");
        logger.debug("===地址:" + url);
        logger.info("===类型:" + method);
        logger.debug("===参数:" + params);

        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
        logger.info("请求结束===返回值:" + JSON.toJSONString(result));
        return result;
    }
}