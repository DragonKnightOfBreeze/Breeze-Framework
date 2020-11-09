# 备注

规定：

* 所有的Dsl元素的构造方法都必须是@Published internal。
* 所有的Dsl元素和Dsl构建方法都必须添加对应的Dsl注解。
* Dsl构建方法需要尽可能地写成扩展方法。
* Dsl构建方法仅在必要时写成内联方法。

运算符重载规则：

* `+"inlineText"`表示文本
* `-"inlineText"`表示注释
* `!"inlineText"`表示内联子元素
* `"inlineText"{ }`表示块子元素。

通过抑制编译错误，可以做到：

* 内部可见性的内联类构造器
* 非顶级声明的内联类

