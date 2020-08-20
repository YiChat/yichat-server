package com.zf.yichat.service;

import com.zf.yichat.dto.TrendCommentListDto;
import com.zf.yichat.dto.TrendListDto;
import com.zf.yichat.model.Trend;
import com.zf.yichat.model.TrendComment;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;

import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:58 2019/6/5 2019
 */
public interface TrendService {

    /**
     * 发布动态
     *
     * @param content  内容
     * @param imgs     图片
     * @param videos   视频
     * @param location 位置
     * @param userId   创建人
     */
    FsResponse add(String content, String imgs, String videos, String location, Long userId);

    /**
     * 删除动态
     */
    void delete(Long trendId);


    /**
     * 根据主键查询动态
     */
    Trend selectById(Long trendId);

    FsResponse selectList(FsPage page, Long userId, Long currentUserId);

    /**
     * 单个动态详情
     */
    TrendListDto detailById(Long trendId, Long userId);


    FsResponse selectFriendList(FsPage page, Long userId);

    /**
     * d\动态点赞
     *
     * @param trendId 动态ID
     * @param userId  点赞人
     */
    void praise(Long trendId, Long userId);

    /**
     * 取消动态点赞
     *
     * @param trendId 动态ID
     * @param userId  点赞人
     */
    void unpraise(Long trendId, Long userId);


    List<TrendListDto.PraiseUser> selectPraiseUserList(Long trendId, Long userId);

    /**
     * 评论动态
     *
     * @param content 内容
     * @param trendId 动态ID
     * @param userId  评论人
     */
    void comment(String content, Long trendId, Long userId, Long commentId);

    /**
     * 查询评论
     */
    TrendComment selectCommentById(Long commentId);

    /**
     * 删除评论
     *
     * @param commentId 评论ID
     */
    FsResponse deleteComment(Long commentId, Long userId);

    /**
     * 评论列表
     *
     * @param trendId 动态ID
     * @param userId  查看人
     * @param page    分页信息
     */
    FsResponse commentList(FsPage page, Long trendId, Long userId);

    List<TrendCommentListDto> selectCommentList(Long trendId, Long userId);


    String selectBackground(Long userId);

}
