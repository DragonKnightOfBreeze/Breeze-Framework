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

依赖类型：
* implementation 依赖不会进行传递。
* api 依赖会进行传递。
* compileOnly 只在编译时使用，不会打包输出，对应provided（+optional？）。
* runtimeOnly 只在运行时使用，不会添加到编译路径，对应runtime。
* annotationProcessor 用于注解处理器，与compile对应。

使gradle支持optional依赖：
* 添加插件`nebula.provided-base`

包含其他模块（在settings.gradle里：
* include(...)
* xxx.include(...)
