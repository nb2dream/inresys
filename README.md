# 技术方案
- 核心框架：Spring
- WEB框架：SpringMVC
- ORM框架：Mybatis
- 安全框架：Shiro
- 模板框架：velocity（支持freemarker、jsp等其他自定义视图）
- 主页框架：adminLTE(Bootstrap)
- JS框架：vue.js
- 表格插件：bootstrap-table
- 树形表格：tree-grid(基于bootstrap扩展)
- 树形插件：ztree
- 弹窗组件：layer
- 表单校验：validator

# 项目结构
- dp-security：父级（聚合）模块
- dp-common：公共通用模块
- dp-shiro：权限模块（操作权限）
- dp-orm：数据持久模块
- dp-quartz：定时任务模块
- dp-web：前端界面
- dp-base：基础模块，目前包含行政区域、通用字典和系统日志功能模块
- dp-manage: 主要的业务模块，对整个系统的业务都是在这个模块完成    
    - api 模块：这个是安卓端请求的api接口
    - material 模块：素材模块，这个素材相关的模块都在这个模块下完成
    - program 模块： 节目模块，这个模块包含了节目和节目单的相关内容
    - terminal 模块：终端模块， 跟终端的相关操作都在这个模块完成
    - push 模块： 推送模块，跟推送的相关内容都在这里完成，包括websocket连接的相关操作

# 命名规范（参考阿里巴巴Java开发手册）
-  获取单个对象的方法用 get 做前缀
-  获取多个对象的方法用 list 做前缀
-  获取统计值的方法用 count 做前缀
-  插入的方法用 save(推荐) 或 insert 做前缀
-  删除的方法用 remove(推荐) 或 delete 做前缀
-  修改的方法用 update 做前缀

# 项目网页运行说明：  

请使用Chrome浏览器或高版本的火狐浏览器浏览网页，如果是xp系统，请使用QQ浏览器并设置网页的模式为极速模式，用其他浏览器可能会导致兼容性的问题

 **注意：不要使用IE浏览器** 

# 项目运行说明:

项目运行前，必须设置好上传素材的保存路径,和在tomcat里面添加上传文件夹路径映射的虚拟路径“/webfile”

>  - 上传路径设置方式:  修改 web模块下的 **system.properties** 配置文件，里的 **material.savePath** 值

# 该项目打包注意要点:
    项目根据不同环境选择不同的配置文件打包，配置文件是在web模块下的config目录下，dev是开发环境，prod是生产环境，test是测试环境，public是公共的配置文件，如Spring的配置文件就在这里

          
> - 如何设置打包选择不同的配置文件呢：通过修改主目录的pom文件 `<profiles />` 标签，如

    <profiles>
        <profile>
            <id>dev</id>
            <properties>
                <package.environment>dev</package.environment>
            </properties>
            <activation>
                <activeByDefault>true</activeByDefault>      # 当这个标签为true时，表示打包选择 dev下的配置文件
            </activation>
        </profile>
	</profiles>


# 项目数据库注意事项:
数据库初始化完成后，设置必须设置 sql_model，复制下面内容到mysql目录下的：my.ini 里

> `sql_mode=STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION`

- [Linux环境下修改sql_mode方式][1]

# 项目部署注意事项:
 在Linux和Windows下部署，如果是按照常规的方式部署，就是吧war包放到tomcat的webapp目录下，然后启动web服务器，这样会导致项目被初始化启动两次，导致websocket心跳检测这个定时任务线程会被再启动一条，导致出错
- [解决方式][2]

# 后期项目建议:
建议把文件的上传保存到专门的ftp文件服务器里面，而不是使用虚拟路径映射，这样做的好处就是当文件上传到ftp服务器后，后期文件的下载获取，就不用走tomcat服务器了，这样做的好处就是减轻web服务器的压力



  [1]: https://my.oschina.net/zhouchenglin/blog/1530356
  [2]: http://blog.csdn.net/u011081244/article/details/62422013
