package com.windea.breezeframework.dsl.xml

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.xml.Xml.*

@TopDslFunction
@XmlDsl
inline fun xml(block:Document.() -> Unit) = Document().apply(block)


@DslFunction
@XmlDsl
fun Document.statement(name:String, vararg attributes:Pair<String, Any?>) =
	Statement(name, attributes.toMap().toStringValueMap()).also { statements += it }

@DslFunction
@XmlDsl
fun Document.comment(text:String) =
	Comment(text).also { comments += it }

@DslFunction
@XmlDsl
inline fun Document.element(name:String, vararg attributes:Pair<String, Any?>, block:Element.() -> Unit = {}) =
	Element(name, attributes.toMap().toStringValueMap()).apply(block).also { rootElement = it }

@DslFunction
@XmlDsl
fun Element.text(text:String) =
	Text(text).also { nodes += it }

@DslFunction
@XmlDsl
fun Element.comment(text:String) =
	Comment(text).also { nodes += it }

@DslFunction
@XmlDsl
inline fun Element.element(name:String, vararg attributes:Pair<String, Any?>, block:Element.() -> Unit = {}) =
	Element(name, attributes.toMap().toStringValueMap()).apply(block).also { nodes += it }
