package com.zf.yichat.api.controller;

import com.alibaba.fastjson.JSON;
import com.zf.yichat.api.dto.resp.RegisterDto;
import com.zf.yichat.dto.MessageBodyDto;
import com.zf.yichat.im.mapper.TigGroupMapper;
import com.zf.yichat.model.TigGroup;
import com.zf.yichat.model.User;
import com.zf.yichat.model.UserIm;
import com.zf.yichat.service.GroupService;
import com.zf.yichat.service.ImApiService;
import com.zf.yichat.service.UserService;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:41 2019/8/2 2019
 */
@RestController
public class TestController extends BaseController {

    public static final String[] email_suffix = "@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aol.com,@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn".split(",");
    public static String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    public static String boy = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";
    public static String name_sex = "";
    private static String firstName = "赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡凌霍虞万支柯咎管卢莫经房裘缪干解应宗宣丁贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊於惠甄魏加封芮羿储靳汲邴糜松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫宁仇栾暴甘钭厉戎祖武符刘姜詹束龙叶幸司韶郜黎蓟薄印宿白怀蒲台从鄂索咸籍赖卓蔺屠蒙池乔阴郁胥能苍双闻莘党翟谭贡劳逄姬申扶堵冉宰郦雍却璩桑桂濮牛寿通边扈燕冀郏浦尚农温别庄晏柴瞿阎充慕连茹习宦艾鱼容向古易慎戈廖庚终暨居衡步都耿满弘匡国文寇广禄阙东殴殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空曾毋沙乜养鞠须丰巢关蒯相查后江红游竺权逯盖益桓公万俟司马上官欧阳夏侯诸葛闻人东方赫连皇甫尉迟公羊澹台公冶宗政濮阳淳于仲孙太叔申屠公孙乐正轩辕令狐钟离闾丘长孙慕容鲜于宇文司徒司空亓官司寇仉督子车颛孙端木巫马公西漆雕乐正壤驷公良拓拔夹谷宰父谷粱晋楚阎法汝鄢涂钦段干百里东郭南门呼延归海羊舌微生岳帅缑亢况后有琴梁丘左丘东门西门商牟佘佴伯赏南宫墨哈谯笪年爱阳佟第五言福百家姓续";
    private static String girl = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";
    private static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
    @Autowired
    private UserService userService;
    @Autowired
    private TigGroupMapper tigGroupMapper;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ImApiService imApiService;

    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    public static String getTelephone() {
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String thrid = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + thrid;
    }

