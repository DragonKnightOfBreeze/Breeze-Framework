package com.windea.breezeframework.dsl.markup

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.dsl.*

//TODO

//REGION Dsl annotations

internal annotation class CreoleDsl

//REGION Dsl elements & Build functions

@CreoleDsl
interface CreoleDslElement : DslElement

@Reference("[Creole](http://plantuml.com/zh/creole)")
@CreoleDsl
class Creole : DslElement, Dsl {
	override fun toString(): String = TODO()
}


@CreoleDsl
interface CreoleRichText : CreoleDslElement

@CreoleDsl
class CreoleBoldText

@CreoleDsl
class CreoleItalicText

@CreoleDsl
class CreoleMonospacedText

@CreoleDsl
class CreoleStrokedText

@CreoleDsl
class CreoleUnderlinedText

@CreoleDsl
class CreoleWavedText


@CreoleDsl
class CreoleIcon : CreoleDslElement {
	override fun toString(): String = TODO("not implemented")
}


@CreoleDsl
interface CreoleList

@CreoleDsl
class CreoleUnorderedList

@CreoleDsl
class CreoleOrderedList


@CreoleDsl
class CreoleHorizontalLine


@CreoleDsl
interface CreoleHeader

@CreoleDsl
class CreoleHeader1

@CreoleDsl
class CreoleHeader2

@CreoleDsl
class CreoleHeader3

@CreoleDsl
class CreoleHeader4


@CreoleDsl
class CreoleTable


@CreoleDsl
class CreoleTree



//REGION Enumerations and constants

enum class CreoleHorizontalLineType

//REGION Config object
