# 概述

基于Kotlin的整合代码框架。

为标准库和部分框架提供各种有用的扩展。

注意：

* 这个框架仍然有待完善，但是你现在就可以使用它的一些有用的功能。
* 这个框架是为Kotlin Jvm设计的，但是你自然可以在Java中使用它的一部分功能。
* 这个框架仍在更新中，未来会提供更多的模块和功能。

# 模块

## breeze-core

为Kotlin标准库和常见功能需求提供工具和扩展。

## breeze-dsl

提供简洁而灵活的dsl api，用于通过代码生成标记语言的文本。

可以进行充分的配置。

## breeze-functional

为函数式编程中提供工具和扩展。

支持0到11个参数的函数。

## breeze-http

提供简洁而直观的http api，基于Java原生的http api，用于发起http请求。

可以进行充分的配置。

## breeze-javafx

为JavaFX提供工具和扩展。

## breeze-logger

提供简单的日志器的api。

## breeze-reflect

为Java反射和Kotlin反射提供工具和扩展。

## breeze-serialization

提供简洁而统一的序列化器api，基于第三方库，用于进行数据的序列化与反序列化。

可以进行充分的配置。

## breeze-spring-boot

为Spring Boot框架提供工具和扩展。

## breeze-time

为Java的时间api提供工具和扩展。

# 参考

## 依赖 & 可选依赖

* [Spring Boot](https://github.com/spring-projects/spring-boot)
* [Spring Cloud](https://github.com/spring-cloud)
* [Kodein Framework](https://github.com/Kodein-Framework/Kodein-DI)
* [Spek Framework](https://github.com/spekframework/spek)
* [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
* [charleskorn/kaml](https://github.com/charleskorn/kaml)
* [Jackson](https://github.com/FasterXML/jackson)
* [Gson](https://github.com/google/gson)
* [FastJson](https://github.com/alibaba/fastjson)
* [MicroUtils/kotlin-logging](https://github.com/MicroUtils/kotlin-logging)
* [pmwmedia/tinylog](https://github.com/pmwmedia/tinylog)
* [MiloszKrajewski/stateful4k](https://github.com/MiloszKrajewski/stateful4k)

## 实现参考

* [Awesome Kotlin](https://github.com/KotlinBy/awesome-kotlin)
* [Google Guava](https://github.com/google/guava)
* [MehdiK/Humanizer.jvm](https://github.com/MehdiK/Humanizer.jvm)
* [kohesive/klutter](https://github.com/kohesive/klutter)
* [hotchemi/khronos](https://github.com/hotchemi/khronos)
* [yole/kxdate](https://github.com/yole/kxdate)
* [cesarferreira/kotlin-pluralizer](https://github.com/cesarferreira/kotlin-pluralizer)
* [consoleau/kassava](https://github.com/consoleau/kassava)
* [sandjelkovic/kxjtime](https://github.com/sandjelkovic/kxjtime)
* [hankdavidson/ktime](https://github.com/hankdavidson/ktime)
* [vanshg/KrazyKotlin](https://github.com/vanshg/KrazyKotlin)
* [MarioAriasC/funKTionale](https://github.com/MarioAriasC/funKTionale/tree/master/funktionale-composition)
* [KotlinTuples](https://github.com/enbandari/KotlinTuples)
* [Json Path](https://github.com/json-path/JsonPath)

# 用法

项目已发布到[Maven Central](https://repo1.maven.org/maven2)。

## Maven

在`pom.xml`中：
  
```xml
<dependencies>
  <dependency>
    <groupId>icu.windea.breezeframework</groupId>
    <artifactId>${module}</artifactId>
    <version>${version}</version>
  </dependency>
  <!--...-->
</dependencies>
```

## Gradle

在`build.gradle`中:

```groovy
dependencies {
    implementation "icu.windea.breezeframework:$module:$version"
    //...
}
```

## Gradle Kts

在`build.gradle.kts`中:

```kotlin
dependencies {
    implementation("icu.windea.breezeframework:$module:$version")
    //...
}
```