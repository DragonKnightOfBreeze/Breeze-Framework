推荐的配置顺序：
* plugins{}, group & version, repositories{}, dependencies{}, tasks.xxx{} & val xxx & xxx{}

子模块必须具有配置文件

生成项目
* `$ gradle init`

子模块任务（不能用于创建子模块）：
* `$ gradle :mySubproject:taskName`

编译：
* 添加插件kotlin-jvm
* `$ gradle compileKotlin`

测试：
* 添加测试依赖
* `$ gradle test`

生成javadoc：
* 添加插件org.jetbrains.dokka
* `$ gradle dokka`

生成javadoc jar：
* 自定义tasks.dokka{}块。
* `$ gradle dokka`

推送到本地仓库：
* 添加插件build-scan,、maven-publish，自定义group和version
* 自定义publishing{}块
* `$ gradle publish`

多模块项目：
* plugins{} & dependencies{}块不能写在allprojects{}里面
* 插件和依赖请写在子模块中。


implementation vs compile/api：
* 前者会隐藏内部使用的库。

包含其他模块（在settings.gradle里：
* include(...)
* xxx.include(...)
