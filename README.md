# lw-blog 部署

## 介绍

>**项目名称**：lw-blog
>
>**作者：** [Mr丶冷文](https://kevinlu98.cn)
>
>**版本：** 1.0
>
>**地址：** [https://github.com/kevinlu98/lw-blog](https://github.com/kevinlu98/lw-blog)
>
>**描述信息：** 基于Java的一个功能完善颜值优雅且支持缓存的个人博客，整个博客的开发过程已录制成教程在[传送门](https://www.bilibili.com/medialist/play/372653463?from=space&business=space&sort_field=pubtime&spm_id_from=333.999.0.0)

## 展示截图

![https://imagebed-1252410096.cos.ap-nanjing.myqcloud.com/2048/a0c5f803df8748b1b51f805b80a80720.png](https://imagebed-1252410096.cos.ap-nanjing.myqcloud.com/2048/a0c5f803df8748b1b51f805b80a80720.png)

![https://imagebed-1252410096.cos.ap-nanjing.myqcloud.com/2048/64af2e3d9ab347899afc9eec45f72fb0.png](https://imagebed-1252410096.cos.ap-nanjing.myqcloud.com/2048/64af2e3d9ab347899afc9eec45f72fb0.png)

![https://imagebed-1252410096.cos.ap-nanjing.myqcloud.com/2048/fcc90e1c26d24bf0bf0dc96c4c108541.png](https://imagebed-1252410096.cos.ap-nanjing.myqcloud.com/2048/fcc90e1c26d24bf0bf0dc96c4c108541.png)

![https://imagebed-1252410096.cos.ap-nanjing.myqcloud.com/2048/ea4acc19577b4a578da8e11458d084fe.png](https://imagebed-1252410096.cos.ap-nanjing.myqcloud.com/2048/ea4acc19577b4a578da8e11458d084fe.png)

![https://imagebed-1252410096.cos.ap-nanjing.myqcloud.com/2048/42d37050c5934fd49997924939128ea8.png](https://imagebed-1252410096.cos.ap-nanjing.myqcloud.com/2048/42d37050c5934fd49997924939128ea8.png)

## 项目部署

如果你会部署`Java`项目，那么这节课就可以跳过了，我这里给大家讲解怎么用宝塔来部署`Springboot`的项目，用宝塔的原因是方便管理服务器及方便安装一些中间件，不然大家自己去安装`NGINX`、`MySQL`这些东西的时候难免会报错或遇到一些其它问题，我们就用宝塔来管理我们的服务器

### 准备工作

- 一台有公网IP的服务器
- 一个备案的域名
- 如果你没有根着项目一步一步来到这里，只是单纯的想把这个博客部署做为你的个人网站，你可以直接到`github`上去下载`release`版本

### 开始工作

- 我们进行到服务器的管理页，我这里的服务器是在腾讯云买的，所以就以腾讯云为例，安装操作系统`Centos7.X`

- 用我们的远程工具登录服务器，`Mac`电脑我推荐使用`FinalShell`

- 安装宝塔：[传送门](https://bt.cn/new/download.html)

    - CentOS系统直接用下面命令安装

  ```powershell
  yum install -y wget && wget -O install.sh http://download.bt.cn/install/install_6.0.sh && sh install.sh ed8484bec
  ```

    - 当出现如下输出时则安装成功

  ![https://imagebed-1252410096.cos.ap-nanjing.myqcloud.com/2048/5bbab9dc4fa740d0aa86988f0a3b4253.png](https://imagebed-1252410096.cos.ap-nanjing.myqcloud.com/2048/5bbab9dc4fa740d0aa86988f0a3b4253.png)

- 开放宝塔用到的端口
    - 面板端口：一般情况下是8888，到时大家看自己控制台的输出就行
    - MYSQL管理工具的商品：888

- 安装`LNMP`及`java`相关环境

![https://imagebed-1252410096.cos.ap-nanjing.myqcloud.com/2048/4b234785c65147508a3c32a24ab2c0bb.png](https://imagebed-1252410096.cos.ap-nanjing.myqcloud.com/2048/4b234785c65147508a3c32a24ab2c0bb.png)

- 上传博客到服务器并解压

- 创建一个数据库

- 修改配置文件

```yaml
spring:
  mail:
    # 是否开启邮件通知
    enable: true
    #  默认的邮件编码为UTF-8
    default-encoding: UTF-8
    # 邮箱服务器
    host: smtp.qq.com
    # 邮箱
    username: xxx@qq.com
    # 密码
    password: xxxx
    # 端口
    port: 587
  # 定义jackson时间序列化格式
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
  # 数据库的连接信息
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/lw-blog?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false
    username: root
    password: 123456
  jpa:
    hibernate:
      ddl-auto: update # 更新或者创建数据表结构 如果数据库中没做表，Jpa会我们创建 这个配置就是在没有表的时候创建，实体类发生改变的话 会更新表结构
    show-sql: true
  thymeleaf:
    # 关闭缓存
    cache: false
    # 模版的前缀
    prefix: classpath:/templates/
    # 开启模版的本地check
    check-template-location: true
    # 模版的后缀
    suffix: .html
    # 模版的编码
    encoding: utf-8
    # 响应头的content-Type
    servlet:
      content-type: text/html
    # 模式H5
    mode: HTML5
  servlet:
    multipart:
      max-file-size: 20MB
  cache:
    type: ehcache
    ehcache:
      config: classpath:/ehcache-spring.xml
logging:
  file:
    path: ./logs
  level:
    root: info
# 定义用户上传文件的路径
upload:
  base-dir: ./upload/
website:
  # 站点标题
  title: 冷文学习者
  # SEO 关键字
  keywords:
    - typecho
    - freewind
    - java
    - 程序员
    - springboot
    - 学习
    - 自学
    - 冷文学习者
    - 冷文图床
  # SEO描述信息
  description: 冷文学习者(KEVINLU98.COM)，记录一个北漂小码农的日常业余生活
  # 站长头像
  avatar: /static/image/avatar/1.png
  # 站长昵称
  nickname: Mr丶冷文
  # 管理页用户名
  username: admin
  # 管理页密码，用MD5加密后的密文
  password: 7fef6171469e80d32c0559f88b377245
  # 站长坐标
  address: 北京 昌平
  # 站点favicon
  favicon: /static/image/favicon.png
  # 站长标签
  tags:
    - java
    - springboot
    - 大数据
    - 网页设计
    - php爱好者
  # 站点URL
  url: http://localhost:8080
  # 网站上面的一句话
  navdesc: 让崇拜从这里开始，用代码做点好玩的事件，让每一天都变的充实起来
  # 站长QQ
  qq: 1628048198
  # 站长Github
  github: https://github.com/kevinlu98?tab=repositories
  # 站长新浪
  sina: https://weibo.com/lengwenboke
  # 站长邮箱
  mail: kevinlu98@qq.com
  # 网站LOGO 300 x 80
  logo: /static/image/logo.png
  # 网站底部信息
  footer: '<p>冷文学习者 版权所有 Copyright © www.kevinlu98.cn All Rights Reserved.</p> <p><a target="_blank" href="https://beian.miit.gov.cn">陕ICP备19024566-1号</a>
<a style="margin-left: 10px" target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11011402012109"><img style="vertical-align: top;" src="https://imagebed-1252410096.cos.ap-nanjing.myqcloud.com/2046/d4ab98835b8842c88eededac6e7c9e35.png">京公网安备
    11011402012109号</a></p>'
# 默认图片配置
default-image:
  # 文章没有封面时默认从下述的图片取一个，可以使用外链的方式
  images:
    - /static/image/1.jpg
    - /static/image/2.jpg
    - /static/image/3.jpg
    - /static/image/4.jpg
    - /static/image/5.jpg
  # 评论者如果使用QQ邮箱评论，直接取QQ头像作为评论头像，反之使用下面的随机图片做为头像，可以使用外链
  avatars:
    - /static/image/avatar/1.png
    - /static/image/avatar/2.png
    - /static/image/avatar/3.png
    - /static/image/avatar/4.png
    - /static/image/avatar/5.png
    - /static/image/avatar/6.png
    - /static/image/avatar/7.png
    - /static/image/avatar/8.png
    - /static/image/avatar/9.png
    - /static/image/avatar/10.png
    - /static/image/avatar/11.png
    - /static/image/avatar/12.png
    - /static/image/avatar/13.png
server:
  port: 9001 # 端口，给一个服务器上没有被占用的ip


```



- 利用宝塔进行Java项目的一键启动，启动参数加上`--spring.config.local`参数指定`yml`文件

```powershell
--spring.config.local=/www/wwwroot/jokeryan.cn/application.yml
```





