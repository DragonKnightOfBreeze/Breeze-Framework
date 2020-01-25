# 概述

[Github](https://github.com/DragonKnightOfBreeze/breeze-framework)

基于Kotlin的整合代码框架，为标准库和其他框架提供各种有用的扩展。

注意：

* 这个框架并未完全实现其功能，且未经过充分的测试。尽管它现在已经具备了许多有用的功能。
* 这个框架是为Kotlin设计的，而非Java。尽管你自然可以在Java中使用它。
* 这个框架正在更新中，未来会提供更多的模块和功能。

# 模块

* breeze-core
* breeze-data
* breeze-dsl
* breeze-functional
* breeze-game
* breeze-generator
* breeze-http
* breeze-javafx
* breeze-linq
* breeze-logger
* breeze-reflect
* breeze-serialization
* breeze-spring-boot
* breeze-test
* breeze-text
* breeze-time

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

## 用法

可能需要用到的api key: `5558ef52e2a46a0b88182227efba5dcc60a77499`。

## Maven

```xml
<project>
  ...
  <dependencies>
    <dependency>
      <groupId>com.windea.breezeframework</groupId>
      <artifactId>${module}</artifactId>
      <version>${version}</version>
    </dependency>
  </dependencies>
  ...
  <repositories>
    <repository>
      <id>github-packages</id>
      <name>Github Packages</name>
      <url>https://maven.pkg.github.com/dragonknightofbreeze/breeze-framework</url>
    </repository>
  </repositories>
</project>
```

## Gradle

```groovy
repositories {
    maven { url "https://maven.pkg.github.com/dragonknightofbreeze/breeze-framework" }
}

dependencies {
    implementation "com.windea.breezeframework:$module:$version"
}
```

## Gradle Kts

```kotlin
repositories {
    maven{ url = uri("https://maven.pkg.github.com/dragonknightofbreeze/breeze-framework") }
}

dependencies {
    implementation("com.windea.breezeframework:$module:$version")
}
```

# 示例

参见：[ExampleTest.kt](breeze-core/src/test/kotlin/com/windea/breezeframework/core/tests/ExampleTest.kt).

链接：[Github Link](https://github.com/DragonKnightOfBreeze/Breeze-Framework/blob/master/breeze-core/src/test/kotlin/com/windea/breezeframework/core/tests/ExampleTest.kt).
