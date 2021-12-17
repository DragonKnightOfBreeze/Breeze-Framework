// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("MermaidClassDiagramDslExtensions")

package icu.windea.breezeframework.dsl.mermaid.classdiagram

import icu.windea.breezeframework.dsl.mermaid.classdiagram.MermaidClassDiagramDsl.*
import icu.windea.breezeframework.dsl.mermaid.classdiagram.MermaidClassDiagramDsl.Annotation

/**
 * 开始构建[MermaidClassDiagramDsl]。
 */
@MermaidClassDiagramDslMarker
inline fun mermaidClassDiagramDsl(block: DslDocument.() -> Unit): DslDocument {
	return DslDocument().apply(block)
}

@MermaidClassDiagramDslMarker
inline fun DslEntry.`class`(name: String, block: Class.() -> Unit = {}): Class {
	return Class(name).apply(block).also { classes += it }
}

@MermaidClassDiagramDslMarker
fun DslEntry.relation(fromClassId: String, toClassId: String, type: RelationType): Relation {
	return Relation(fromClassId, toClassId, type).also { relations += it }
}

@MermaidClassDiagramDslMarker
fun DslEntry.relation(fromClassId: String, fromCardinality: String?, type: RelationType, toCardinality: String?, toClassId: String): Relation {
	return Relation(fromClassId, toClassId, type).apply {
		this.fromCardinality = fromCardinality
		this.toCardinality = toCardinality
	}.also { relations += it }
}

@MermaidClassDiagramDslMarker
fun Class.annotation(name: String): MermaidClassDiagramDsl.Annotation {
	return Annotation(name).also { annotation = it }
}

@MermaidClassDiagramDslMarker
fun Class.annotation(type: AnnotationType): MermaidClassDiagramDsl.Annotation {
	return Annotation(type.text).also { annotation = it }
}

@MermaidClassDiagramDslMarker
fun Class.statement(expression: String): Statement {
	return Statement(expression).also { statements += it }
}

@MermaidClassDiagramDslMarker
fun Class.`public`(statement: Statement): Statement {
	return statement.apply { visibility = Visibility.Public }
}

@MermaidClassDiagramDslMarker
fun Class.`private`(statement: Statement): Statement {
	return statement.apply { visibility = Visibility.Private }
}

@MermaidClassDiagramDslMarker
fun Class.`protected`(statement: Statement): Statement {
	return statement.apply { visibility = Visibility.Protected }
}

@MermaidClassDiagramDslMarker
fun Class.`package`(statement: Statement): Statement {
	return statement.apply { visibility = Visibility.Package }
}

@MermaidClassDiagramDslMarker
infix fun Relation.text(text: String): Relation {
	return apply { this.text = text }
}

@MermaidClassDiagramDslMarker
infix fun Relation.cardinality(cardinalityPair: Pair<String, String>): Relation {
	return apply { fromCardinality = cardinalityPair.first; toCardinality = cardinalityPair.second }
}

