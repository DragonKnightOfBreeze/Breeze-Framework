# Summary

[中文文档](README.md) | [English Document](README_en.md)

[Github](https://github.com/DragonKnightOfBreeze/breeze-framework)

Integrated code framework based on Kotlin.

Provides many useful extensions for standard library and some frameworks.

NOTE：

* This framework is not fully implemented, but you can instantly use some of it's useful features.
* This framework is designed to be used by Kotlin Jvm, but you can obviously use it in Java.
* This framework is updating, and will provide more modules and functions in future.

# Modules

## breeze-core

Provide utilities and extensions for the Kotlin standard library and common functional requirements.

## breeze-dsl

Provides a concise and flexible dsl api, used to generate text of markup languages by code.

Can be sufficient configured.

## breeze-functional

Provide utilities and extensions for functional programming.

Support functions with 0 to 11 parameters.

## breeze-http

Provide a concise and intuitive http api, based on the Java native http api, used to send http requests.

Can be sufficient configured.

## breeze-javafx

Provide tools and extensions for Java FX.

## breeze-logger

Provide a simple logger api.

## breeze-reflect

Provide utilities and extensions for Java reflection and Kotlin reflection.

## breeze-serialization

Provides a concise and unified serializer api, based on third-party libraries, for serialize and deserialize data.

Can be sufficient configured.

## breeze-spring-boot

Provide utilities and extensions for the Spring Boot Framework.

## breeze-time

Provide utilities and extensions for Java's time api.

# Reference

## Dependencies & Optional dependencies

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

## Implementation References

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

# Usage

This project has been published on JCenter.

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

# Example

See:

* [ExampleTest.kt](breeze-core/src/test/kotlin/ExampleTest.kt)
