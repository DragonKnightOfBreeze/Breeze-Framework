package com.windea.breezeframework.dsl.xml

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.xml.Xml.*

/**(No document.)*/
@TopDslFunction
@XmlDsl
inline fun xml(block:Document.() -> Unit) = Document().apply(block)


/**(No document.)*/
@DslFunction
@XmlDsl
fun Document.statement(vararg attributes:Pair<String, Any?>) =
	Statement(attributes.toMap()).also { declarations += it }

/**(No document.)*/
@DslFunction
@XmlDsl
fun Document.comment(text:String) =
	Comment(text).also { comments += it }

/**(No document.)*/
@DslFunction
@XmlDsl
inline fun Document.element(name:String, block:Element.() -> Unit = {}) =
	Element(name).apply(block).also { rootElement = it }

/**(No document.)*/
@DslFunction
@XmlDsl
inline fun Document.element(name:String, vararg attributes:Pair<String, Any?>, block:Element.() -> Unit = {}) =
	Element(name, attributes.toMap()).apply(block).also { rootElement = it }

/**(No document.)*/
@DslFunction
@XmlDsl
fun Element.text(text:String) =
	Text(text).also { nodes += it }

/**(No document.)*/
@DslFunction
@XmlDsl
fun Element.cdata(text:String) =
	CData(text).also { nodes += it }

/**(No document.)*/
@DslFunction
@XmlDsl
fun Element.comment(text:String) =
	Comment(text).also { nodes += it }

/**(No document.)*/
@DslFunction
@XmlDsl
inline fun Element.element(name:String, block:Element.() -> Unit = {}) =
	Element(name).apply(block).also { nodes += it }

/**(No document.)*/
@DslFunction
@XmlDsl
inline fun Element.element(name:String, vararg attributes:Pair<String, Any?>, block:Element.() -> Unit = {}) =
	Element(name, attributes.toMap()).apply(block).also { nodes += it }
