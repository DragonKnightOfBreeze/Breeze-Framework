package com.windea.breezeframework.generator

//REGION top interfaces

/**生成器。*/
interface Generator

//REGION type aliases

internal typealias SchemaDefinitionMap = Map<String, SchemaMap>
internal typealias SchemaMap = Map<String, Map<String, Any>>
internal typealias SchemaRule = (Pair<String, Any?>) -> Map<String, Any?>
internal typealias SqlDataMap = Map<String, Map<String, List<Map<String, Any?>>>>
