package com.zf.yichat.service.impl;

import com.github.pagehelper.PageHelper;
import com.zf.yichat.dto.VideoCommentReplyDto;
import com.zf.yichat.dto.VideoDto;
import com.zf.yichat.mapper.*;
import com.zf.yichat.model.*;
import com.zf.yichat.service.BalanceService;
import com.zf.yichat.service.SysDictService;
import com.zf.yichat.service.UserService;
import com.zf.yichat.service.VideoService;
import com.zf.yichat.service.config.RedisService;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.DateUtils;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import com.zf.yichat.vo.BalanceType;
import com.zf.yichat.vo.DictKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.reducing;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:15 2020/4/15 2020
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VideoPraiseMapper videoPraiseMapper;

    @Autowired
    private VideoCommentMapper videoCommentMapper;

    @Autowired
    private VideoCommentPraiseMapper videoCommentPraiseMapper;
    @Autowired
    private VideoViewMapper videoViewMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoPlayRecordMapper videoPlayRecordMapper;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private VideoUserRewardMapper videoUserRewardMapper;

    @Override
    public FsResponse save(String path, String thumbnail, BigDecimal rate, Integer seconds, String content, Long userId) {

        Video video = new Video();
        video.setCommentCount(0);
        video.setPraiseCount(0);
        video.setContent(content);
        video.setPath(path);
        video.setSeconds(seconds);
        video.setStatus(1);
        video.setCtime(new Date());
        video.setThumbnail(thumbnail);
        video.setUserId(userId);
        video.setViewCount(0);
        video.setRate(rate);
        video.setBalanceStatus(0);
        video.setBalanceMoney(BigDecimal.ZERO);
        video.setCheckTime(new Date());

        videoMapper.insertUseGeneratedKeys(video);

        FsResponse response = FsResponseGen.successMsg(null);
        response.setData(detailDto(video.getId(), userId));

        return response;
    }

    private String getPublishReword(Long userId, Video video) {
        VideoUserReward ur = getReward(userId);
        if (Objects.isNull(ur)) {
            return null;
        }
        //发布视频奖励
        BigDecimal reward = new BigDecimal(sysDictService.selectOne(DictKey.video_public_reward).getValue());
        BigDecimal limit = new BigDecimal(sysDictService.selectOne(DictKey.video_public_reward_limit).getValue());
        //计算今天是否已经超过奖励
        Example example = new Example(Video.class);
        example.createCriteria().andEqualTo("userId", userId)
                .andEqualTo("balanceStatus", 1)
                .andGreaterThan("checkTime", DateUtils.formatCurrentDate("yyyy-MM-dd") + DateUtils.TIME_START_SUFFIX)
                .andLessThan("checkTime", DateUtils.formatCurrentDate("yyyy-MM-dd") + DateUtils.TIME_END_SUFFIX);

        BigDecimal all = videoMapper.selectByExample(example).stream().map(Video::getBalanceMoney).collect(reducing(BigDecimal.ZERO, BigDecimal::add));

        if ((all.add(reward)).compareTo(limit) <= 0) {
            String msg = "发布视频奖励" + reward + "元";
            video.setBalanceStatus(1);
            video.setBalanceMoney(reward);
            addReward(ur, reward);
            balanceService.update(userId, BalanceType.VIDEO_PUBLISH, reward, video.getId(), msg);
            videoMapper.updateByPrimaryKeySelective(video);
            return msg;
        }

        return null;
    }

    private VideoDto detailDto(Long videoId, Long userId) {

        Video video = selectById(videoId);
        User user = userService.selectById(userId);

        VideoDto dto = new VideoDto();
        dto.setRate(video.getRate());
        dto.setAvatar(user.getAvatar());
        dto.setNick(user.getNick());
        dto.setUserId(userId);
        dto.setCommentCount(video.getCommentCount());
        dto.setContent(video.getContent());
        dto.setPath(video.getPath());
        dto.setThumbnail(video.getThumbnail());
        dto.setPraseCount(video.getPraiseCount());
        dto.setSeconds(video.getSeconds());
        dto.setVideoId(videoId);
        dto.setViewCount(video.getViewCount());
        dto.setPraiseStatus(Objects.nonNull(selectPraiseByUserId(videoId, userId)) ? 1 : 0);


        return dto;

    }

    private VideoDto MyListDto(Video video, Long userId, boolean simple) {

        VideoDto dto = new VideoDto();
        if (!simple) {


        }

        User user = userService.selectById(userId);
        dto.setAvatar(user.getAvatar());
        dto.setNick(user.getNick());
        dto.setPraiseStatus(Objects.nonNull(selectPraiseByUserId(video.getId(), userId)) ? 1 : 0);
        dto.setUserId(userId);
        dto.setCommentCount(video.getCommentCount());
        dto.setContent(video.getContent());
        dto.setPath(video.getPath());
        dto.setThumbnail(video.getThumbnail());
        dto.setPraseCount(video.getPraiseCount());
        dto.setSeconds(video.getSeconds());
        dto.setVideoId(video.getId());
        dto.setViewCount(video.getViewCount());
        dto.setRate(video.getRate());


        return dto;

    }


    @Override
    public String praise(Long videoId, Long userId) {

        VideoPraise sr = selectPraiseByUserId(videoId, userId);

        //存在则取消
        if (Objects.nonNull(sr)) {
            videoPraiseMapper.deleteByPrimaryKey(sr.getId());
            videoMapper.reducePraiseCount(videoId);
        } else {
            VideoPraise vp = new VideoPraise();
            vp.setUserId(userId);
            vp.setVideoId(videoId);
            vp.setCtime(new Date());
            vp.setBalanceStatus(0);
            videoPraiseMapper.insertUseGeneratedKeys(vp);
            videoMapper.incrementPraiseCount(videoId);

//            VideoUserReward reward = getReward(userId);
//            if (Objects.isNull(reward)) {
//                return null;
//            }
//
//            //今天奖励上限
//            BigDecimal limitMoney = new BigDecimal(sysDictService.selectOne(DictKey.video_praise_reward_limit).getValue());
//            BigDecimal rewardMoney = new BigDecimal(sysDictService.selectOne(DictKey.video_praise_reward).getValue());
//
//
//            if (!Optional.ofNullable(videoPraiseMapper.limitMoney(userId, limitMoney)).orElse(false)) {
//
//
//                videoPraiseMapper.batchUpdateBalanceStatus(vp.getId());
//                balanceService.update(userId, BalanceType.VIDEO_PRAISE, rewardMoney, null, "点赞视频奖励" + rewardMoney + "元");
//                addReward(reward, rewardMoney);
//
//                //10次奖励一次
//                Example example = new Example(VideoPraise.class);
//                example.createCriteria().andEqualTo("userId", userId)
//                        .andEqualTo("balanceStatus", 1)
//                        .andGreaterThan("ctime", DateUtils.formatCurrentDate("yyyy-MM-dd") + DateUtils.TIME_START_SUFFIX)
//                        .andLessThan("ctime", DateUtils.formatCurrentDate("yyyy-MM-dd") + DateUtils.TIME_END_SUFFIX);
//
//
//                if (videoPraiseMapper.selectCountByExample(example) == 5) {
//                    rewardMoney = rewardMoney.multiply(new BigDecimal(5));
//
//                    return "点赞视频奖励" + rewardMoney + "元";
//
//                }
//            }


        }

        return null;


    }

    @Override
    public Video selectById(Long videoId) {
        return videoMapper.selectByPrimaryKey(videoId);
    }

    @Override
    public VideoComment selectCommentById(Long commentId) {
        return videoCommentMapper.selectByPrimaryKey(commentId);
    }

    @Override
    public List<VideoDto> list(Long userId, Integer type) {
        //0默认排序 1最新 2最热 3关注
        return DtoChangeUtils.getList(videoMapper.list(userId, type), v -> {
            Video video = selectById(v.getId());
            User user = userService.selectById(v.getUserId());

            VideoDto dto = new VideoDto();
            dto.setAvatar(user.getAvatar());
            dto.setNick(user.getNick());
            dto.setUserId(v.getUserId());
            dto.setCommentCount(video.getCommentCount());
            dto.setContent(video.getContent());
            dto.setPath(video.getPath());
            dto.setThumbnail(video.getThumbnail());
            dto.setPraseCount(video.getPraiseCount());
            dto.setSeconds(video.getSeconds());
            dto.setVideoId(v.getId());
            dto.setRate(video.getRate());

            dto.setPraiseStatus(Objects.nonNull(selectPraiseByUserId(v.getId(), userId)) ? 1 : 0);


            return dto;
        });
    }

    @Override
    public VideoPraise selectPraiseByUserId(Long video, Long userId) {
        Example example = new Example(VideoPraise.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("videoId", video);
        return videoPraiseMapper.selectOneByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FsResponse comment(Long videoId, Long commentId, String content, Long srcCommentId, Long userId) {
//        VideoComment vc = new VideoComment();
//        vc.setCommentId(Objects.isNull(commentId) ? 0 : commentId);
//        vc.setContent(content);
//        vc.setPraiseCount(0);
//        vc.setVideoId(videoId);
//        vc.setUserId(userId);
//        vc.setReplyCount(0);
//        vc.setCtime(new Date());
//        vc.setBalanceStatus(0);
//        vc.setStatus(0);
//        vc.setCommentUserId(Objects.isNull(commentId) ? null : videoCommentMapper.selectByPrimaryKey(commentId).getUserId());
//        vc.setSrcCommentId(Objects.isNull(srcCommentId) ? 0 : srcCommentId);
//
//        //同一个视频如果奖励过则不重复奖励
//        Example exist = new Example(VideoComment.class);
//        exist.createCriteria().
//                andEqualTo("videoId", videoId)
//                .andEqualTo("userId", userId);
//        boolean isCanReward = true;
//        if (videoCommentMapper.selectCountByExample(exist) > 0) {
//            isCanReward = false;
//        }
//
//        videoCommentMapper.insertUseGeneratedKeys(vc);
//
//        if (Objects.nonNull(srcCommentId)) {
//            //增加回复数
//            videoCommentMapper.incrementReplyCount(srcCommentId);
//        }
//
//        //增加评论数
//        videoMapper.incrementCommentCount(videoId);
//
//        VideoCommentReplyDto dto = new VideoCommentReplyDto();
//        User user = userService.selectById(userId);
//        dto.setAvatar(user.getAvatar());
//        dto.setNick(user.getNick());
//        dto.setUserId(userId);
//
//        dto.setContent(content);
//        dto.setCommentId(vc.getId());
//
//        //如果有回复对象
//        if (Objects.nonNull(commentId)) {
//            VideoComment vcc = selectCommentById(commentId);
//            User vccUser = userService.selectById(vc.getCommentUserId());
//            dto.setReplayCommentId(vcc.getId());
//            dto.setReplyNick(vccUser.getNick());
//            dto.setReplyUserId(vc.getCommentUserId());
//        }
//
//        dto.setTimeDesc("刚刚");
//
//        FsResponse response = FsResponseGen.successMsg( null);
//
//
//        response.setData(dto);

        return FsResponseGen.failMsg("评论关闭");

    }

    private String getCommentReword(Long userId, Long commentId) {

        VideoUserReward reward = getReward(userId);
        if (Objects.isNull(reward)) {
            return null;
        }

        //今天奖励上限
        BigDecimal limitMoney = new BigDecimal(sysDictService.selectOne(DictKey.video_comment_reward_limit).getValue());
        BigDecimal rewardMoney = new BigDecimal(sysDictService.selectOne(DictKey.video_comment_reward).getValue());

        //缓存标记提示
        String key = userId + "comment_reword";
        if (Optional.ofNullable(videoCommentMapper.limitMoney(userId, limitMoney)).orElse(false)) {
            if (StringUtils.isNotBlank(redisService.getVal(key))) {
                return null;
            } else {
                redisService.setVal(key, "1");
                return null;
            }

        }

        //进入就奖励
        balanceService.update(userId, BalanceType.VIDEO_COMMENT, rewardMoney, null, "评论视频奖励" + rewardMoney + "元");

        videoCommentMapper.updateBalanceStatus(commentId);

        addReward(reward, rewardMoney);

        //清除标记
        redisService.deleteKey(key);

        Example example = new Example(VideoComment.class);
        example.createCriteria().andEqualTo("balanceStatus", 1).andEqualTo("userId", userId)
                .andGreaterThan("ctime", DateUtils.formatCurrentDate("yyyy-MM-dd") + DateUtils.TIME_START_SUFFIX)
                .andLessThan("ctime", DateUtils.formatCurrentDate("yyyy-MM-dd") + DateUtils.TIME_END_SUFFIX);
        example.setOrderByClause("id desc");
        //满5条提醒一次
        if (videoCommentMapper.selectCountByExample(example) % 5 == 0) {
            rewardMoney = rewardMoney.multiply(new BigDecimal(5));

            return "评论视频奖励" + rewardMoney + "元";
        }

        return null;

    }

    @Override
    public void commentPraise(Long commentId, Long userId) {
        VideoCommentPraise praise = selectCommentPraiseByUserId(commentId, userId);
        if (Objects.nonNull(praise)) {

            videoCommentPraiseMapper.deleteByPrimaryKey(praise);
            videoCommentMapper.reductPraiseCount(commentId);
        } else {
            VideoCommentPraise pra = new VideoCommentPraise();
            pra.setCommentId(commentId);
            pra.setUserId(userId);
            videoCommentPraiseMapper.insertSelective(pra);
            videoCommentMapper.incrementPraiseCount(commentId);
        }

    }

    @Override
    public FsResponse selectCommentList(Integer pageNo, Integer pageSize, Long videoId, Long userId) {
//        Example example = new Example(VideoComment.class);
//        example.createCriteria().andEqualTo("videoId", videoId).andEqualTo("commentId", 0).andEqualTo("status", 0);
//        example.setOrderByClause(" praise_count desc, id desc ");
//        FsPage page = FsPage.init(pageNo, pageSize);
//        PageHelper.startPage(page.getPageNo(), page.getPageSize());
//        return DtoChangeUtils.getPageList(videoCommentMapper.selectByExample(example), v -> {
//            VideoCommentDto dto = new VideoCommentDto();
//            User user = userService.selectById(v.getUserId());
//            dto.setAvatar(user.getAvatar());
//            dto.setNick(user.getNick());
//            dto.setUserId(v.getUserId());
//            dto.setPraiseCount(v.getPraiseCount());
//            dto.setReplyCount(v.getReplyCount());
//            dto.setContent(v.getContent());
//            dto.setCommentId(v.getId());
//            dto.setPraiseStatus(Objects.nonNull(selectCommentPraiseByUserId(v.getId(), userId)) ? 1 : 0);
//
//            long timeDis = System.currentTimeMillis() - v.getCtime().getTime();
//            //计算分钟数
//            long mins = timeDis / (1000 * 60);
//
//            String timeDesc = "刚刚";
//            //超过1天算日期
//            if (mins > 24 * 60) {
//                timeDesc = DateUtils.formatDate(v.getCtime(), "MM-dd");
//            } else if (mins > 60) {
//                timeDesc = mins / 60 + "小时前";
//            } else if (mins > 1) {
//                timeDesc = mins + "分钟前";
//            }
//
//            dto.setTimeDesc(timeDesc);
//
//            return dto;
//        });

        return FsResponseGen.successData(new ArrayList<>());
    }

    @Override
    public FsResponse selectCommentReplyList(Integer pageNo, Integer pageSize, Long commentId, Long userId) {
        Example example = new Example(VideoComment.class);
        example.createCriteria().andEqualTo("srcCommentId", commentId).andEqualTo("status", 0);
        example.setOrderByClause(" praise_count desc, id desc ");
        FsPage page = FsPage.init(pageNo, pageSize);
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return DtoChangeUtils.getPageList(videoCommentMapper.selectByExample(example), v -> {
            VideoCommentReplyDto dto = new VideoCommentReplyDto();
            User user = userService.selectById(v.getUserId());
            User srcUser = userService.selectById(v.getCommentUserId());
            dto.setAvatar(user.getAvatar());
            dto.setNick(user.getNick());
            dto.setUserId(v.getUserId());
            dto.setContent(v.getContent());
            dto.setCommentId(v.getId());

            dto.setPraiseCount(v.getPraiseCount());
            dto.setReplayCommentId(v.getCommentId());
            dto.setReplyNick(srcUser.getNick());
            dto.setReplyUserId(v.getCommentUserId());
            dto.setPraiseStatus(Objects.nonNull(selectCommentPraiseByUserId(v.getId(), userId)) ? 1 : 0);

            long timeDis = System.currentTimeMillis() - v.getCtime().getTime();
            //计算分钟数
            long mins = timeDis / (1000 * 60);

            String timeDesc = "刚刚";
            //超过1天算日期
            if (mins > 24 * 60) {
                timeDesc = DateUtils.formatDate(v.getCtime(), "MM-dd");
            } else if (mins > 60) {
                timeDesc = mins / 60 + "小时前";
            } else if (mins > 1) {
                timeDesc = mins + "分钟前";
            }

            dto.setTimeDesc(timeDesc);

            return dto;
        });
    }


    @Override
    public VideoCommentPraise selectCommentPraiseByUserId(Long commentId, Long userId) {
        Example example = new Example(VideoCommentPraise.class);
        example.createCriteria().andEqualTo("commentId", commentId).andEqualTo("userId", userId);

        return videoCommentPraiseMapper.selectOneByExample(example);
    }


    @Override
    public FsResponse selectMyList(FsPage page, Integer type, Long userId) {

        List<Video> list = null;

        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        //查询我创建的
        if (type == null || type == 0) {
            list = videoMapper.selectCreateList(userId);
            return DtoChangeUtils.getPageList(list, v -> {
                return MyListDto(v, userId, true);
            });
            //查询好友动态
        } else if (type == 1) {
            list = videoMapper.selectFriendList(userId);

            return DtoChangeUtils.getPageList(list, v -> {
                return MyListDto(v, userId, false);
            });
            //查询我点赞的动态
        } else if (type == 2) {
            list = videoMapper.selectPraiseList(userId);
            return DtoChangeUtils.getPageList(list, v -> {
                return MyListDto(v, userId, true);
            });
        } else {

        }


        return DtoChangeUtils.getPageList(list, v -> {
            return MyListDto(v, userId, false);
        });
    }


    @Override
    public VideoDto detailById(Long videoId, Long userId) {

        return detailDto(videoId, userId);
    }

    @Override
    public void viewVideo(Long videoId, Long userId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                videoMapper.incrementViewCount(videoId);
                videoViewMapper.saveOrUpdate(videoId, userId);
            }
        }).start();
    }

    @Override
    public String play(Long videoId, Long userId) {

        String resStr = null;
        VideoPlayRecord record = new VideoPlayRecord();
        record.setUserId(userId);
        record.setVideoId(videoId);

        //增加浏览量
        viewVideo(videoId, userId);

        record.setBalanceStatus(0);
        record.setCtime(new Date());


        VideoUserReward reward = getReward(userId);
        if (Objects.isNull(reward)) {
            return null;
        }

        //当前视频看过则过滤
        if (Objects.nonNull(videoPlayRecordMapper.selectByVideoId(videoId, userId))) {
            return null;
        }

        videoPlayRecordMapper.insertUseGeneratedKeys(record);

        //如果奖励到达上限则不往下了

        //今天奖励上限
        BigDecimal limitMoney = new BigDecimal(sysDictService.selectOne(DictKey.video_play_reward_limit).getValue());
        BigDecimal rewardMoney = new BigDecimal(sysDictService.selectOne(DictKey.video_play_reward).getValue());


        if (Optional.ofNullable(videoPlayRecordMapper.limitMoney(userId, limitMoney)).orElse(false)) {
            return null;

        }

        videoPlayRecordMapper.updateBalanceStatus(record.getId());
        balanceService.update(userId, BalanceType.VIDEO_PLAY, rewardMoney, null, "观看视频奖励" + rewardMoney + "元");
        addReward(reward, rewardMoney);

        Example example = new Example(VideoPlayRecord.class);
        example.createCriteria().andEqualTo("balanceStatus", 1).andEqualTo("userId", userId)
                .andGreaterThan("ctime", DateUtils.formatCurrentDate("yyyy-MM-dd") + DateUtils.TIME_START_SUFFIX)
                .andLessThan("ctime", DateUtils.formatCurrentDate("yyyy-MM-dd") + DateUtils.TIME_END_SUFFIX);


        //4条加上当前一条
        if (videoPlayRecordMapper.selectCountByExample(example) % 5 == 0) {
            rewardMoney = rewardMoney.multiply(new BigDecimal(5));


            resStr = "观看视频奖励" + rewardMoney + "元";
        }

        return resStr;

    }

    @Override
    public FsResponse deleteVideo(Long videoId, Long userId) {

        Video video = selectById(videoId);
        //如果userId为空 则是后台强制删除
        if (Objects.nonNull(userId) && video.getUserId().compareTo(userId) != 0) {
            //校验是否有权限
            return FsResponseGen.fail(YiChatMsgCode.VIDEO_NONE_MINE);

        }

        //更新视频状态


        video.setStatus(-1);
        videoMapper.updateByPrimaryKeySelective(video);


        return FsResponseGen.success();
    }

    @Override
    public FsResponse deleteComment(Long commentId, Long userId) {

        VideoComment vc = selectCommentById(commentId);
        //如果该视频创建者 则拥有权限
        if (Objects.nonNull(userId)) {
            if (vc.getUserId().compareTo(userId) != 0) {
                Video video = selectById(vc.getVideoId());
                if (video.getUserId().compareTo(userId) != 0) {
                    return FsResponseGen.fail(YiChatMsgCode.VIDEO_NONE_MINE);
                }
            }

        }

        //删除评论
        vc.setStatus(1);
        videoCommentMapper.updateByPrimaryKeySelective(vc);

        //评论数减去数量
        int reduceCount = 1;
        if (vc.getSrcCommentId() > 0) {
            //删除子回复数量
            reduceCount = videoCommentMapper.updateReplyStatus(vc.getSrcCommentId());
        }

        //更新视频的评论数量
        videoMapper.reduceCommentCount(vc.getVideoId(), reduceCount);


        return FsResponseGen.success();
    }

    private VideoUserReward getReward(Long userId) {

        BigDecimal limitMoney = new BigDecimal(sysDictService.selectOne(DictKey.video_reward_limit).getValue());
        System.out.println("单个用户奖励上限：" + limitMoney.toPlainString());

        Example example = new Example(VideoUserReward.class);
        example.createCriteria().andEqualTo("userId", userId);

        VideoUserReward reward = videoUserRewardMapper.selectOneByExample(example);
        //已经超过限额了
        if (Objects.nonNull(reward) && reward.getReward().compareTo(limitMoney) >= 0) {
            return null;
        } else {
            if (Objects.isNull(reward)) {
                reward = new VideoUserReward();
                reward.setReward(BigDecimal.ZERO);
                reward.setCtime(new Date());
                reward.setUserId(userId);
                videoUserRewardMapper.insertUseGeneratedKeys(reward);
            }
            return reward;
        }


    }

    private void addReward(VideoUserReward reward, BigDecimal add) {

        reward.setReward(reward.getReward().add(add));

        videoUserRewardMapper.updateByPrimaryKeySelective(reward);
    }

}
