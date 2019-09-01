package com.windea.breezeframework.core.generators.text

/**文本生成器。*/
interface TextGenerator


typealias SchemaDefinitionMap = Map<String, SchemaMap>
typealias SchemaMap = Map<String, Map<String, Any>>
typealias SchemaRule = (Pair<String, Any?>) -> Map<String, Any?>

typealias SqlDataMap = Map<String, Map<String, List<Map<String, Any?>>>>
