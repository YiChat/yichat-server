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
  `vi /etc/profile` 在最后加入<br/>
 `export TIGASE_HOME=/usr/local/tigase`<br/>
 保存退出，并执行 `source /etc/profile` 使配置文件立即生效。
 ##### 4.登陆mysql创建tigase用户及导入数据
 `mysql -u root -p`<br/>
 `grant all on *.* to tigase@'%' identified by 'aa12345';`<br/>
 `grant all on *.* to tigase@'localhost' identified by 'aa12345';`<br/>
 `flush privileges;`<br/>
 创建tigase数据库 <br/>
 `create database tigase;`
  导入数据sql,文件在项目config文件夹中 <br/>
  https://github.com/YiChat/yichat-server/blob/master/api/src/main/resources/config/tigase.sql
 ##### 5.启动/停止 tigase服务
 `tigase start` <br/>
 `tigase stop`
### 安装接口服务

  ##### 1.创建api数据库并导入数据
  `create database api;`
  导入数据sql,文件在项目config文件夹中 <br/>
  https://github.com/YiChat/yichat-server/blob/master/api/src/main/resources/config/api.sql

  ##### 2.打包项目jar包上传到服务器
  一般通过IDEA插件打成jar的形式上传服务器，写个简单的脚本执行<br>
  `vi exec.sh` <br>
  输入下面文本 <br>
  > ps -ef | grep api-1.0-SNAPSHOT-exec.jar | awk '{print $2}' | xargs kill -9
  nohup java -jar api-1.0-SNAPSHOT-exec.jar --spring.profiles.active=yichat  &
  
  然后执行 sh exec.sh即可启动成功
  
### 注意事项  
##### 1.IM服务默认要开通端口5222、19080、5280端口，接口服务要开通8015端口(在配置文件中可修改)
##### 2.api数据中的sys_dict表中的字典 IM服务器地址要修改成IM服务器IP地址
##### 3.接口服务重要配置在文件中已经说明
https://github.com/YiChat/yichat-server/blob/master/api/src/main/resources/application-yichat.yml
