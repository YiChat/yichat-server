package com.zf.yichat.api.dto.request;

import java.io.Serializable;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 09:53 2019/5/29 2019
 */
abstract class FsRequest implements Serializable {
    /*
      * 参数校验
      */
    public abstract void valid();
}
