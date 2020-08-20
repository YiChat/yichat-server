package com.zf.yichat.service.impl;

import com.aliyun.oss.OSSClient;
import com.zf.yichat.service.OssService;
import com.zf.yichat.service.config.ConfigSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 09:10 2019/11/1 2019
 */
@Service
public class OssServiceImpl implements OssService {

    @Autowired
    private ConfigSet configSet;

    @Override
    public String upload(InputStream inputStream) {


        OSSClient client = init();
        String path = UUID.randomUUID() + ".jpg";

        // 上传文件流
        client.putObject(configSet.getOss().getBucketName(), path, inputStream);

        // 关闭client
        client.shutdown();
        return path;

    }


    protected OSSClient init() {
        return new OSSClient(configSet.getOss().getEndpoint(), configSet.getOss().getAppKey(), configSet.getOss().getSecretKey());
    }
}
