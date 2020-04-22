package com.windea.breezeframework.dsl

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.core.types.*

/**包含可换行的内容。这个接口的优先级要高于[Indentable]。*/
@Dsl
interface Wrappable {
	/**是否需要对内容进行换行。*/
	var wrapContent:Boolean

	@InternalUsageApi
	fun String.doWrap(extraCondition:Boolean = true,transform:(String)->String):String {
		return if(wrapContent && extraCondition) transform(this) else this
	}
}

/**包含可缩进的内容。*/
@Dsl
interface Indentable {
	/**是否需要对内容进行缩进。*/
	var indentContent:Boolean

	@InternalUsageApi
	fun String.doIndent(indent:String, extraCondition:Boolean = true):String {
		return if(indentContent && extraCondition) this.prependIndent(indent) else this
	}
}

/**包含可以空行分隔的内容。*/
@Dsl
interface Splitable {
	/**是否需要对内容以空行分隔。*/
	var splitContent:Boolean

	@InternalUsageApi
	fun Array<*>.doSplit(extraCondition:Boolean = true):String{
		return if(splitContent && extraCondition) this.typingAll(DslConstants.ss) else this.typingAll(DslConstants.ls)
	}
}

/**包含可被生成的内容。*/
@Dsl
interface Generatable {
	/**是否需要生成文本。*/
	var generateContent:Boolean

	/**生成文本。*/
	@InternalUsageApi
	fun String.doGenerate(extraCondition:Boolean = true,transform:(String)->String):String {
		return if(generateContent && extraCondition) transform(this) else this
	}
}

/**可以转换成Html标签。*/
@Dsl
interface HtmlConvertable{
	//TODO
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


/**设置是否缩进内容。*/
@InlineDslFunction
@Dsl
infix fun <T : Indentable> T.indent(value:Boolean) = apply { indentContent = value }

/**设置是否换行内容。*/
@InlineDslFunction
@Dsl
 infix fun <T : Wrappable> T.wrap(value:Boolean) = apply { wrapContent = value }

/**设置是否分割内容。*/
@InlineDslFunction
@Dsl
 infix fun <T : Splitable> T.split(value:Boolean) = apply { splitContent = value }

/**设置是否生成内容。*/
@InlineDslFunction
@Dsl
infix fun <T : Generatable> T.generate(value:Boolean) = apply { generateContent = value }
