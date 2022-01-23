# 概述

[中文文档](README.md) | [English Documentation](README_en.md)

[中文参考文档](https://windea.icu/Breeze-Framework/#/zh/) | [English Reference Documentation](https://windea.icu/Breeze-Framework/#/en/)

[Github](https://github.com/DragonKnightOfBreeze/Breeze-Framework)

基于Kotlin的整合代码框架。为标准库和部分框架提供各种有用的扩展。

注意：

* 这个框架是为Kotlin Jvm设计的，可以在Java中使用它的一部分功能。
* 这个框架仍在更新中，未来会提供更多的功能。
* 这个框架尚未确定模块、目录和文件结构，未来它们可能会发生较大的变动。

最新版本：3.1.1

Kotlin版本：Kotlin Jvm 1.6.0

# 模块

## breeze-core

提供基础的扩展。

* 提供额外的顶级方法。如`pass()`、`javaTypeOf()`、`Any?.cast()`。
* 提供额外的入口方法。如`enumMapOf()`、`concurrentMapOf()`。
* 提供额外的操作符方法。如`String.times()`、`List.times()`。
* 为常用类提供各种各样的扩展方法。如`String.truncate()`、`List.swap()`、`Map.asConcurrent()`。
* 为数据类提供各种各样的扩展方法。如`Any?.smartEquals()`、`equalsBy()`。
* 提供各种各样的组件，用于实现特定的功能。如`Converter`、`DefaultGenerator`、`RandomGenerator`、`CaseFormat`。
* 支持1到6个参数的元组。

## breeze-dsl

提供简洁而灵活的dsl api，用于通过代码生成标记语言的文本。

## breeze-functional

提供函数式编程的扩展。

## breeze-generator

TODO 提供生成器的api。

* `RandomGenerator`：用于根据指定的类型与参数生成随机值。
* `DefaultGenerator`：用于根据指定的类型与参数生成默认值。
* `ContextGenerator`：用于根据上下文与指定的逻辑生成期望的值。
* `ScriptingGenerator`：用于基于指定的脚本内容生成期望的值。

## breeze-http

提供简洁而直观的http api，基于Java原生的http api，用于发起http请求。

## breeze-javafx

提供javafx的扩展

## breeze-logger

提供简单的日志器的api。

## breeze-reflect

提供java反射和kotlin反射的扩展。

## breeze-serialization

提供简洁而统一的序列化器api，基于第三方库，用于进行数据的序列化与反序列化。

## breeze-time

提供java时间api的扩展。

# 参考

## 框架 & 库 & 实现

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
* [langx-java](https://github.com/fangjinuo/langx-java)

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