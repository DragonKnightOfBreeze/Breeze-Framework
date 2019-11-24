# Summary

## Breeze-Framework

[Github](https://github.com/DragonKnightOfBreeze/breeze-framework)
[Bintray](https://bintray.com/windea/breeze-framework)

Integrated code framework based on Kotlin, provide many useful extensions for standard library and some frameworks.
Including: basic & functional & reflect & text & time extensions & linq & dsls & generators & delegated serializers and more.

## NOTE

* This framework is not fully implemented & tested. Though you can instantly use some of it's useful features.
* This framework is designed to be used by Kotlin-Jvm, rather than Java. Though you can obviously use it in Java.
* This framework is updating, and will provide more modules and functions in future.

## Usage

build.gradle

```groovy
repositories{
    maven { url "https://dl.bintray.com/windea/breeze-framework" }
}

dependencies {
    implementation "com.windea.breezeframework:$module:$version"
}
```

build.gradle.kts

```kotlin
repositories {
    maven("https://dl.bintray.com/windea/breeze-framework")
}

dependencies {
    implementation("com.windea.breezeframework:$module:$version")
}
```

# Modules

## breeze-core

* Provide general extensions for `String`, `Collection`, `Number`, `Boolean`, `Tuple`.
    * Including many extra operator override extensions. (e.g, `String.times(Int)`, `Collection.get(IntRange)`.)
    * Including many extended infix extensions. (e.g, `String.equalsIc`, `String.startsWith`, `Iterable.anyIn`.)
    * Including some powerful handler extensions for `String`. (e.g, `String.escapeBy`, `String.switchCaseBy`.)
    * Including some deep operator extensions for `Collection`. (e.g, `List.deepGet`, `List.deepFlatten`.)
    * Including extra convert extensions for `Number`, `String`, etc. (e.g, `String.toFile`, `String.toEnumValue`.)
* Provide global extensions missing in stdlib. (e.g, `FIXME`, `once`, `accept`, `tryOrPrint`, `tryOrIgnore`.)
* Provide basic annotations, consts, enums and interfaces.
* Provide generator extensions for `equals`, `hashcode` and `toString` (**YEAH IT DO!**).

## breeze-data

* Provide some useful extensions for data-use. (e.g, `toPropertyMap`, `serialize`)
* Provide multi-data-type serializers delegate to third-party libraries such as `Gson`, `SnakeYaml`, `Jackson`.

## breeze-dsl

Powerful and clear builders for various domain specific languages.

* Provide dsl builders for common markup languages such as `Xml`, `Markdown`, `Creole`.
* Provide dsl builders for some graph languages such as `Mermaid`, `PlantUml`, `Flow`, `Sequence`.
* Provide dsl builders for specific text such as `CriticMarkupText`, `CommandLineText`.

Now support:

* graph
    * mermaid
        * [X] MermaidClassDiagramDsl
        * [X] MermaidFlowChartDsl
        * [X] MermaidGanttDsl
        * [X] MermaidPieChartDsl
        * [X] MermaidSequenceDiagramDsl
        * [X] MermaidStateDiagramDsl
    * puml
        * [ ] PumlSequenceDiagramDsl
        * [ ] PumlStateDiagramDsl
    * [X] FlowDsl
    * [X] SequenceDsl 
* markup
    * [X] CreoleDsl
    * [X] JsonDsl
    * [X] MarkdownDsl
    * [X] XmlDsl
    * [ ] YamlDsl
* text
    * [X] CommandLineTextDsl (You can use it to print colorful text in command line, and **YEAH IT DO!**)
    * [X] CriticMarkupTextDsl
    
Note:
* Dsl is used to generate text, and it's the only thing that dsl should do.
* Dsl do not provide ability to generate no-text files that could be provided by 3rd library/application.
* Dsl can not deserialize data from generated string. 
* Less limit (either type or invocation) dsl is not a good dsl.

## breeze-functional

* Provide functional extensions for Functions. (e.g, `curried`, 'partical', `compose`.)
* Provide functional extensions for Functions from zero parameter to 11 parameter.

## breeze-game

* Provide some useful extensions for game.
* **Should be platform-independent.**

## breeze-generator

* Provide generators for string and text. (e.g, `UrlGenerator`)

## breeze-http

* Provide convenient http extensions delegated to `java.net.http.HttpClient`.

## breeze-javafx

* Provide some useful extensions for javafx.

## breeze-linq

* Provide simulate language integrated search implementation.
* Can be implemented by delegating to Kotlin Collection or Java Stream.
* Do not store collection information, and can be defined independent.

Usage:
```
listOf("foo", "Bar", "FooBar") linq (from<String>() where { it.length <= 5 } select { it.toLowerCase() }
```

## breeze-logger

* Provide lightweight, individual, and powerful logger for Kotlin.
* Default implementation is `ColorfulLogger`, which print colorful text in command line.

## breeze-reflect

* Provide some useful extensions for kotlin reflect & java reflect. (e.g, `checkClassForName`, `nameOf`.)

## breeze-serialization

* Provide serializers for common kotlin type. (e.g, `RangeSerializer`) 
* [ ] Provide lightweight multi-data-type serializers implementations.
* [ ] Linked with related Dsl.

## breeze-spring-boot

* Provide some useful extensions for `SpringBoot` and it's optional modules.

## breeze-spring-cloud

* Provide some useful extensions for `SpringCloud` and it's optional modules.

## breeze-test

TODO

## breeze-text

* Provide some useful extensions for text, including i18n text and humanized text.
* **Should be usage-specific.**

## breeze-time

* Provide some useful extensions for time, including `Date`, `Temporal`, etc.
* Including necessary dsl-like extensions. (e.g, `20.minutes`, `20.minutes.ago`.) (**YEAH IT DO!**)
* Including necessary convenient check extensions. (e.g, `LocalDate.isToday`, `LocalDate.isInFuture`.) 

# Dependencies & Optional dependencies

* Kodein-di
* Spekframework
* Anko
* SpringBoot
* SpringCloud
* LibGDX
***
* [MicroUtils/kotlin-logging](https://github.com/MicroUtils/kotlin-logging)
* [pmwmedia/tinylog](https://github.com/pmwmedia/tinylog)
* [charleskorn/kaml](https://github.com/charleskorn/kaml)
* [MiloszKrajewski/stateful4k](https://github.com/MiloszKrajewski/stateful4k)

# References

* [Google Guava](https://github.com/google/guava)
***
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

# Example

```kotlin
fun example() {
    //true
    println(arrayOf(1, 2, 3) anyIn arrayOf(3, 4, 5))
    //{[0]=1, [1]=2, [2]=3, [3][0]=4, [3][1]=5, [4].a=6}
    println(listOf(1, 2, 3, listOf(4, 5), mapOf("a" to 6)).deepFlatten())
    //{0=a, 1=b, 2=c}
    println(listOf("a", "b", "c").toIndexKeyMap())
    
    //[a, b, c, a, b, c, a, b, c]
    println(listOf("a", "b", "c") * 3)
    //[b, c]
    println(listOf("a", "b", "c")[1..2])
    
    //true
    println("Hello world" endsWithIc "World")
    //[abc, def]
    println("1abc2def3".substrings("""\d(\w*)\d(\w*)\d""".toRegex()))
    //1{0}2{1}3{2}
    println("1{}2{}3{}".replaceIndexed("{}") { "{$it}" })
    //**********
    println("*" * 10)
    //[***, ***, ***]
    println("*********" / 3)
    //  <element>
    //    Here also indented.
    //  </element>
    println("""
      <element>
        Here also indented.
      </element>
    """.trimRelativeIndent())
    
    //abcAbc
    println("Abc abc".switchCaseBy(camelCase))
    //AbcAbc
    println("ABC_ABC".switchCaseBy(PascalCase))
    //ABC_ABC
    println("abc-abc".switchCaseBy(SCREAMING_SNAKE_CASE))
    //a.b[1][2].c[3]
    println("/a/b/1/2/c/3".switchCaseBy(StandardReference))
}
```
