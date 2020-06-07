package com.windea.breezeframework.dsl.xml

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.xml.Xml.*
import com.windea.breezeframework.dsl.xml.Xml.Companion.config

/**(No document.)*/
@XmlDsl
inline fun xml(block:Document.() -> Unit):Document = Document().apply(block)

/**(No document.)*/
@XmlDsl
inline fun xmlConfig(block:Config.() -> Unit) = config.block()


/**(No document.)*/
@XmlDsl
fun Document.statement(vararg attributes:Pair<String, Any?>):Statement =
	Statement(attributes.toMap()).also { declarations += it }

/**(No document.)*/
@XmlDsl
fun Document.comment(text:String):Comment =
	Comment(text).also { comments += it }

/**(No document.)*/
@XmlDsl
inline fun Document.element(name:String, block:Element.() -> Unit = {}):Element =
	Element(name).apply(block).also { rootElement = it }

/**(No document.)*/
@XmlDsl
inline fun Document.element(name:String, vararg attributes:Pair<String, Any?>, block:Element.() -> Unit = {}):Element =
	Element(name, attributes.toMap()).apply(block).also { rootElement = it }

/**(No document.)*/
@XmlDsl
fun Element.text(text:String):Text =
	Text(text).also { nodes += it }

/**(No document.)*/
@XmlDsl
fun Element.cdata(text:String):CData =
	CData(text).also { nodes += it }

/**(No document.)*/
@XmlDsl
fun Element.comment(text:String):Comment =
	Comment(text).also { nodes += it }

/**(No document.)*/
@XmlDsl
inline fun Element.element(name:String, block:Element.() -> Unit = {}):Element =
	Element(name).apply(block).also { nodes += it }

/**(No document.)*/
@XmlDsl
inline fun Element.element(name:String, vararg attributes:Pair<String, Any?>, block:Element.() -> Unit = {}):Element =
	Element(name, attributes.toMap()).apply(block).also { nodes += it }
