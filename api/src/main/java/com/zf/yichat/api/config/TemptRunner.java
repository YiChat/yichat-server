package com.zf.yichat.api.config;

import com.zf.yichat.service.config.ConfigSet;
import com.zf.yichat.utils.common.FsConst;
import com.zf.yichat.vo.ApiConst;
import com.zf.yichat.vo.SmsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *  初始化资源
 */
@Component("runner")
@ConfigurationProperties(prefix = "config")
public class TemptRunner implements CommandLineRunner {

    private String unit;
    @Autowired
    private ConfigSet configSet;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public void run(String... args) {

        //设置单位
        FsConst.UNIT = getUnit();
        logger.info("财务流通单位初始完成:{}", FsConst.UNIT);
        //初始短信平台
        try {
            ApiConst.client = SmsClient.valueOf(configSet.getSms().getClient());
            logger.info("短信平台初始成功:{}", ApiConst.client.getDesc());
        } catch (Exception e) {
            logger.info("短信平台初始失败");
        }


        logger.info("--------------------------- API START SUCCESS!!!-----------------------");

    }
}
