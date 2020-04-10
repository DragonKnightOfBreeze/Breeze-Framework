@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.core.types.*
import com.windea.breezeframework.dsl.DslConstants.ls
import com.windea.breezeframework.dsl.DslConstants.ss

//规定：
//所有的Dsl元素的构造方法都必须是@Published internal。
//所有的Dsl元素和Dsl构建方法都必须添加对应的Dsl注解。
//运算符重载规则：`+"text"`表示文本，`-"text"`表示注释，`!"text"`表示内联子元素，`"text"{ }`表示块子元素。
//文本属性以外的默认属性通过内联中缀方法构建。
//Dsl构建方法需要尽可能地写成扩展方法。
//Dsl的主要功能是生成处理后的字符串，尽量避免添加其他无关的功能。

//注意：
//通过抑制编译错误，可以做到：
//内部可见性的内联类构造器
//非顶级声明的内联类
//不为json和yaml提供dsl，因为它们可以非常方便地使用集合结构表示

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

//TODO 让注解处理器生效
/**注明了这个注解定义了一个领域特定语言文件。这个文件是由Kotlin脚本编写的，其输出结果是一个[DslDocument]。*/
@MustBeDocumented
@Target(AnnotationTarget.FILE)
annotation class DslFile


/**Dsl文档。*/
interface DslDocument {
	override fun toString():String
}

/**Dsl元素。*/
interface DslElement {
	override fun toString():String
}

/**Dsl入口。*/
interface DslEntry {
	@InternalUsageApi
	fun contentString():String = ""
}

/**Dsl常量集。*/
object DslConstants{
	@InternalUsageApi val ls:String = System.lineSeparator()
	@InternalUsageApi val ss:String = "$ls$ls"
}


/**包含可缩进的内容。*/
@Dsl
interface Indent {
	/**是否需要对内容进行缩进。*/
	var indentContent:Boolean

	@InternalUsageApi
	fun String.doIndent(indent:String, extraCondition:Boolean = true):String {
		return if(indentContent && extraCondition) this.prependIndent(indent) else this
	}
}

/**包含可被生成的内容。*/
@Dsl
interface Generate {
	/**是否需要生成文本。*/
	var generateContent:Boolean

	/**生成文本。*/
	@InternalUsageApi
	fun String.doGenerate(extraCondition:Boolean = true,transform:(String)->String):String {
		return if(generateContent && extraCondition) transform(this) else this
	}
}

/**包含可换行的内容。这个接口的优先级要高于[Indent]。*/
@Dsl
interface WrapLine {
	/**是否需要对内容进行换行。*/
	var wrapContent:Boolean

	@InternalUsageApi
	fun String.doWrapLine(extraCondition:Boolean = true,transform:(String)->String):String {
		return if(wrapContent && extraCondition) transform(this) else this
	}
}

/**包含可以空行分隔的内容。*/
@Dsl
interface SplitLine {
	/**是否需要对内容以空行分隔。*/
	var splitContent:Boolean

	@InternalUsageApi
	fun Array<*>.doSplitLine(extraCondition:Boolean = true):String{
		return if(splitContent && extraCondition) this.typingAll(ss) else this.typingAll(ls)
	}
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


/**设置是否缩进内容。*/
@InlineDslFunction
@Dsl
inline infix fun <T : Indent> T.indent(value:Boolean) = apply { indentContent = value }

/**设置是否换行内容。*/
@InlineDslFunction
@Dsl
inline infix fun <T : WrapLine> T.wrap(value:Boolean) = apply { wrapContent = value }

/**设置是否分割内容。*/
@InlineDslFunction
@Dsl
inline infix fun <T : SplitLine> T.split(value:Boolean) = apply { splitContent = value }

/**设置是否生成内容。*/
@InlineDslFunction
@Dsl
inline infix fun <T : Generate> T.generate(value:Boolean) = apply { generateContent = value }
