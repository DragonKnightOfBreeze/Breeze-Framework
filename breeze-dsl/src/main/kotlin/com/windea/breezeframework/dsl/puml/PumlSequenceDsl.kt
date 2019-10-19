@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.puml

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.puml.PumlConfig.quote
import org.intellij.lang.annotations.*

//REGION Dsl annotations

@DslMarker
private annotation class PumlSequenceDsl

//REGION Dsl & Dsl config & Dsl elements

/**PlantUml序列图。*/
@ReferenceApi("[PlantUml Sequence Diagram](http://plantuml.com/zh/sequence-diagram)")
@PumlSequenceDsl
class PumlSequence @PublishedApi internal constructor() : Puml(), PumlSequenceDslEntry {
	override fun toString(): String {
		TODO("not implemented")
	}
}


interface PumlSequenceDslEntry


/**PlantUml序列图Dsl的元素。*/
@PumlSequenceDsl
interface PumlSequenceDslElement : PumlDslElement


/**PlantUml序列图参与者。*/
@PumlSequenceDsl
class PumlSequenceParticipant @PublishedApi internal constructor(
	val name: String
) : PumlSequenceDslElement {
	var alias: String? = null
	var order: Int? = null
	var color: String? = null
	var shape: PumlSequenceParticipantShape = PumlSequenceParticipantShape.Actor
	
	override fun equals(other: Any?): Boolean {
		return this === other || (other is PumlSequenceParticipant && (other.alias == alias || other.name == name))
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
@PumlSequenceDsl
class PumlSequenceMessage @PublishedApi internal constructor(
	val fromActorId: String,
	val toActorId: String,
	@Language("Creole")
	val text: String = "",  //NOTE can wrap by "\n"
	val isBidirectional: Boolean = false
) : PumlSequenceDslElement {
	var arrowColor: String? = null
	var arrowShape: PumlSequenceMessageArrowShape = PumlSequenceMessageArrowShape.Arrow
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

//REGION Enumerations and constants

/**PlantUml序列图参与者的形状。*/
@PumlSequenceDsl
enum class PumlSequenceParticipantShape(val text: String) {
	Actor("actor"), Boundary("boundary"), Control("control"),
	Entity("entity"), Database("database"), Collections("collections")
}

/**PlantUml序列图消息箭头的形状。*/
@PumlSequenceDsl
enum class PumlSequenceMessageArrowShape(val prefix: String, val suffix: String) {
	Arrow("<", ">"), UpArrow("/", "\\"), DownArrow("\\", "/"),
	ThinArrow("<<", ">>"), ThinUpArrow("//", "\\\\"), ThinDownArrow("\\\\", "//"),
	DottedArrow("<-", "->"), DottedUpArrow("/-", "-\\"), DottedDownArrow("\\-", "-/"),
	DottedThinArrow("<<-", "->>"), DottedThinUpArrow("//-", "-\\\\"), DottedThinDownArrow("\\\\-", "-//")
}

//REGION Build extensions

@PumlSequenceDsl
inline fun pumlSequence(builder: PumlSequence.() -> Unit) =
	PumlSequence().also { it.builder() }


@PumlSequenceDsl
inline infix fun PumlSequenceParticipant.alias(alias: String) =
	this.also { it.alias = alias }

@PumlSequenceDsl
inline infix fun PumlSequenceParticipant.order(order: Int) =
	this.also { it.order = order }

@PumlSequenceDsl
inline infix fun PumlSequenceParticipant.color(color: String) =
	this.also { it.color = color }

@PumlSequenceDsl
inline infix fun PumlSequenceParticipant.shape(shape: PumlSequenceParticipantShape) =
	this.also { it.shape = shape }


@PumlSequenceDsl
inline infix fun PumlSequenceMessage.arrowColor(arrowColor: String) =
	this.also { it.arrowColor = arrowColor }

@PumlSequenceDsl
inline infix fun PumlSequenceMessage.arrowShape(arrowShape: PumlSequenceMessageArrowShape) =
	this.also { it.arrowShape = arrowShape }

@PumlSequenceDsl
inline infix fun PumlSequenceMessage.post(isPosted: Boolean) =
	this.also { it.isPosted = isPosted }
