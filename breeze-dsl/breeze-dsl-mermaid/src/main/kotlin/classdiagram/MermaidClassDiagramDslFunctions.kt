// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("unused")

package com.windea.breezeframework.dsl.mermaid.classdiagram

import com.windea.breezeframework.dsl.mermaid.classdiagram.MermaidClassDiagramDslDefinitions.*
import com.windea.breezeframework.dsl.mermaid.classdiagram.MermaidClassDiagramDslDefinitions.Annotation

@MermaidClassDiagramDslMarker
inline fun mermaidClassDiagramDsl(block: MermaidClassDiagramDsl.() -> Unit) = MermaidClassDiagramDsl().apply(block)


@MermaidClassDiagramDslMarker
inline fun IDslEntry.`class`(name: String, block: Class.() -> Unit = {}) = Class(name).apply(block).also { classes += it }

@MermaidClassDiagramDslMarker
fun IDslEntry.relation(
	fromClassId: String,
	toClassId: String,
	type: RelationType,
) = Relation(fromClassId, toClassId, type).also { relations += it }

@MermaidClassDiagramDslMarker
fun IDslEntry.relation(
	fromClassId: String,
	fromCardinality: String?,
	type: RelationType,
	toCardinality: String?,
	toClassId: String,
) = Relation(fromClassId, toClassId, type)
	.apply { this.fromCardinality = fromCardinality; this.toCardinality = toCardinality }.also { relations += it }

@MermaidClassDiagramDslMarker
fun Class.annotation(name: String) = Annotation(name).also { annotation = it }

@MermaidClassDiagramDslMarker
fun Class.annotation(type: AnnotationType) = Annotation(type.text).also { annotation = it }

@MermaidClassDiagramDslMarker
fun Class.statement(expression: String) = Statement(expression).also { statements += it }

@MermaidClassDiagramDslMarker
fun Class.`public`(statement: Statement) = statement.apply { visibility = Visibility.Public }

@MermaidClassDiagramDslMarker
fun Class.`private`(statement: Statement) = statement.apply { visibility = Visibility.Private }

@MermaidClassDiagramDslMarker
fun Class.`protected`(statement: Statement) = statement.apply { visibility = Visibility.Protected }

@MermaidClassDiagramDslMarker
fun Class.`package`(statement: Statement) = statement.apply { visibility = Visibility.Package }

@MermaidClassDiagramDslMarker
infix fun Relation.text(text: String) = apply { this.text = text }

@MermaidClassDiagramDslMarker
infix fun Relation.cardinality(cardinalityPair: Pair<String, String>) =
	apply { fromCardinality = cardinalityPair.first; toCardinality = cardinalityPair.second }

