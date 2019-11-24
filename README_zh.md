# 概述

Breeze-Framework

[Github](https://github.com/DragonKnightOfBreeze/breeze-framework)
[Bintray](https://bintray.com/windea/breeze-framework)

基于Kotlin的整合代码框架，为标准库和其他框架提供各种有用的扩展。

**注意**

* 这个框架并未完全实现其功能，且未经过充分的测试。尽管它现在已经具备了许多有用的功能。
* 这个框架是为Kotlin设计的，而非Java。尽管你自然可以在Java中使用它。
* 这个框架正在更新中，未来会提供更多的模块和功能。

**用法**

```
repositories {
    maven("https://dl.bintray.com/windea/breeze-framework")
}

dependencies {
    implementation("com.windea.breezeframework:breeze-core:$version")
}
```

# 模块

* **breeze-core**
* **breeze-data**
* **breeze-dsl**
* **breeze-functional**
* **breeze-fxgl**
* **breeze-game**
* **breeze-javafx**
* **breeze-reflect**
* **breeze-serialization**
* **breeze-spring-boot**
* **breeze-spring-cloud**
* **breeze-text**
* **breeze-time**

# 依赖 & 可选依赖

* Kodein-di
* Spekframework
* Anko
* SpringBoot
* SpringCloud

***

* [MicroUtils/kotlin-logging](https://github.com/MicroUtils/kotlin-logging)
* [pmwmedia/tinylog](https://github.com/pmwmedia/tinylog)
* [charleskorn/kaml](https://github.com/charleskorn/kaml)
* [MiloszKrajewski/stateful4k](https://github.com/MiloszKrajewski/stateful4k)

# 参考

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

# 示例

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
