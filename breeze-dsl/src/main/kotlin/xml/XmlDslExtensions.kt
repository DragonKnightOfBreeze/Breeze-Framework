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
	return Statement(attributes.toMap()).also { statements += it }
}

/**
 * 创建一个[XmlDsl.Tag]并注册。
 */
@XmlDslMarker
fun DslEntry.text(text: String): Text {
	return Text(text).also { nodes += it }
}

/**
 * 创建一个[XmlDsl.CData]并注册。
 */
@XmlDslMarker
fun DslEntry.cdata(text: String): CData {
	return CData(text).also { nodes += it }
}

/**
 * 创建一个[XmlDsl.Comment]并注册。
 */
@XmlDslMarker
fun DslEntry.comment(text: String): Comment {
	return Comment(text).also { nodes += it }
}

/**
 * 创建一个[XmlDsl.Tag]并注册。
 */
@XmlDslMarker
inline fun DslEntry.element(name: String, block: Tag.() -> Unit = {}): Tag {
	return Tag(name).apply(block).also { nodes += it }
}

/**
 * 创建一个[XmlDsl.Tag]并注册。
 */
@XmlDslMarker
inline fun DslEntry.element(name: String, vararg attributes: Pair<String, Any?>, block: Tag.() -> Unit = {}): Tag {
	return Tag(name, attributes.toMap()).apply(block).also { nodes += it }
}
