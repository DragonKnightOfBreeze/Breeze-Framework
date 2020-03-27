@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.constants.SystemProperties.lineSeparator
import com.windea.breezeframework.core.types.*

//规定：
//所有的Dsl元素的构造方法都必须是@Published internal。
//所有的Dsl元素和Dsl构建方法都必须添加对应的DslMarker注解。
//所有的Dsl构建方法都要尽可能地写成内联形式和表达式形式，且不要显式声明返回值，使用`Xxx.also{}`的写法。
//运算符重载规则：`+"text"`表示文本，`-"text"`表示注释，`!"text"`表示内联子元素，`"text"{ }`表示块子元素。
//文本属性以外的默认属性通过内联中缀方法构建。
//Dsl构建方法需要尽可能地写成扩展方法。
//Dsl的主要功能是生成处理后的字符串，尽量避免添加其他无关的功能。
//toString()方法的具体实现不要要求过多，只要能够良好地打印字符串即可。
//下划线开头的方法被认为是框架内部的，即使它实际上是公开的

//通过抑制编译错误，可以做到：
//内部可见性的内联类构造器
//非顶级声明的内联类

//编写规则：
//Dsl注解、通用入口接口、通用元素接口放到的顶层
//Dsl文档、配置、元素、枚举放在Dsl接口的内部
//Dsl字符串生成方法尽量不要使用表达式形式编写，因为可能很复杂。
//Dsl构建方法使用表达式形式编写，不明确声明返回类型，尽量声明为扩展方法。
//Dsl元素之上尽量使用接口，而非抽象类，必要时使用内联类
//~~对于Dsl入口，以buildString的方式生成字符串，以提高性能（相比之下可以很快）~~
//~~对于Dsl元素，可以直接使用模版字符串或joinToString~~

//region dsl top declarations
/**领域特定语言。*/
@DslMarker
@MustBeDocumented
annotation class Dsl

/**注明这个注解定义了一个顶级的领域特定语言构建方法。这些方法用于生成文档或进行配置。*/
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
annotation class TopDslFunction

/**注明这个注解定义了一个内联的领域特定语言构建方法。这些方法不会自动注册对应的元素。*/
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
annotation class InlineDslFunction

/**注明这个注解定义了一个领域特定语言构建方法。*/
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
annotation class DslFunction

/**Dsl文档。*/
interface DslDocument {
	override fun toString():String
}

/**Dsl入口。*/
interface DslEntry {
	@InternalUsageApi
	fun contentString():String = ""
}

/**Dsl元素。*/
interface DslElement {
	override fun toString():String
}
//endregion

//region dsl declarations
/**包含可缩进的内容。*/
@Dsl
interface CanIndent {
	/**是否需要对缩进内容。*/
	var indentContent:Boolean

	/**缩进文本。*/
	@InternalUsageApi
	fun String.doIndent(indent:String, condition:Boolean = true):String {
		return if(indentContent && condition) prependIndent(indent) else this
	}
}

/**包含可换行的内容。这个接口的优先级要高于[CanIndent]。*/
@Dsl
interface CanWrapLine {
	/**是否需要对内容进行换行。*/
	var wrapContent:Boolean

	/**用于换行的分隔符。*/
	@InternalUsageApi
	val wrapSeparator:String
		get() = if(wrapContent) lineSeparator else ""

	@InternalUsageApi
	fun String.doWrap():String {
		return if(wrapContent) "$lineSeparator$this$lineSeparator" else this
	}
}

/**包含可以空行分隔的内容。*/
@Dsl
interface CanSplitLine {
	/**是否需要对内容以空行分隔。*/
	var splitContent:Boolean

	/**用于已空行分隔的分隔符。*/
	@InternalUsageApi
	val splitSeparator:String
		get() = if(splitContent) lineSeparator + lineSeparator else lineSeparator
}

/**包含可被生成的内容。*/
@Dsl
interface CanGenerate {
	/**是否需要生成文本。*/
	var generateContent:Boolean

	/**生成文本。*/
	@InternalUsageApi
	fun doGenerate():String
}


/**(No document.)*/
@Dsl
interface UPlus<out T> {
	/**(No document.)*/
	operator fun String.unaryPlus():T
}

/**(No document.)*/
@Dsl
interface UMinus<out T> {
	/**(No document.)*/
	operator fun String.unaryMinus():T
}

/**(No document.)*/
@Dsl
interface Invoke<out T> {
	operator fun String.invoke(block:Block<T> = {}):T
}

interface InvokeArgs<out T> : Invoke<T> {
	operator fun String.invoke(vararg args:Arg, block:Block<T> = {}):T
}


/**带有一个可用于查询的编号。*/
@Dsl
interface WithId {
	/**编号。（不需要保证唯一性）*/
	val id:String
}

/**包含一对可被视为节点的子元素。*/
@Dsl
interface WithNode {
	/**源结点的编号。*/
	val sourceNodeId:String

	/**目标结点的编号。*/
	val targetNodeId:String
}

/**包含有可被视为转化的子元素。*/
@Dsl
interface WithTransition<in N : WithId, T : WithNode> {
	/**根据节点元素创建过渡元素。*/
	@DslFunction
	@Dsl
	infix fun String.links(other:String):T

	/**根据节点元素创建过渡元素。*/
	@DslFunction
	@Dsl
	infix fun String.links(other:N):T = this@WithTransition.run { this@links links other.id }

	/**根据节点元素创建过渡元素。*/
	@DslFunction
	@Dsl
	infix fun N.links(other:String):T = this@WithTransition.run { this@links.id links other }

	/**根据节点元素创建过渡元素。*/
	@DslFunction
	@Dsl
	infix fun N.links(other:N):T = this@WithTransition.run { this@links.id links other.id }

	/**根据节点元素连续创建过渡元素。*/
	@DslFunction
	@Dsl
	infix fun T.links(other:String):T = this@WithTransition.run { this@links.targetNodeId links other }

	/**根据节点元素连续创建过渡元素。*/
	@DslFunction
	@Dsl
	infix fun T.links(other:N):T = this@WithTransition.run { this@links.targetNodeId links other.id }
}
//endregion

//region dsl build extensions
/**设置是否缩进内容。*/
@InlineDslFunction
@Dsl
inline infix fun <T : CanIndent> T.indent(value:Boolean) = apply { indentContent = value }

/**设置是否换行内容。*/
@InlineDslFunction
@Dsl
inline infix fun <T : CanWrapLine> T.wrap(value:Boolean) = apply { wrapContent = value }

/**设置是否分割内容。*/
@InlineDslFunction
@Dsl
inline infix fun <T : CanSplitLine> T.split(value:Boolean) = apply { splitContent = value }

/**设置是否生成内容。*/
@InlineDslFunction
@Dsl
inline infix fun <T : CanGenerate> T.generate(value:Boolean) = apply { generateContent = value }
//endregion