    public static String getChineseName() {
        int index = getNum(0, firstName.length() - 1);
        String first = firstName.substring(index, index + 1);
        int sex = getNum(0, 1);
        String str = boy;
        int length = boy.length();
        if (sex == 0) {
            str = girl;
            length = girl.length();
            name_sex = "女";
        } else {
            name_sex = "男";
        }
        index = getNum(0, length - 1);
        String second = str.substring(index, index + 1);
        int hasThird = getNum(0, 1);
        String third = "";
        if (hasThird == 1) {
            index = getNum(0, length - 1);
            third = str.substring(index, index + 1);
        }
        return first + second + third;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/yichat/Downloads/掌上聊/c_friend.csv"));
        //换成你的文件名
        reader.readLine();
        // 第一行信息，为标题信息，不用,如果需要，注释掉
        //
        String line = null;
        List<String> data = new ArrayList<>();
        List<String> result = new ArrayList<>();
        List<String> sign = new ArrayList<>();
        int i = 0;
        while ((line = reader.readLine()) != null) {

            String item[] = line.split(",");
            //CSV格式文件为逗号分隔符文件，这里根据逗号切分
            String last = item[item.length - 1];
            //这就是你要的数据了
            // int value = Integer.parseInt(last);
            data.add(line);
            //如果是数值，可以转化为数值

            i++;

        }


        //去重
        for (String entry : data) {
            String kk = entry;


            //判断是否存在结果集中
            boolean isExist = false;
            for (String ee : result) {
                String[] arr = ee.split(",");
                if (kk.equals(arr[1] + "," + arr[0]) || kk.equals(ee)) {
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                result.add(entry);
            }

        }

        System.out.println(result.size());

        File csv = new File("/Users/yichat/Downloads/掌上聊/c_friend_result.csv"); // CSV数据文件
        BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
        // 附加   // 添加新的数据行
        for (String entry : result) {
            bw.write(entry.split(",")[0] + "," + entry.split(",")[1]);
            bw.newLine();
        }
        bw.close();


    }

    @RequestMapping("/test")
    public FsResponse test() {
        return FsResponseGen.successData("服务器启动正常");
    }

    @RequestMapping("/test/friend/msg")
    public FsResponse testFriendMsg(Long userId, Integer times) {


        List<TigGroup> list = groupService.selectGroupListByCreater(userId);

        System.out.println(JSON.toJSONString(list, true));

        ExecutorService service = Executors.newFixedThreadPool(50);
        times = Objects.isNull(times) ? 5 : times;
        for (int in = 0; in < times; in++) {
            for (int i = 0; i < list.size(); i++) {

                User user = userService.selectById(userId);
                service.execute(new TestMsgThread(user.getAvatar(), user.getNick(), user.getId().toString(), list.get(i).getGid().toString(), System.currentTimeMillis() + "发送消息"));

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

        service.shutdown();

        return FsResponseGen.successData("发送完毕");

    }

    //批量注册用户
    @PostMapping("/batch/user")
    public FsResponse batchUser(String mobiles, String password) {

        ExecutorService service = Executors.newFixedThreadPool(30);
        List<String> tels = GeneralUtils.splitExcludeEmpty(mobiles, ",");
        int i = 1;
        for (String tel : tels) {


            if (userService.selectByMobile(tel) != null) {
                continue;
            }

            service.execute(new TestThread(i, tel, password));


            i++;
        }

        service.shutdown();
        return FsResponseGen.success();
    }


    //模拟生成用户

    @RequestMapping("/generate/user")
    public FsResponse generate() {

        int count = 500;
        ExecutorService service = Executors.newFixedThreadPool(30);
        for (int i = 0; i < count; i++) {

            String tel = getTelephone();

            if (userService.selectByMobile(tel) != null) {
                count++;
                continue;
            }

            service.execute(new TestThread(i, tel, "aa12345"));


        }

        service.shutdown();
        return FsResponseGen.success();
    }

    class TestMsgThread implements Runnable {

        private String avatar;
        private String nick;
        private String fromUserId;
        private String userId;
        private String text;

        public TestMsgThread(String avatar, String nick, String fromUserId, String userId, String text) {
            this.avatar = avatar;
            this.nick = nick;
            this.fromUserId = fromUserId;
            this.userId = userId;
            this.text = text;
        }

        @Override
        public void run() {
            MessageBodyDto dto = new MessageBodyDto();

            MessageBodyDto.Ext ext = dto.new Ext();
            ext.setAvatar(avatar);
            ext.setNick(nick);
            ext.setUserId(fromUserId);

            MessageBodyDto.Body body = dto.new Body();
            body.setContent(text);

            MessageBodyDto.Data data = dto.new Data();
            data.setExt(ext);
            data.setBody(body);
            data.setChatType(2);
            data.setMsgType(2001);
            data.setMsgId(UUID.randomUUID().toString());
            data.setFrom(fromUserId);
            data.setTo(userId);


            dto.setData(data);
            dto.setType(2000);
            imApiService.sendRobotMsg(2, Long.parseLong(userId), URLEncoder.encode(JSON.toJSONString(dto)));

            System.out.println(nick + "消息成功:" + JSON.toJSONString(dto));

        }
    }

    class TestThread implements Runnable {

        private int i;
        private String tel;
        private String password;

        public TestThread(int i, String tel, String password) {
            this.i = i;
            this.tel = tel;
            this.password = password;
        }

        @Override
        public void run() {
            User user = new User();
            user.setMobile(this.tel);
            user.setPassword(password);
            user.setStatus(0);

            UserIm im = userService.add(user);

            RegisterDto data = new RegisterDto();
            data.setImPassword(im.getImPassword());
            data.setToken(user.getToken());
            data.setUserId(user.getId());
            data.setAppId(user.getAppid());
            data.setMobile(user.getMobile());

            System.out.println("第" + i + "用户注册成功:" + JSON.toJSONString(data));

        }
    }

}
