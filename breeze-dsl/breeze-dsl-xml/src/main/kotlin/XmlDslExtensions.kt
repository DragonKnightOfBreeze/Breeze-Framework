// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.xml

import com.windea.breezeframework.dsl.xml.XmlDsl.*

/**
 * 开始构建[XmlDsl]。
 */
@XmlDslMarker
inline fun xmlDsl(block: Document.() -> Unit): Document {
	return Document().apply(block)
}

/**
 * 配置[XmlDsl]。
 */
@XmlDslMarker
inline fun xmlDslConfig(block: Config.() -> Unit) {
	Config.block()
}


/**
 * 创建一个[XmlDsl.Statement]并注册。
 */
@XmlDslMarker
fun Document.statement(vararg attributes: Pair<String, Any?>): Statement {
	return Statement(attributes.toMap()).also { declarations += it }
}

/**
 * 创建一个[XmlDsl.Comment]并注册。
 */
@XmlDslMarker
fun Document.comment(text: String): Comment {
	return Comment(text).also { comments += it }
}

/**
 * 创建一个[XmlDsl.Element]并注册。
 */
@XmlDslMarker
inline fun Document.element(name: String, block: Element.() -> Unit = {}): Element {
	return Element(name).apply(block).also { rootElement = it }
}

/**
 * 创建一个[XmlDsl.Element]并注册。
 */
@XmlDslMarker
inline fun Document.element(name: String, vararg attributes: Pair<String, Any?>, block: Element.() -> Unit = {}): Element {
	return Element(name, attributes.toMap()).apply(block).also { rootElement = it }
}

/**
 * 创建一个[XmlDsl.Element]并注册。
 */
@XmlDslMarker
fun Element.text(text: String): Text {
	return Text(text).also { nodes += it }
}

/**
 * 创建一个[XmlDsl.CData]并注册。
 */
@XmlDslMarker
fun Element.cdata(text: String): CData {
	return CData(text).also { nodes += it }
}

/**
 * 创建一个[XmlDsl.Comment]并注册。
 */
@XmlDslMarker
fun Element.comment(text: String): Comment {
	return Comment(text).also { nodes += it }
}

/**
 * 创建一个[XmlDsl.Element]并注册。
 */
@XmlDslMarker
inline fun Element.element(name: String, block: Element.() -> Unit = {}): Element {
	return Element(name).apply(block).also { nodes += it }
}

/**
 * 创建一个[XmlDsl.Element]并注册。
 */
@XmlDslMarker
inline fun Element.element(name: String, vararg attributes: Pair<String, Any?>, block: Element.() -> Unit = {}): Element {
	return Element(name, attributes.toMap()).apply(block).also { nodes += it }
}
