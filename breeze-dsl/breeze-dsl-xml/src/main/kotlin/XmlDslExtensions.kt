// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("XmlDslExtensions")

package icu.windea.breezeframework.dsl.xml

import icu.windea.breezeframework.dsl.xml.XmlDsl.*

/**
 * 开始构建[XmlDsl]。
 */
@XmlDslMarker
inline fun xmlDsl(block: DslDocument.() -> Unit): DslDocument {
	return DslDocument().apply(block)
}

/**
 * 配置[XmlDsl]。
 */
@XmlDslMarker
inline fun xmlDslConfig(block: DslConfig.() -> Unit) {
	DslConfig.block()
}

/**
 * 创建一个[XmlDsl.Statement]并注册。
 */
@XmlDslMarker
fun DslDocument.statement(vararg attributes: Pair<String, Any?>): Statement {
	return Statement(attributes.toMap()).also { declarations += it }
}

/**
 * 创建一个[XmlDsl.Comment]并注册。
 */
@XmlDslMarker
fun DslDocument.comment(text: String): Comment {
	return Comment(text).also { comments += it }
}

/**
 * 创建一个[XmlDsl.Element]并注册。
 */
@XmlDslMarker
inline fun DslDocument.element(name: String, block: Element.() -> Unit = {}): Element {
	return Element(name).apply(block).also { rootElement = it }
}

/**
 * 创建一个[XmlDsl.Element]并注册。
 */
@XmlDslMarker
inline fun DslDocument.element(name: String, vararg attributes: Pair<String, Any?>, block: Element.() -> Unit = {}): Element {
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
