package com.zf.yichat.api.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11AprProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

/**
 * 修改长链接keepAlive相关配置，保证路由策略的性能高效。
 *
 * @author yichat
 * @date create in 22:12 2019/9/4 2019
 */
public class WebServerConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        // 使用对应工厂类提供给我们的接口定制化我们的tomcat connector
        ((TomcatServletWebServerFactory) factory).addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                Http11AprProtocol protocol = (Http11AprProtocol) connector.getProtocolHandler();
                // 定制化keepAliveTimeout，设置30秒内没有请求则服务端自动断开keepalive链接
                protocol.setKeepAliveTimeout(300000);
                // 当客户端发送超过10000个请求则自动断开keepalive链接
                protocol.setMaxKeepAliveRequests(10000);

            }
        });
    }
}
