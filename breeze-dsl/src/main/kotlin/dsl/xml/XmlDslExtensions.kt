// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("XmlDslExtensions")

package icu.windea.breezeframework.dsl.xml

@XmlDslMarker
inline fun xmlDsl(block: XmlDslDocument.() -> Unit): XmlDslDocument {
	return XmlDslDocument().apply(block)
}

@XmlDslMarker
inline fun xmlDslConfig(block: XmlDslConfig.() -> Unit) {
	XmlDslConfig.block()
}

@XmlDslMarker
fun XmlDslDocument.statement(vararg attributes: Pair<String, Any?>): XmlStatement {
	return XmlStatement(attributes.toMap()).also { statements += it }
}

@XmlDslMarker
fun XmlDslEntry.text(text: String): XmlText {
	return XmlText(text).also { nodes += it }
}

@XmlDslMarker
fun XmlDslEntry.cdata(text: String): XmlCData {
	return XmlCData(text).also { nodes += it }
}

@XmlDslMarker
fun XmlDslEntry.comment(text: String): XmlComment {
	return XmlComment(text).also { nodes += it }
}

@XmlDslMarker
inline fun XmlDslEntry.element(name: String, block: XmlTag.() -> Unit = {}): XmlTag {
	return XmlTag(name).apply(block).also { nodes += it }
}

@XmlDslMarker
inline fun XmlDslEntry.element(name: String, vararg attributes: Pair<String, Any?>, block: XmlTag.() -> Unit = {}): XmlTag {
	return XmlTag(name, attributes.toMap()).apply(block).also { nodes += it }
}
