package com.zf.yichat.api.config.interceptor;

import com.zf.yichat.service.SecurityService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthInterceptor.class);
    private static String[] excludeUrl = {"/api/user/register", "/api/login", "/api/error/auth", "/api/test", "/api/test/friend/msg"
            , "/api/login/third", "/api/check/token", "/api/sms/send", "/api/version"
            , "/api/pay/weixin/notify/", "/api/pay/alipay/notify", "/api/message/group/send",
            "/api/message/group/send/img", "/api/config/status"
            , "/api/generate/user", "/api/batch/user", "/api/wx/user/info"};
    private static String[] halfExcluede = {"/api/user/info/update", "/api/user/password/reset"};
    @Autowired
    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();


        LOGGER.debug("current request url:{}", url);
        LOGGER.debug("current request mehod:{}", request.getMethod());
        String body = "";

        LOGGER.debug("current request body:{}", body);
        //web接口统一不校验
        if (url.contains("web/api")) {
            return true;
        }

        //机器人接口房型
        if (url.contains("/robot")) {
            return true;
        }


        for (int i = 0; i < excludeUrl.length; i++) {
            if (excludeUrl[i].equals(url)) {
                return true;
            }
        }


        String token = request.getHeader("zf-token");
        LOGGER.debug("get token {}", Optional.ofNullable(token).orElse(""));
        for (int i = 0; i < halfExcluede.length; i++) {
            if (halfExcluede[i].equals(url)) {
                if (StringUtils.isBlank(token)) {
                    return true;
                }
            }
        }

        LOGGER.debug("check token start....");
        boolean isPass = securityService.validToken(token);
        LOGGER.debug("check token result:{}", isPass);
        if (!isPass) {
            response.sendRedirect(request.getContextPath() + "/error/auth");
            return false;
        }
        return true;


    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
