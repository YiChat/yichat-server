package com.zf.yichat.api.controller.trend;

import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.request.TrendPraiseCancleRequest;
import com.zf.yichat.api.dto.request.TrendPraiseRequest;
import com.zf.yichat.service.TrendService;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:45 2019/6/5 2019
 */
@RestController
@RequestMapping("trend")
public class TrendPraiseController extends BaseController {

    @Autowired
    private TrendService trendService;

    /**
     * 点赞
     */
    @PostMapping("praise")
    public FsResponse praise(@RequestBody TrendPraiseRequest params) {
        params.valid();
        trendService.praise(params.getTrendId(), getUserId());
        return FsResponseGen.successData(trendService.selectPraiseUserList(params.getTrendId(), getUserId()));
    }

    /**
     * 取消点赞
     */
    @PostMapping("praise/cancle")
    public FsResponse praiseCancle(@RequestBody TrendPraiseCancleRequest params) {
        params.valid();
        trendService.unpraise(params.getTrendId(), getUserId());
        return FsResponseGen.successData(trendService.selectPraiseUserList(params.getTrendId(), getUserId()));
    }
}
