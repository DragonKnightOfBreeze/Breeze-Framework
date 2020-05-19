@file:Suppress("unused")

package com.windea.breezeframework.dsl.mermaid.classdiagram

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.classdiagram.MermaidClassDiagram.*
import com.windea.breezeframework.dsl.mermaid.classdiagram.MermaidClassDiagram.Annotation


/*** (No document.)*/
@TopDslFunction
@MermaidClassDiagramDsl
inline fun mermaidClassDiagram(block:Document.() -> Unit) = Document().apply(block)


/*** (No document.)*/
@DslFunction
@MermaidClassDiagramDsl
inline fun IDslEntry.`class`(name:String, block:Class.() -> Unit = {}) =
	Class(name).apply(block).also { classes += it }

/*** (No document.)*/
@DslFunction
@MermaidClassDiagramDsl
fun IDslEntry.relation(fromClassId:String, toClassId:String, type:RelationType) =
	Relation(fromClassId, toClassId, type).also { relations += it }

/*** (No document.)*/
@DslFunction
@MermaidClassDiagramDsl
fun IDslEntry.relation(
	fromClassId:String,
	fromCardinality:String?,
	type:RelationType,
	toCardinality:String?,
	toClassId:String
) = Relation(fromClassId, toClassId, type)
	.apply { this.fromCardinality = fromCardinality }
	.apply { this.toCardinality = toCardinality }
	.also { relations += it }

/*** (No document.)*/
@DslFunction
@MermaidClassDiagramDsl
fun Class.annotation(name:String) =
	Annotation(name).also { annotation = it }

/*** (No document.)*/
@DslFunction
@MermaidClassDiagramDsl
fun Class.annotation(type:AnnotationType) =
	Annotation(type.text).also { annotation = it }

/*** (No document.)*/
@DslFunction
@MermaidClassDiagramDsl
fun Class.statement(expression:String) =
	Statement(expression).also { statements += it }

/*** (No document.)*/
@DslFunction
@MermaidClassDiagramDsl
fun Class.`public`(statement:Statement) =
	statement.apply { visibility = Visibility.Public }

/*** (No document.)*/
@DslFunction
@MermaidClassDiagramDsl
fun Class.`private`(statement:Statement) =
	statement.apply { visibility = Visibility.Private }

/*** (No document.)*/
@DslFunction
@MermaidClassDiagramDsl
fun Class.`protected`(statement:Statement) =
	statement.apply { visibility = Visibility.Protected }

/*** (No document.)*/
@DslFunction
@MermaidClassDiagramDsl
fun Class.`package`(statement:Statement) =
	statement.apply { visibility = Visibility.Package }

/*** (No document.)*/
@DslFunction
@MermaidClassDiagramDsl
infix fun Relation.text(text:String) = apply { this.text = text }

/*** (No document.)*/
@DslFunction
@MermaidClassDiagramDsl
infix fun Relation.cardinality(cardinalityPair:Pair<String, String>) =
	apply { fromCardinality = cardinalityPair.first }.apply { toCardinality = cardinalityPair.second }

