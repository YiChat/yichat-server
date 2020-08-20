# yichat-server
yichat接口源码

# 部署说明
### 准备

在linux服务器上自行安装 JDK1.8、mysql和redis。

### 安装IM服务
 ###### 1.解压tigase包
  tigase包下载 
  链接:https://pan.baidu.com/s/1RqE-eqvV5NCvnJl9GGyMiQ  密码:hwhy <br>
 `tar zxvf tigase.tar.gz -C /usr/local/`
 ###### 2.创建软连接（快捷方式）
 `ln -s /usr/local/tigase/scripts/tigase.sh /usr/bin/tigase`
 
### 安装接口服务