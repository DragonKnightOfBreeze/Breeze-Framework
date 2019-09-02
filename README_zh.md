# 概述

[Breeze-Framework](https://github.com/DragonKnightOfBreeze/Breeze-Framework)

基于Kotlin的整合代码框架，为标准库和其他框架提供各种有用的扩展功能。

**NOTE:** 项目并未完全实现，且未经过充分测试。

**NOTE:** 尽管如此，这个框架仍然能够为你带来许多便利。

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
    println("Abc abc".to(camelCase))
    //AbcAbc
    println("ABC_ABC".to(PascalCase))
    //ABC_ABC
    println("abc-abc".to(SCREAMING_SNAKE_CASE))
    //a.b[1][2].c[3]
    println("/a/b/1/2/c/3".to(StandardReference))
}
```

# 模块

* [x] breeze-core：核心模块。为字符串、集合等提供各种有用的扩展，提供一些通用的生成器、注解、枚举、接口等。
* [ ] breeze-data：数据模块。为json、yaml、xml、properties等数据类型提供Dsl、读取器和解析器。
* [x] breeze-dream：白日梦模块。内含众多幻想元素，**你应该用不上。**
* [ ] breeze-game：游戏模块。为游戏应用提供一些不依赖于平台的功能实现。
* [x] breeze-spring-boot：对Spring Boot的扩展。
* [ ] breeze-spring-cloud：对Spring Cloud的扩展。
* [x] breeze-text：文本模块。为字符串提供特定领域的扩展和生成器。包括字符串转中文、字符串转英文等。
* [x] breeze-time：时间模块。为时间提供扩展、内联扩展。允许使用类dsl式语法创建时间。

# 可选依赖

* Kodein-di
* Spekframework
* Anko
* ……

# 参考

* [khronos](https://github.com/hotchemi/khronos)
* [klutter](https://github.com/kohesive/klutter)
* [Humanizer.jvm](https://github.com/MehdiK/Humanizer.jvm)

感谢提供思路和灵感！
