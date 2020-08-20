package com.zf.yichat.service;

import com.zf.yichat.model.User;
import com.zf.yichat.model.UserBank;
import com.zf.yichat.model.UserDevice;
import com.zf.yichat.model.UserIm;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.vo.ThirdType;

import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:42 2019/5/28 2019
 */
public interface UserService {
    /**
     * 注册用户
     */
    UserIm add(User user);

    /**
     * 注册第三方用户
     */
    String addThird(String nick, String avatar, String openId, String unionId, ThirdType thirdType);

    User selectByMobile(String mobile);

    List<UserDevice> selectDeviceByUserId(Long userId);

    UserDevice selectDevice(Long id, String deviceId);

    boolean existDeviceId(String deviceId);

    void update(User user);


    /**
     * 查询用户IM信息
     *
     * @param userId 用户ID
     */
    UserIm selectIm(Long userId);

    /**
     * 更新用户设备
     *
     * @param userId   用户ID
     * @param deviceId 设备号
     */
    void updateDevice(Long userId, String deviceId);

    /*
      * 主键查询用户
      * @param userId 主键ID
      */
    User selectById(Long userId);

    /**
     * 搜索
     */
    List<User> searchByMobileOrAppId(String content);

    /**
     * 通过第三方信息查询注册用户信息
     *
     * @param openId
     * @param unionId
     */
    User selectByThirdAccount(String openId, String unionId);

    List<User> selectList(List<Long> idList);


    /**
     * 后台用户列表
     *
     * @param page   分页信息
     * @param nick   昵称
     * @param status 状态
     * @param mobile 手机号
     * @param ucode  唯一码
     */
    FsResponse selectIndexList(FsPage page, String nick, Integer status, String mobile, String ucode);

    /**
     * 添加银行卡号
     */
    void adBankCard(UserBank userBank);

    List<UserBank> selectBankList(Long userId);

    void deleteBank(Integer bankId);

    User selectByAppId(String mobile);

    void updateTimestamp(Long userId, Long time);

    Long getTimestamp(Long userId);

    //清理用户
    void crashUser(List<Long> userIds);

    FsResponse recordSign(Long userId, boolean isSign);


    void updatePassword(Long userId, String password);

    User selectByUnionId(String unionId);

    void saveIp(String ipAddress, Long userId);

    User selectByRecommendCode(String recommendCode);

    FsResponse selectRecommendList(FsPage page, Long userId);

    Long selectRecommendCount(String ucode);
}
