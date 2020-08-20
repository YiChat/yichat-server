package com.zf.yichat.api.controller.trend;

import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.request.TrendCommentDeleteRequest;
import com.zf.yichat.api.dto.request.TrendCommentListRequest;
import com.zf.yichat.api.dto.request.TrendCommentRequest;
import com.zf.yichat.service.TrendService;
import com.zf.yichat.utils.response.FsPage;
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
@RequestMapping("trend/comment")
public class TrendCommentController extends BaseController {

    @Autowired
    private TrendService trendService;

    /**
     * 评论/回复评论
     */
    @PostMapping("")
    public FsResponse comment(@RequestBody TrendCommentRequest params) {
        params.valid();
        trendService.comment(params.getContent(), params.getTrendId(), getUserId(), params.getCommentId());
        return FsResponseGen.successData(trendService.selectCommentList(params.getTrendId(), getUserId()));
    }

    /**
     * 删除评论
     */
    @PostMapping("delete")
    public FsResponse commentDelete(@RequestBody TrendCommentDeleteRequest params) {
        params.valid();

        return trendService.deleteComment(params.getCommentId(), getUserId());
    }

    /**
     * 评论列表
     */
    @PostMapping("list")
    public FsResponse commentList(@RequestBody TrendCommentListRequest params) {
        params.valid();
        return trendService.commentList(FsPage.init(params.getPageNo(), params.getPageSize()), params.getTrendId(), getUserId());
    }
}
