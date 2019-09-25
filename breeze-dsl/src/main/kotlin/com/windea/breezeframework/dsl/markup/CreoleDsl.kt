package com.windea.breezeframework.dsl.markup

import com.windea.breezeframework.dsl.*

//TODO

//REGION Dsl annotations

internal annotation class CreoleDsl

//REGION Dsl elements & Build functions

@CreoleDsl
interface CreoleDslElement : DslElement

class Creole : DslElement, Dsl {
	override fun toString(): String = TODO()
}


interface CreoleRichText : CreoleDslElement, CanInlineContent

class CreoleBoldText

class CreoleItalicText

class CreoleMonospacedText

class CreoleStrokedText

class CreoleUnderlinedText

class CreoleWavedText


interface CreoleList

class CreoleUnorderedList

class CreoleOrderedList


class CreoleHorizontalLine


interface CreoleHeader

class CreoleHeader1

class CreoleHeader2

class CreoleHeader3

class CreoleHeader4


class CreoleTable


class CreoleTree


class CreoleIcon

//REGION Enumerations and constants

enum class CreoleHorizontalLineType

//REGION Config object
