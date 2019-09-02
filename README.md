# Summary

[Breeze-Framework](https://github.com/DragonKnightOfBreeze/Breeze-Framework)

Integrated code framework base on Kotlin,Provide many useful extensions for standard library and some frameworks.

**NOTE:** This project is not fully implemented & tested, and remain to add English document comments.

**NOTE:** Even so, you can still instantly use some of it's useful features.

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
}
```

# Modules

* [x] breeze-core: Core module. Provide extensions for strings, collections and so on, provide basic generators, annotations, enums and interfaces. 
* [ ] breeze-data: Data module. Provide dsls,wrapped loaders & dumpers for json, yaml, xml, properties and so on.  
* [x] breeze-dream: Daydream module. Include many fantasy elements, you shall have no chance to use it. 
* [ ] breeze-game: Game-basics module. Provide some platform-independent implementations for game applications
* [x] breeze-spring-boot: Extensions for Spring Boot.
* [ ] breeze-spring-cloud: Extensions for Spring Cloud.
* [x] breeze-text: Text module. Provide specific extensions and generators. e.x, string2chs, string2eng.
* [x] breeze-time: Temporal module. Provide extensions, inline extensions, dsl-like generators for temporal.

# Optional Dependencies

* Kodein-di
* Spekframework
* Anko
* ……

# References

* [khronos](https://github.com/hotchemi/khronos)
* [klutter](https://github.com/kohesive/klutter)
* [Humanizer.jvm](https://github.com/MehdiK/Humanizer.jvm)

Thanks for providing train of thought and ideas!
