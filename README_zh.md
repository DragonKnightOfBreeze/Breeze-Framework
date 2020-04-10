# 概述

[English Document](README.md) | [中文文档](README_zh.md)

[Github](https://github.com/DragonKnightOfBreeze/breeze-framework)

基于Kotlin的整合代码框架，为标准库和其他框架提供各种有用的扩展，它能做的比你想象的要更多。

注意：

* 这个框架仍然有待完善，但是你现在就可以使用它的一些有用的功能。
* 这个框架是为Kotlin-JVM设计的，但是你自然可以在Java中使用它的一部分功能。
* 这个框架仍在更新中，未来将会提供更多的模块和功能。

# 模块

## breeze-core

为Kotlin标准库补充功能和扩展，并且提供许多日常项目开发中经常用到的工具和扩展。例如：
* 额外的运算符重载方法。如`String.minus`、`String.times`、`String.div`。
* 额外的类型转换方法。如`String.toFile`、`String.toLocalDateTime`和`String.toIntRange`。
* 额外的字符串处理方法。如`String.quote`、`String.escapeBy`和`String.switchCaseBy`。
* 额外的集合操作方法。如`List.bind`、`List.deepGet`、`List.deepSet`和`List.deepQuery`。
* 用于委托实现`equals`、`hashCode`和`toString`的工具方法。如`equalsBy`、`hashCodeBy`、`toStringBy`和`toStringByReference`。

## breeze-dsl

提供简洁而灵活的领域特定语言的api，为常见的标记语言提供支持，便于生成对应的文本，并允许进行充分的配置。例如：
* `Xml`、`Markdown`、`Creole`等标记语言。
* `Mermaid`、`Flow`、`Sequence`等绘图语言。
* 其他一些特殊格式的文本。

## breeze-functional

提供一些函数式编程中可能用到的扩展方法。支持0到11个参数的函数。例如：
* 用于柯里化/反柯里化当前函数的`curried`和`uncurried`方法。
* 用于反转当前函数的参数的`reversed`方法。
* 用于绑定/解绑当前函数的参数的`tupled`和`untupled`方法。
* 用于得到当前函数的偏函数的`partial`和`partialLast`方法。

## ~~breeze-game~~

~~**［有待完全实现］** 提供一些游戏开发中可能用到的通用工具和功能。~~

## breeze-generator

**［有待完善］** 提供一些具有特定用途的生成器。例如：
* 用于生成各种格式的链接的`UrlGenerator`。
* 用于生成扩展Json Schema的`JsonSchemaGenerator`。

## breeze-http

**［有待完善］** 提供简洁而直观的Http api，封装Java原生的Http api，并允许进行充分的配置。

注意：可能需要配合第三方序列化库如`Gson`使用。

## breeze-javafx

**［有待完善］** 为JavaFX补充功能和扩展。

## breeze-linq

**［有待完善］** 提供简洁而直观的语言集成查询的api，允许以类似sql的方式，实现集合的查询操作。

注意：这个api本身不包含集合数据。

## breeze-logger

**［有待完善］** 提供简单而独立的日志器的api，并允许进行充分的配置。例如：
* 包含了一般日志信息的`SimpleLogger`。
* 可以输出彩色的控制台文本的`ColorfulLogger`。

## breeze-mapper

**［有待完全实现］** 提供简单而独立的映射器的api，用于进行数据的序列化与反序列化操作，并允许进行充分的配置。例如：
* 用于映射和反映射对象的`ObjectMapper`。
* 用于映射常见标记语言的`JsonMapper`、`YamlMapper`、`XmlMapper`和`PropertiesMapper`。

## breeze-reflect

为Java反射和Kotlin反射补充功能和扩展。例如：
* 直接得到类型、对象、属性、方法的名字的`nameOf`方法。
* 直接得到Java类型的`javaTypeOf`方法。
* 直接得到取值方法和赋值方法的`Class.getters`和`Class.setters`属性。

## ~~breeze-serialization~~

~~**［有待完全实现］** 为KotlinX Serialization补充功能和扩展。~~

## breeze-serializer

**［有待完善］** 提供简洁而统一的序列化器的api，用于进行数据的序列化和反序列化操作，并允许进行充分的配置。由第三方库委托实现，例如：

* 由`breeze-mapper`委托实现的`BreezeJsonSerializer`、`BreezeXmlSerializer`等。这是默认的实现，也是最简单的一种实现。
* 由`Kotlinx Serialization`委托实现的`KotlinJsonSerializer`等。这是最推荐的一种实现。
* 由`Jackson`委托实现的`JacksonJsonSerializer`、`JacksonXmlSerializer`等。这是最全面的一种实现。
* 由`Gson`委托实现的`GsonSerializer`。
* 由`FastJson`委托实现的`FastJsonSerializer`。
* 另外还提供了一些便于进行序列化和反序列化的扩展方法。如`Any?.serialize`和`String.deserialize`。

## breeze-spring-boot

**［有待完善］** 为Spring Boot补充功能和扩展，并且提供额外的组件和自动配置。

## ~~breeze-text~~

~~提供一些具有特定用途的字符串处理方法。~~

## breeze-time

为Java的时间api补充功能和扩展。
* 支持传统的时间api。包括`Date`、`Calendar`等。
* 支持Java8的时间api。包括`LocalDate`、`LocalDateTime`等。
* 为数字类型提供额外的属性，用于生成时间。如`1.seconds`、`2.minutes`和`3.years`。
* 为时长和时期提供额外的运算符重载方法。如`Duration.unaryMinus`和`Period.times`。

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

## 用法

项目已发布到JCenter。~~令人难受的是存在一些格式问题。~~

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

参见：
* [ExampleTest.kt](breeze-core/src/test/kotlin/com/windea/breezeframework/core/ExampleTest.kt)
