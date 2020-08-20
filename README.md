# yichat-server
yichat接口源码

# 部署说明
### 准备

在linux服务器上自行安装 JDK1.8、mysql和redis。

### 安装IM服务
 ##### 1.解压tigase包
  tigase包下载 
  链接:https://pan.baidu.com/s/1RqE-eqvV5NCvnJl9GGyMiQ  密码:hwhy <br/>
 `tar zxvf tigase.tar.gz -C /usr/local/`
 ##### 2.创建软连接（快捷方式）
 `ln -s /usr/local/tigase/scripts/tigase.sh /usr/bin/tigase`
 ##### 3.配置tigase环境变量
 vi /etc/profile 在最后加入
 `export TIGASE_HOME=/usr/local/tigase`<br/>
 保存退出，并执行 `source /etc/profile` 使配置文件立即生效。
 ##### 4.登陆mysql创建tigase用户及导入数据
 `mysql -u root -p`<br/>
 `grant all on *.* to tigase@'%' identified by 'aa12345';`<br/>
 `grant all on *.* to tigase@'localhost' identified by 'aa12345';`<br/>
 `flush privileges;`<br/>
 创建tigase数据库 <br/>
 `create database tigase;`
  导入数据sql
 
### 安装接口服务