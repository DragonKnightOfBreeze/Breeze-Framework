// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*


/**
 * Dsl.
 *
 * Dsl defines an actual domain specific language, and is the top node of a dsl definition structure.
 */
@DslApiMarker
interface Dsl {
	override fun toString():String
}

/**
 * Dsl entry.
 *
 * Dsi entry is a special node of a dsl definition structure, which can include various dsl elements.
 */
@DslApiMarker
interface DslEntry {
	@InternalApi
	fun toContentString():String = ""
}

/**
 * Dsl element.
 *
 * Dsl element is a general node of a dsl definition structure.
 */
@DslApiMarker
interface DslElement {
	override fun toString(): String
}

/**
 * Dsl Configuration.
 */
interface DslConfig

/**
 * Dsl constant set.
 */
@InternalApi
object DslConstants {
	val ls: String = System.lineSeparator()
	val ss: String = "$ls$ls"

	fun String.normalWrap() = this.replace("\n", "\\n").replace("\r", "\\r")
}


/**包含可换行的内容。这个接口的优先级要高于[Indentable]。*/
@DslApiMarker
interface Wrappable {
	/**是否需要对内容进行换行。*/
	var wrapContent:Boolean

	@InternalApi
	fun String.doWrap(extraCondition:Boolean = true,transform:(String)->String):String {
		return if(wrapContent && extraCondition) transform(this) else this
	}
}

/**包含可缩进的内容。*/
@DslApiMarker
interface Indentable {
	/**是否需要对内容进行缩进。*/
	var indentContent:Boolean

	@InternalApi
	fun String.doIndent(indent:String, extraCondition:Boolean = true):String {
		return if(indentContent && extraCondition) this.prependIndent(indent) else this
	}
}

/**包含可以空行分隔的内容。*/
@DslApiMarker
interface Splitable {
	/**是否需要对内容以空行分隔。*/
	var splitContent:Boolean

	@InternalApi
	fun Array<*>.doSplit(extraCondition:Boolean = true):String{
		return if(splitContent && extraCondition) this.joinToText(DslConstants.ss) else this.joinToText(DslConstants.ls)
	}
}

/**包含可被生成的内容。*/
@DslApiMarker
interface Generatable {
	/**是否需要生成文本。*/
	var generateContent:Boolean

	/**生成文本。*/
	@InternalApi
	fun String.doGenerate(extraCondition:Boolean = true,transform:(String)->String):String {
		return if(generateContent && extraCondition) transform(this) else this
	}
}


/**带有一个可用于查询的编号。*/
@DslApiMarker
interface WithId {
	/**编号。（不需要保证唯一性）*/
	val id:String
}

/**包含一对可被视为节点的子元素。*/
@DslApiMarker
interface WithNode {
	/**源结点的编号。*/
	val sourceNodeId:String

	/**目标结点的编号。*/
	val targetNodeId:String
}

/**包含有可被视为转化的子元素。*/
@DslApiMarker
interface WithTransition<in N : WithId, T : WithNode> {
	/**根据节点元素创建过渡元素。*/
	@DslApiMarker
	infix fun String.links(other:String):T

	/**根据节点元素创建过渡元素。*/
	@DslApiMarker
	infix fun String.links(other:N):T = this@WithTransition.run { this@links links other.id }

	/**根据节点元素创建过渡元素。*/
	@DslApiMarker
	infix fun N.links(other:String):T = this@WithTransition.run { this@links.id links other }

	/**根据节点元素创建过渡元素。*/
	@DslApiMarker
	infix fun N.links(other:N):T = this@WithTransition.run { this@links.id links other.id }

	/**根据节点元素连续创建过渡元素。*/
	@DslApiMarker
	infix fun T.links(other:String):T = this@WithTransition.run { this@links.targetNodeId links other }

	/**根据节点元素连续创建过渡元素。*/
	@DslApiMarker
	infix fun T.links(other:N):T = this@WithTransition.run { this@links.targetNodeId links other.id }
}
