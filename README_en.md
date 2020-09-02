# Summary

[中文文档](README.md) | [English Document](README_en.md)

[Github](https://github.com/DragonKnightOfBreeze/breeze-framework)

Integrated code framework written by Kotlin,
provides many useful extensions for standard library and some frameworks.

NOTE：

* This framework is not fully implemented. But you can instantly use some of it's useful features.
* This framework is designed to be used by Kotlin-JVM. But you can obviously use it in Java.
* This framework is updating, and will provide more modules and functions in future.

# Modules

## breeze-core

* Provide generic extensions for String, Collections, Number, Boolean, Tuple, etc.
  * Including some extra operator extensions for String, Collections. e.g, `String.times`, `Collection.div`.
  * Including some extended infix extensions for String, Collections. e.g, `String.startsWith`, `Iterable.allIn`.
  * Including some powerful handle extensions for String. e.g, `String.quote`, `String.escapeBy`, `String.switchCaseBy`.
  * Including some deep operation extensions for Collection. e.g, `List.deepGet`, `MutableList.deepSet`, `Iterable.deepQuery`.
  * Including some deep operation extensions for Tuple. e.g, `Tuple.map`, `Tuple.toList`, `Tuple.toRange`.
  * Including some convert extensions for Number, String etc. e.g, `String.toFile`, `String.toEnumValue`.
* Provide global extensions missing in stdlib.
  * Including some todo extensions. e.g, `FIXME`, `DELAY`.
  * Including some scope extensions. e.g, `once`, `tryOrPrint`, `tryOrIgnore`.
  * Including some useful extension associated to type. e.g, `javaTypeOf<T>()`, `cast<T>()`
* Provide basic annotations, consts, enums and interfaces maybe useful.
* Provide generator extensions for `equals`, `hashcode` and `toString`. e,g, `equalsBy`, `equalsByOne`

## breeze-dsl

* Powerful and clear builders for various domain specific languages.
  * Provide dsl builders for common markup languages such as `Xml`, `Markdown`, `Creole`.
  * Provide dsl builders for some graph languages such as `Mermaid`, `PlantUml`, `Flow`, `Sequence`.
  * Provide dsl builders for specific text such as `CriticMarkupText`, `CommandLineText`.

## breeze-functional

* Provide functional extensions for Functions. e.g, `curried`, `partial`, `compose`.
* Provide functional extensions for Functions from 0 to 11 parameters.

## breeze-generator

* Provide generators for string and text. (e.g, `UrlGenerator`)

## breeze-http

* Provide convenient http extensions delegated to `java.net.http.HttpClient`.
* Simple and convenient API which is similar to `axios`.
* Based on string body, so you should provide external json serializer implementation such as `Gson`, `Jackson`.

## breeze-javafx

* Provide some useful extensions for javafx.

## breeze-linq

* Provide simulate language integrated search implementation.
* Can be implemented by delegating to Kotlin Collection or Java Stream.
* Do not store collection information, and can be defined independent.

## breeze-logger

* Provide lightweight, individual but enough powerful loggers for kotlin.
* Provide basic implementations such as `SimpleLogger`, `ColorfulLogger`.
* This implementation is a little simple, please consider for a while before use it.

## breeze-mapper

* Provide some useful extensions for data mapping. e.g, `mapObject`, `unmapObject`.
* Provide lightweight, individual but enough powerful mappers for data serialization.
* Provide basic implementations such as `JsonMapper`, `YamlMapper`.
* DO NOT FULLY IMPLEMENTED.

## breeze-reflect

* Provide some useful extensions for kotlin reflect & java reflect. e.g, `checkClassForName`, `nameOf`.

## breeze-serializer

* Provide some useful extensions for data serialization. e.g, `serialize`, `deserialize`.
* Provide multi-data-type serializers delegate to third-party libraries such as `Jackson`, `Gson`, `FastJson`.

## breeze-spring-boot

* Provide some useful extensions for `SpringBoot` and it's optional modules.

## breeze-time

* Provide some useful extensions for time, including `Date`, `Temporal`, etc.
* Including necessary dsl-like extensions. e.g, `20.minutes`, `20.minutes.ago`.
* Including necessary convenient check extensions. e.g, `LocalDate.isToday`, `LocalDate.isInFuture`.

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
