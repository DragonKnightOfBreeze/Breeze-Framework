@file:Reference("[PlantUml State Diagram](http://plantuml.com/zh/state-diagram)")
@file:Suppress("CanBePrimaryConstructorProperty", "NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.puml

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.puml.PumlConfig.quote
import org.intellij.lang.annotations.*

//REGION Dsl marker annotations & Dsl element interfaces

@DslMarker
internal annotation class PumlStateDiagramDsl

//REGION Dsl elements & Build functions

/**构建PlantUml状态图。*/
@PumlStateDiagramDsl
fun pumlStateDiagram(builder: PumlStateDiagram.() -> Unit) = PumlStateDiagram().also { it.builder() }

/**PlantUml状态图的元素。*/
@PumlStateDiagramDsl
interface PumlStateDiagramDslElement : PumlDslElement

/**PlantUml状态图。*/
@PumlStateDiagramDsl
class PumlStateDiagram : PumlStateDiagramDslElement, Puml by PumlDelegate() {
	val states: MutableSet<PumlStateDiagramState> = mutableSetOf()
	val links: MutableList<PumlStateDiagramLink> = mutableListOf()
	
	override fun toString(): String {
		val statesSnippet = states.joinToString("\n")
		val linksSnippet = links.joinToString("\n")
		val contentSnippet = "$statesSnippet\n\n$linksSnippet"
		return "@startuml\n${getPrefixString()}\n\n$contentSnippet\n\n${getSuffixString()}\n@enduml"
	}
	
	
	@PumlStateDiagramDsl
	inline fun state(name: String, text: String = "") = PumlStateDiagramState(name, text).also { states += it }
	
	@PumlStateDiagramDsl
	inline fun link(sourceStateId: String, targetStateId: String, text: String = "") =
		PumlStateDiagramLink(sourceStateId, targetStateId, text).also { links += it }
	
	@PumlStateDiagramDsl
	inline fun link(sourceState: PumlStateDiagramState, targetStateId: String, text: String = "") =
		link(sourceState.alias ?: sourceState.name, targetStateId, text)
	
	@PumlStateDiagramDsl
	inline fun link(sourceStateId: String, targetState: PumlStateDiagramState, text: String = "") =
		link(sourceStateId, targetState.alias ?: targetState.name, text)
	
	@PumlStateDiagramDsl
	inline fun link(sourceState: PumlStateDiagramState, targetState: PumlStateDiagramState, text: String = "") =
		link(sourceState.alias ?: sourceState.name, targetState.alias ?: targetState.name, text)
}

//TODO 提取到公共Dsl（tag、color）
/**
 * PlantUml状态图状态。
 *
 * 语法示例：
 * * `state A: text can\n wrap line.`
 * * `state A<<Tag>> #333: state with tag and color.`
 * * `state "Long name" as A: state with long name.`
 */
@PumlStateDiagramDsl
class PumlStateDiagramState(
	@Language("Creole")
	val name: String, //NOTE can wrap by "\n" if alias is not null
	@Language("Creole")
	val text: String = "" //NOTE can wrap by "\n"
) : PumlStateDiagramDslElement {
	var alias: String? = null
	var tag: String? = null
	var color: String? = null
	
	override fun equals(other: Any?): Boolean {
		return this === other || (other is PumlStateDiagramState && (other.alias == alias || other.name == name))
	}
	
	override fun hashCode(): Int {
		return alias?.hashCode() ?: name.hashCode()
	}
	
	override fun toString(): String {
		val textSnippet = text.replaceWithEscapedWrap()
		val tagSnippet = tag?.let { "<<$it>>" } ?: ""
		val colorSnippet = color?.let { " ${it.addPrefix("#")}" } ?: ""
		return if(alias == null) {
			"state $name$tagSnippet$colorSnippet: $textSnippet"
		} else {
			val nameSnippet = name.replaceWithEscapedWrap().wrapQuote(quote) //only escaped with an existing alias
			"state $nameSnippet as $alias$tagSnippet$colorSnippet: $textSnippet"
		}
	}
	
	
	@PumlStateDiagramDsl
	inline infix fun alias(alias: String) = this.also { it.alias = alias }
	
	@PumlStateDiagramDsl
	inline infix fun tag(tag: String) = this.also { it.tag = tag }
	
	@PumlStateDiagramDsl
	inline infix fun color(color: String) = this.also { it.color = color }
}

//@PumlStateDiagramDsl
//class PumlStateDiagramCompositedState : PumlStateDiagramState()
//
//@PumlStateDiagramDsl
//class PumlStateDiagramConcurrentState : PumlStateDiagramState()

/**
 * PlantUml状态图连接。
 *
 * 语法示例：
 * * `A -> B`
 * * `A -> B: text can\n wrap line.`
 * * `A --> B: arrow can be longer.`
 * * `A -[#333,dotted]right-> B: arrow with color, shape and direction.`
 */
@PumlStateDiagramDsl
class PumlStateDiagramLink(
	@Language("Creole")
	val sourceStateId: String,
	@Language("Creole")
	val targetStateId: String,
	@Language("Creole")
	val text: String = "" //NOTE can wrap by "\n"
) : PumlStateDiagramDslElement {
	var arrowColor: String? = null
	var arrowShape: PumlArrowShape? = null
	var arrowDirection: PumlArrowDirection? = null
	var arrowLength: Int = 1
	
	override fun toString(): String {
		val textSnippet = if(text.isEmpty()) "" else ": ${text.replaceWithEscapedWrap()}"
		val arrowParamsSnippet = arrayOf(arrowColor?.addPrefix("#"), arrowShape?.text).filterNotNull().let {
			if(it.isEmpty()) "" else it.joinToString(",", "[", "]")
		}
		val arrowDirectionSnippet = arrowDirection ?: ""
		val arrowLengthSnippet = "-" * (arrowLength - 1)
		val arrowSnippet = "-$arrowParamsSnippet$arrowDirectionSnippet$arrowLengthSnippet>"
		return "$sourceStateId $arrowSnippet $targetStateId$textSnippet"
	}
	
	
	@PumlStateDiagramDsl
	inline infix fun arrowColor(arrowColor: String) = this.also { it.arrowColor = arrowColor }
	
	@PumlStateDiagramDsl
	inline infix fun arrowShape(arrowShape: PumlArrowShape) = this.also { it.arrowShape = arrowShape }
	
	@PumlStateDiagramDsl
	inline infix fun arrowDirection(arrowDirection: PumlArrowDirection) = this.also { it.arrowDirection = arrowDirection }
	
	@PumlStateDiagramDsl
	inline infix fun arrowLength(arrowLength: Int) = this.also { it.arrowLength = arrowLength.coerceIn(1, 8) }
}

