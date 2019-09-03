# 笔记

* Kotlin中不可进行隐式转换，例如，将Int转化为Long，必须隐式调用`anInt.toLong()`方法。
* Kotlin中的包名可以为lowercase、camelCase、snake_case或者中文，不能包含`-`。推荐使用camelCase。
* Kotlin中的项名（类、变量等）可以为任意合法的utf-8字符，不能包含`.`。需要的时候可用反斜杠包起来。

# 灵感



# 特性请求

* 标准库提供的String、Container的更多的合乎逻辑的运算符重载。
* 具有多个接受者的扩展方法。
* 允许对字符串模版进行运算符重载。
* 允许对返回值为Boolean的中缀方法使用非运算符。如`"abc" !startsWith "a"`。
* 标准库应当提供更多的TODO方法，并允许用户自定义TODO注解和方法。
