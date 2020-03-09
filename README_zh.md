# 概述

[Github](https://github.com/DragonKnightOfBreeze/breeze-framework)

基于Kotlin的整合代码框架，为标准库和其他框架提供各种有用的扩展。

注意：

* 这个框架并未完全实现其功能，且未经过充分的测试。尽管它现在已经具备了许多有用的功能。
* 这个框架是为Kotlin设计的，而非Java。尽管你自然可以在Java中使用它。
* 这个框架正在更新中，未来会提供更多的模块和功能。

# 模块

## breeze-core

为Kotlin标准库补充缺失的功能和扩展，并提供许多日常项目开发中经常用到的工具方法和扩展方法。
如额外的字符串处理方法、集合操作方法、运算符重载方法、类型转化方法等。

## breeze-dsl

提供标准的dsl api，为一些语言提供dsl支持，便于生成对应的文本。
如xml、markdown等标记语言以及mermaid等绘图语言。

## breeze-functional

提供一些函数式编程中可能用到的扩展方法。

## breeze-game

提供一些游戏开发中可能用到的通用工具和功能。

**未完全实现。**

## breeze-generator

提供一些用于生成特定格式的字符串、json文件、yaml文件的生成器。

## breeze-http

提供一个直观优雅的api，封装java原生的http api，允许对其进行灵活的配置。
可能需要配合第三方库如`Gson`使用。

**有待完善的实现。**

## breeze-javafx

提供一些java-fx开发中可能用到的扩展方法。

## breeze-linq

提供一个api，允许以类似sql语句的形式，实现集合的集成查询操作。
这个api本身不保存集合信息。

**有待完善的实现。**

## breeze-logger

提供一个简单而独立的日志器，允许进行一定的自定义配置。
可以用来输出带颜色的控制台文本。

**有待完善的实现。**

## breeze-reflect

为Kotlin反射与java反射补充缺失的功能和扩展，并且额外补充一些有用的工具方法和扩展方法。

## breeze-serialization

为kotlinx-serialization补充可能有用的功能和扩展。

**未完全实现。**

## breeze-serializer

提供一个api，允许以简洁而统一的方式，实现各种数据格式的序列化和反序列化操作。
这个api本身不提供实现，由第三方库如`Gson`代理实现，并且支持多种实现方式。

## breeze-spring-boot

为spring boot及其可选模块提供缺失和可能有用的组件、自动配置和扩展方法。

## breeze-text

提供一些仅在特定情况下可能有用的用于字符串处理的扩展方法。

## breeze-time

提供一些与时间相关的扩展方法，便于生成和转换时间。
支持传统的Date api以及Java8中新的时间api。

# 依赖 & 可选依赖

* Kodein-di
* Spekframework
* Anko
* SpringBoot
* SpringCloud
* LibGDX
* [MicroUtils/kotlin-logging](https://github.com/MicroUtils/kotlin-logging)
* [pmwmedia/tinylog](https://github.com/pmwmedia/tinylog)
* [charleskorn/kaml](https://github.com/charleskorn/kaml)
* [MiloszKrajewski/stateful4k](https://github.com/MiloszKrajewski/stateful4k)

# 参考

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

## 用法

## Maven

```xml
<dependency>
  <groupId>com.windea.breezeframework</groupId>
  <artifactId>${module}</artifactId>
  <version>${version}</version>
</dependency>
```

## Gradle

```groovy
implementation "com.windea.breezeframework:$module:$version"
```

## Gradle Kts

```kotlin
implementation("com.windea.breezeframework:$module:$version")
```

# 示例

参见：[ExampleTest.kt](breeze-core/src/test/kotlin/com/windea/breezeframework/core/tests/ExampleTest.kt).
