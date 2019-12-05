@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl

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

//region top annotations and interfaces
/**Dsl。*/
@DslMarker
@MustBeDocumented
internal annotation class Dsl

/**Dsl的构建器。*/
interface DslBuilder {
	override fun toString(): String
}

/**Dsl的配置。*/
interface DslConfig

/**Dsl的入口。*/
interface DslEntry

/**Dsl的元素。*/
interface DslElement {
	override fun toString(): String
}
//endregion

//region dsl annotations
/**表示这个Dsl构建方法是内联的。即，对应的构建方法不会自动注册对应的元素，且允许直接返回字符串。*/
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
annotation class InlineDsl

/**表示这个字符串属性换行。并注明对应的行分隔符和额外条件。*/
@MustBeDocumented
@Target(AnnotationTarget.PROPERTY)
annotation class Multiline(
	/**行分隔符。*/
	val lineSeparator: String,
	/**额外条件。*/
	val condition: String = ""
)
//endregion

//region dsl interfaces
/**包含可换行的内容。这个接口的优先级要高于[CanIndent]。*/
@Dsl
interface CanWrap {
	var wrapContent: Boolean
	
	val wrap: String get() = if(wrapContent) "\n" else ""
}

/**包含可分割的内容。一般使用空行进行分割。*/
@Dsl
interface CanSplit {
	var splitContent: Boolean
	
	val split: String get() = if(splitContent) "\n\n" else "\n"
}

/**包含可缩进的内容。*/
@Dsl
interface CanIndent {
	var indentContent: Boolean
	
	fun String.applyIndent(indent: String, condition: Boolean = true): String {
		return if(indentContent && condition) this.prependIndent(indent) else this
	}
}

/**可生成文本。可能替换原始文本。*/
@Dsl
interface CanGenerate {
	var generateContent: Boolean
	
	fun toGeneratedString(): String
}


/**包含可被视为文本的子元素。*/
@Dsl
interface WithText<T> {
	/**添加主要的文本元素为子元素。*/
	@Dsl
	operator fun String.unaryPlus(): T
}

/**包含可被视为注释的子元素。*/
@Dsl
interface WithComment<T> {
	/**添加注释元素为子元素。*/
	@Dsl
	operator fun String.unaryMinus(): T
}

/**包含可被视为块的子元素。*/
@Dsl
interface WithBlock<T> {
	/**添加主要的块元素为子元素。*/
	@Dsl
	operator fun String.invoke(block: T.() -> Unit = {}): T
}

/**带有一个可用于查询的id。这个id不是唯一的。*/
@Dsl
interface WithId {
	val id: String
}

/**带有一个可用于查询的id。这个id是唯一的。*/
@Dsl
interface WithUniqueId : WithId {
	override fun equals(other: Any?): Boolean
	
	override fun hashCode(): Int
}

/**包含一对可被视为节点的子元素。*/
@Dsl
interface WithNode<N : WithId> {
	val sourceNodeId: String
	val targetNodeId: String
}

/**包含有可被视为转换的子元素。*/
@Dsl
interface WithTransition<N : WithId, T : WithNode<N>> {
	/**根据节点元素创建过渡元素。*/
	@Dsl
	infix fun String.fromTo(other: String): T
	
	/**根据节点元素创建过渡元素。*/
	@Dsl
	infix fun String.fromTo(other: N): T = this@WithTransition.run { this@fromTo fromTo other.id }
	
	/**根据节点元素创建过渡元素。*/
	@Dsl
	infix fun N.fromTo(other: String): T = this@WithTransition.run { this@fromTo.id fromTo other }
	
	/**根据节点元素创建过渡元素。*/
	@Dsl
	infix fun N.fromTo(other: N): T = this@WithTransition.run { this@fromTo.id fromTo other.id }
	
	/**根据节点元素连续创建过渡元素。*/
	@Dsl
	infix fun T.fromTo(other: String): T = this@WithTransition.run { this@fromTo.targetNodeId fromTo other }
	
	/**根据节点元素连续创建过渡元素。*/
	@Dsl
	infix fun T.fromTo(other: N): T = this@WithTransition.run { this@fromTo.targetNodeId fromTo other.id }
}
//endregion

//region build extensions
/**设置是否换行内容。*/
@Dsl
inline infix fun <T : CanWrap> T.wrap(value: Boolean) = this.also { it.wrapContent = value }

/**设置是否缩进内容。*/
@Dsl
inline infix fun <T : CanIndent> T.indent(value: Boolean) = this.also { it.indentContent = value }

/**设置是否分割内容。*/
@Dsl
inline infix fun <T : CanSplit> T.split(value: Boolean) = this.also { it.splitContent = value }

/**设置是否生成内容。*/
@Dsl
inline infix fun <T : CanGenerate> T.generate(value: Boolean) = this.also { it.generateContent = value }
//endregion
