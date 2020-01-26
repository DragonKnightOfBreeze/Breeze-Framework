# 概述

* 标准库中的一些存在重载方法的方法，可以使用默认参数简化代码。
* 但是为了保持一致性，本库中的方法会参照相关的标准库中的方法，判断是否使用默认参数。
* 如何抑制编译器错误：参照[DefaultErrorMessages.java]，根据错误信息查找错误名。

[DefaultErrorMessages.java]: https://github.com/JetBrains/kotlin/blob/master/compiler/frontend/src/org/jetbrains/kotlin/diagnostics/rendering/DefaultErrorMessages.java