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

发布到JCenter：
* 创建bintray帐号（可从bintray创建）
* 创建一个属于自己的组织
* 在该组织中创建一个新的仓库
* 在`build.gradle.kts`中配置相关选项。在`publishing.repositories.bintray`块下。
* 需要配置正确的`user`和`key`。（bintray用户名和api key）
    * 可从gradle启动任务时的`-Pxxx=xxx`形式的命令参数获取。
    * 可从外部属性文件中获取。
    * 可从系统环境变量中获取。（可能需要重新启动电脑）
* 需要配置正确的`pkg.userOrg`、`pkg.repo`和`pkg.name`，其中仓库必须是预先存在的。
* 如何立即使用：
    * 添加自定义仓库`maven("https://dl.bintray.com/breeze-knights/breeze-framework")`
    * 添加自定义依赖`implementation("com.windea:breeze-framework:1.0.1")`
* 如何同步到JCenter：
    * 通过<https://bintray.com/breeze-knights/breeze-framework>可以打开JCenter仓库。
    * 然后点击Actions>Include My Package即可添加自己的库。
    * 需要配置是否是pom项目。
    * 点击Send，等待审核通过后，即可通过JCenter引用自己的库。
* **注意：成功发布到bintray后，需要手动登录帐号并确认发布。**

发布到Github：  
<https://help.github.com/en/github/managing-packages-with-github-packages/configuring-gradle-for-use-with-github-packages>
