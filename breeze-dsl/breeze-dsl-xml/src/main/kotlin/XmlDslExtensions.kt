// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.xml

import com.windea.breezeframework.dsl.xml.XmlDslDefinitions.*

/**Build a [XmlDsl].*/
@XmlDslMarker
inline fun xmlDsl(block: XmlDsl.() -> Unit) = XmlDsl().apply(block)

/**Configure a [XmlDsl] by [XmlDslConfig].*/
@XmlDslMarker
inline fun xmlDslConfig(block: XmlDslConfig.() -> Unit) = XmlDslConfig.block()


/**Create a [XmlDslDefinitions.Statement] and register it.*/
@XmlDslMarker
fun XmlDsl.statement(vararg attributes: Pair<String, Any?>) = Statement(attributes.toMap()).also { declarations += it }

/**Create a [XmlDslDefinitions.Comment] and register it.*/
@XmlDslMarker
fun XmlDsl.comment(text: String) = Comment(text).also { comments += it }

/**Create a [XmlDslDefinitions.Element] and register it.*/
@XmlDslMarker
inline fun XmlDsl.element(
	name: String,
	block: Element.() -> Unit = {},
) = Element(name).apply(block).also { rootElement = it }

/**Create a [XmlDslDefinitions.Element] and register it.*/
@XmlDslMarker
inline fun XmlDsl.element(
	name: String,
	vararg attributes: Pair<String, Any?>,
	block: Element.() -> Unit = {},
) = Element(name, attributes.toMap()).apply(block).also { rootElement = it }

/**Create a [XmlDslDefinitions.Text] and register it.*/
@XmlDslMarker
fun Element.text(text: String) = Text(text).also { nodes += it }

/**Create a [XmlDslDefinitions.CData] and register it.*/
@XmlDslMarker
fun Element.cdata(text: String) = CData(text).also { nodes += it }

/**Create a [XmlDslDefinitions.Comment] and register it.*/
@XmlDslMarker
fun Element.comment(text: String) = Comment(text).also { nodes += it }

/**Create a [XmlDslDefinitions.Element] and register it.*/
@XmlDslMarker
inline fun Element.element(
	name: String,
	block: Element.() -> Unit = {},
) = Element(name).apply(block).also { nodes += it }

/**Create a [XmlDslDefinitions.Element] and register it.*/
@XmlDslMarker
inline fun Element.element(
	name: String,
	vararg attributes: Pair<String, Any?>,
	block: Element.() -> Unit = {},
) = Element(name, attributes.toMap()).apply(block).also { nodes += it }
