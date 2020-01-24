# 笔记

## 未整理

* Kotlin中不可进行隐式转化，例如，将Int转化为Long，必须隐式调用`anInt.toLong()`方法。
* Kotlin中的包名可以为lowercase、camelCase、snake_case或者中文，不能包含`-`。推荐使用camelCase。
* Kotlin中的项名（类、变量等）可以为任意合法的utf-8字符，不能包含`.`。需要的时候可用反斜杠包起来。
* `System.exit(0)`中作为参数的Int值表示的是错误码，可用16进制表示。应当提供错误码-错误提示表。
* 变量可以由匿名方法、lambda表达式赋值。（invoke运算符重载）
* Kotlin不能像Scala一样定义任意运算符，但可以使用中缀方法满足一般需求。
* 可以使用`@MagicConstant`让目标变量/参数只能接受在同一个类中定义的相同类型的常量。
* Kotlin可以遍历密封类，并且不需要else分支。
* 使用操作符重载`next`和`hasNext`（或仅`iterator`方法）来使自定义的类可迭代。
* 当接口的默认实现有冲突时，可以传递限定泛型给`super`关键字。
* 如果（同名的）方法重载不能用默认参数表示，即表明这个重载是有问题的。
* Jvm签名不包含方法的返回值，所有当方法的名称一致，参数一致时，返回类型必须一致。
    * 在kotlin中可以通过`@JvmName`解决这类冲突。
* 常见的作用域函数：`with`,`run`,`with`,`apply`,`also`,`use`。
* 柯里化(curried)：由一个多参数的函数转化为返回多层嵌套单参数的函数的过程。即，`f(a,b,c) -> f(a)(b)(c)`
* 偏函数(partial)：对于一个柯里化函数的返回结果传入数层但未满层的参数后，得到的多层嵌套函数。即，`f(a,b,c)-> f(a)(b)`。

## 编译器插件

* `allopen`、`noArg`插件都可以应用在data class上。（需要添加标记注解。）
* 对于Jpa实体类需要使用`noarg`和`allopen`插件，使用var属性。
* 对于Spring bean需要使用`allopen`插件。
* 对于json持久化也需要使用`noarg`插件，可以使用val属性（直接操作字段 ）。
* 使用方法：配置插件，指定特定注解。为需要的类添加该注解，或该元注解（注有该注解的注解）。
* 之后插件将会适用于所有注有该注解（或其元注解）的类/接口（或其子类/实现类）。

## 反射

* java中的反射可以更改只读变量val和常量final。
* `getDeclaredMethod`：得到类中所有声明的方法
* `getMethod`：得到类中所有有访问权限的方法。
* 名如`XxxKt`的类在kotlin中是不可见的，但在java中可见。但可以通过`Class.forName()`得到。
* 通过kotlin反射未属性设置值：`(kClass.memberProperties as KMutableProperty1<T,R>).set(...)`。
* `person.class`和`person.javaClass.kotlin`的区别：前者有协变，返回的是`KClass<out T>`，后者则是`KClass<T>`。
* Kotlin反射的性能要比原生java反射的性能低很多。

## 泛型

* 泛型：泛化的类型或者类型的抽象。
* 真泛型：C#，伪泛型：Java，Kotlin。
* 伪泛型：编译之后泛型会被擦除。在真正运行时不能得到泛型的类型。
* Java为什么要用伪泛型：为了向前兼容。
* Kotlin的具象化泛型：`inline fun <reified T> test()`，可以得到运行时类型。部分实现。
* 协变与逆变：`out`用于返回值，返回值可为其子类；`in`用于传入参数，传入参数可为其父类/接口。其泛型类拥有对应的继承关系。
* `@UnsafeVariance`：：当泛型参数/返回值的协变与逆变有冲突时注上。
* 星投影：取最通用的类型。`out Any?`和`in Nothing`。传入泛型实参的地方不能用星投影。

## Kotlin和Java互操作

* Kotlin中可以用一个lambda表示Java中的函数式接口（只有一个抽象方法的接口）。
* Kotlin中得到字符串中匹配正则的所有匹配结果：`regex.findAll()`。
* Kotlin中使用`regex.matches()`时，正则前后不需要加`^`和`$`。正则仅用于匹配时，也不需要捕捉分组。

## Kotlin脚本

* 后缀名为`.kts`，可以通过IDEA运行，也可以通过kotlinc运行，也可以作为Gradle构建脚本。
* 需要的依赖：`kotlin-script-util`。额外包含一些用于构建脚本的注解。

## Kotlin Android

* Gradle插件：`kotlin-android`，`kotlin-android-extensions`
* 需要的依赖：`anko`。

# 灵感

* ［不确定必要性］扩展方法： `FunctionN.curried()`，`FunctionN.uncurried()`
    * 所属文件：FunctionalExtensions.kt
    * 使用示例：`::f.curried()(a)(b)(c)`。
* ［不确定］扩展方法：`Function2+.partialN()`

# 特性请求

* 标准库提供的String、Container的更多的合乎逻辑的运算符重载。
* 具有多个接受者的扩展方法。
* 允许对字符串模版进行运算符重载。
* 允许对返回值为Boolean的中缀方法使用非运算符。如`"abc" !startsWith "a"`。
* 标准库应当提供更多的TODO方法，并允许用户自定义TODO注解和方法。
* 允许可重复的`SOURCE`以外的注解。
