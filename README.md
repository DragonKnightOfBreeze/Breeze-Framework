# Summary

Breeze-Framework

[Github](https://github.com/DragonKnightOfBreeze/breeze-framework)
[Bintray](https://bintray.com/breeze-knights/breeze-framework)

Integrated code framework based on Kotlin, provide many useful extensions for standard library and some frameworks.

**NOTE**
* This framework is not fully implemented & tested. Though you can instantly use some of it's useful features.
* This framework is designed to be used by Kotlin, rather than Java. Though you can obviously use it in Java.
* This framework is updating, and will provide more modules and functions in future.

# Modules

## breeze-core

* Provide general extensions for `String`, `Collection`, `Number`, `Boolean`, `Tuple`, etc. (e.g, `toFile`, `anyIn`.)
* Provide global extensions missing in stdlib. (e.g, `FIXME`, `tryCatching`, `accept`.)
* Provide basic annotations, consts, enums and interfaces.

## breeze-data

* Provide generator extensions for `equals`, `hashcode` and `toString`.
* Provide some useful extensions for data-use. (e.g, `toPropertyMap`, `serialize`)
* Provide multi-data-type serializers delegate to third-party libraries such as `Gson`, `SnakeYaml`.
* [ ] Provide lightweight multi-datatype serializers implementations.
* [ ] Fully adapt to `kotlinx.serialization`.

## breeze-dream

* 〒▽〒 Please ignore it, for it's my daydream.

## breeze-dsl

* Provide dsl builders for common markup languages such as `Xml`, `Markdown`.
* Provide dsl builders for some graph languages such as `Mermaid`, `PlantUml`.

## breeze-functional

* Provide functional extensions for Functions. (e.g, `curried`, `compose`.)

## breeze-fxgl

* Provide some useful extensions for `FXGL`, a javafx-based game framework.
* **May be removed in future.**

## breeze-game

* Provide some useful extensions for game.
* **Should be platform-independent.**

## breeze-generator

* Provide generators for string and text. (e.g, `UrlGenerator`)

## breeze-javafx

* Provide some useful extensions for javafx.

## breeze-logger

* [ ] Provide lightweight, individual, and powerful logger for Kotlin.

## breeze-reflect

* Provide some useful extensions for kotlin reflect & java reflect. (e.g, `checkClassForName`, `nameOf`)

## breeze-spring-boot

* Provide some useful extensions for `SpringBoot` and it's optional modules.

## breeze-spring-cloud

* Provide some useful extensions for `SpringCloud` and it's optional modules.

## breeze-text

* Provide some useful extensions for text, including i18n text and humanized text.
* **Should be usage-specific.**

## breeze-time

* Provide some useful extensions for time, including `Date`, `Temporal`, etc.
* **Split from `breeze-core`.**

# Optional Dependencies

* Kodein-di
* Spekframework
* Anko
* SpringBoot
* SpringCloud

# References

* [khronos](https://github.com/hotchemi/khronos)
* [klutter](https://github.com/kohesive/klutter)
* [Humanizer.jvm](https://github.com/MehdiK/Humanizer.jvm)
* [funktionale](https://github.com/MarioAriasC/funKTionale/tree/master/funktionale-composition)
* [Google Guava](https://github.com/google/guava)

Thanks for providing train of thought and ideas!

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
    println("1abc2def3".substrings("\\d(\\w*)\\d(\\w*)\\d".toRegex()))
    //1{0}2{1}3{2}
    println("1{}2{}3{}".replaceIndexed("{}") { "{$it}" })
    //**********
    println("*" * 10)
    //  <element>
    //    Here also indented.
    //  </element>
    println("""
      <element>
        Here also indented.
      </element>
    """.toMultilineText())
    	
    //abcAbc
    println("Abc abc".switchTo(camelCase))
    //AbcAbc
    println("ABC_ABC".switchTo(PascalCase))
    //ABC_ABC
    println("abc-abc".switchTo(SCREAMING_SNAKE_CASE))
    //a.b[1][2].c[3]
    println("/a/b/1/2/c/3".switchTo(StandardReference))
}
```
