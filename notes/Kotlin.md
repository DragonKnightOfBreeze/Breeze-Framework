# 笔记

* Kotlin中不可进行隐式转换，例如，将Int转化为Long，必须隐式调用`anInt.toLong()`方法。
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
* `allopen`、`noArg`插件都可以应用在data class上。（需要添加标记注解。）
* 常见的作用域函数：`with`,`run`,`with`,`apply`,`also`,`use`。
* 柯里化(curried)：由一个多参数的函数转化为返回多层嵌套单参数的函数的过程。即，`f(a,b,c) -> f(a)(b)(c)`
* 偏函数(partial)：对于一个柯里化函数的返回结果传入数层但未满层的参数后，得到的多层嵌套函数。即，`f(a,b,c)-> f(a)(b)`。

* 对于Jpa实体类需要使用noarg和allopen插件，使用var属性。
* 对于Spring bean需要使用allopen插件。
* 对于json持久化也需要使用noarg插件，可以使用val属性。
* 以上方式也适用于元注解（注解的注解）。

* 如何使用kotlin实现线程安全（双重校验锁）的单例模式？？？


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

class SingletonHungary {
	private static SingletonHungary singletonHungary = new SingletonHungary();
	//将构造器设置为private禁止通过new进行实例化
	private SingletonHungary() {
		
	}
	public static SingletonHungary getInstance() {
		return singletonHungary;
	}
}
