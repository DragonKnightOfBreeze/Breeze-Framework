@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.puml

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.puml.PumlConfig.quote
import org.intellij.lang.annotations.*

//TODO fully support

//REGION top annotations and interfaces

@DslMarker
private annotation class PumlSequenceDiagramDsl

/**PlantUml序列图。*/
@ReferenceApi("[PlantUml Sequence Diagram](http://plantuml.com/zh/sequence-diagram)")
@PumlSequenceDiagramDsl
class PumlSequenceDiagram @PublishedApi internal constructor() : Puml(), PumlSequenceDiagramDslEntry {
	override fun toString(): String {
		TODO("not implemented")
	}
}

//REGION dsl interfaces

/**PlantUml序列图Dsl的入口。*/
@PumlSequenceDiagramDsl
interface PumlSequenceDiagramDslEntry : PumlDslEntry

/**PlantUml序列图Dsl的元素。*/
@PumlSequenceDiagramDsl
interface PumlSequenceDiagramDslElement : PumlDslElement

//REGION dsl elements

/**PlantUml序列图参与者。*/
@PumlSequenceDiagramDsl
class PumlSequenceDiagramParticipant @PublishedApi internal constructor(
	val name: String
) : PumlSequenceDiagramDslElement {
	var alias: String? = null
	var order: Int? = null
	var color: String? = null
	var shape: PumlSequenceDiagramParticipantShape = PumlSequenceDiagramParticipantShape.Actor
	
	override fun equals(other: Any?): Boolean {
		return this === other || (other is PumlSequenceDiagramParticipant && (other.alias == alias || other.name == name))
	}
	
	override fun hashCode(): Int {
		return alias?.hashCode() ?: name.hashCode()
	}
	
	override fun toString(): String {
		val orderSnippet = order?.let { " order $order" }.orEmpty()
		val colorSnippet = color?.let { " ${it.addPrefix("#")}" }.orEmpty()
		return if(alias == null) {
			"$shape $name$orderSnippet$colorSnippet"
		} else {
			val nameSnippet = name.replaceWithEscapedWrap().wrapQuote(quote) //only escaped with an existing alias
			"$shape $nameSnippet as $alias$orderSnippet$colorSnippet"
		}
	}
}

/**PlantUml序列图消息。*/
@PumlSequenceDiagramDsl
class PumlSequenceDiagramMessage @PublishedApi internal constructor(
	val fromActorId: String,
	val toActorId: String,
	@Language("Creole")
	val text: String = "",  //NOTE can wrap by "\n"
	val isBidirectional: Boolean = false
) : PumlSequenceDiagramDslElement {
	var arrowColor: String? = null
	var arrowShape: PumlSequenceDiagramMessageArrowShape = PumlSequenceDiagramMessageArrowShape.Arrow
	var isPosted: Boolean? = null //TODO add support for bidirectional lost/post
	
	override fun toString(): String {
		val textSnippet = if(text.isEmpty()) "" else ": ${text.replaceWithEscapedWrap()}"
		val arrowColorSnippet = arrowColor?.let { "[${it.addPrefix("#")}]" }.orEmpty()
		val statusSnippet = isPosted?.let { if(it) "o" else "x" }.orEmpty()
		val arrowSnippet = when {
			isBidirectional -> "${arrowShape.prefix}-$arrowColorSnippet${arrowShape.suffix}$statusSnippet"
			else -> "-$arrowColorSnippet${arrowShape.suffix}$statusSnippet"
		}
		return "$fromActorId $arrowSnippet $toActorId$textSnippet"
	}
	
	
}

//REGION enumerations and constants

/**PlantUml序列图参与者的形状。*/
@PumlSequenceDiagramDsl
enum class PumlSequenceDiagramParticipantShape(val text: String) {
	Actor("actor"), Boundary("boundary"), Control("control"),
	Entity("entity"), Database("database"), Collections("collections")
}

/**PlantUml序列图消息箭头的形状。*/
@PumlSequenceDiagramDsl
enum class PumlSequenceDiagramMessageArrowShape(val prefix: String, val suffix: String) {
	Arrow("<", ">"), UpArrow("/", "\\"), DownArrow("\\", "/"),
	ThinArrow("<<", ">>"), ThinUpArrow("//", "\\\\"), ThinDownArrow("\\\\", "//"),
	DottedArrow("<-", "->"), DottedUpArrow("/-", "-\\"), DottedDownArrow("\\-", "-/"),
	DottedThinArrow("<<-", "->>"), DottedThinUpArrow("//-", "-\\\\"), DottedThinDownArrow("\\\\-", "-//")
}

//REGION build extensions

@PumlSequenceDiagramDsl
inline fun pumlSequenceDiagram(builder: PumlSequenceDiagram.() -> Unit) = PumlSequenceDiagram().also { it.builder() }

@PumlSequenceDiagramDsl
inline infix fun PumlSequenceDiagramParticipant.alias(alias: String) =
	this.also { it.alias = alias }

@PumlSequenceDiagramDsl
inline infix fun PumlSequenceDiagramParticipant.order(order: Int) =
	this.also { it.order = order }

@PumlSequenceDiagramDsl
inline infix fun PumlSequenceDiagramParticipant.color(color: String) =
	this.also { it.color = color }

@PumlSequenceDiagramDsl
inline infix fun PumlSequenceDiagramParticipant.shape(shape: PumlSequenceDiagramParticipantShape) =
	this.also { it.shape = shape }

@PumlSequenceDiagramDsl
inline infix fun PumlSequenceDiagramMessage.arrowColor(arrowColor: String) =
	this.also { it.arrowColor = arrowColor }

@PumlSequenceDiagramDsl
inline infix fun PumlSequenceDiagramMessage.arrowShape(arrowShape: PumlSequenceDiagramMessageArrowShape) =
	this.also { it.arrowShape = arrowShape }

@PumlSequenceDiagramDsl
inline infix fun PumlSequenceDiagramMessage.post(isPosted: Boolean) =
	this.also { it.isPosted = isPosted }