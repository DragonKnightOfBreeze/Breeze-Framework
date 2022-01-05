# Summary

[中文文档](README.md) | [English Documentation](README_en.md)

[中文参考文档](https://windea.icu/Breeze-Framework/#/zh/) | [English Reference Documentation](https://windea.icu/Breeze-Framework/#/en/)

[Github](https://github.com/DragonKnightOfBreeze/Breeze-Framework)

Integrated code framework based on Kotlin. Provides many useful extensions for standard library and some frameworks.

NOTE：

* This framework is designed to be used by Kotlin Jvm, and can use part of it's functions in Java.
* This framework is still on updating, and will provide more functions in the future.
* This framework has not yet determine it's module, directory and file structure, and may happen large changes in the future.

Newest Version: 3.1.1

Kotlin Version: Kotlin Jvm 1.6.0

# Modules

## breeze-core

Provide basic extensions.

* Provide extra top functions. e.g. `pass()`, `javaTypeOf()`, `Any?.cast()`.
* Provide extra entry functions. e.g. `enumMapOF()`, `concurrentMapOf()`.
* Provide various extension functions for common classes. e.g. `String.trucnate()`, `List.swap()`, `Map.asConcurrent`.
* Provide various extension functions for data classes. e.g. `Any?.smartEquals`, `equalsBy()`.
* Provide various components for implementing specific functions. e.g. `Converter`, `DefaultGenerator`, `RandomGenerator`, `CaseFormat`.
* Support tuples from 1 argument to 6 arguments.

## breeze-dsl

Provides a concise and flexible dsl api, used to generate text of markup languages by code.

## breeze-functional

Provide extensions for functional programming.

## breeze-generator

Provide a generator api。

* `RandomGenerator`: To generate random value by specific type and parameters.
* `DefaultGenerator`: To generate default value by specific type and parameters.
* `ContextGenerator`: To generate expected value by the context and specific logic.
* `ScriptingGenerator`: To generate expected value by specific script content.

## breeze-http

Provide a concise and intuitive http api, based on the Java native http api, used to send http requests.

## breeze-javafx

Provide extensions for javafx.

## breeze-logger

Provide a simple logger api.

## breeze-reflect

Provide extensions for java reflection and kotlin reflection.

## breeze-serialization

Provides a concise and unified serializer api, based on third-party libraries, for serialize and deserialize data.

## breeze-time

Provide extensions for java time api.

# Reference

## Frameworks & Libraries & Implementations

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
* [Json Pointer](https://tools.ietf.org/html/rfc6901)
* [Json Path](https://github.com/json-path/JsonPath)
* [langx-java](https://github.com/fangjinuo/langx-java)

# Usage

This project has been published on [Maven Central](https://repo1.maven.org/maven2).

## Maven

In `pom.xml`:
  
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

In `build.gradle`:

```groovy
dependencies {
    implementation "icu.windea.breezeframework:$module:$version"
    //...
}
```

## Gradle Kts

In `build.gradle.kts`:

```kotlin
dependencies {
    implementation("icu.windea.breezeframework:$module:$version")
    //...
}
```