package com.windea.breezeframework.data.generators

//REGION Top interfaces

/**生成器。*/
interface Generator

/**生成器的配置。*/
interface GeneratorConfig

//REGION Type aliases

typealias SchemaDefinitionMap = Map<String, SchemaMap>
typealias SchemaMap = Map<String, Map<String, Any>>
typealias SchemaRule = (Pair<String, Any?>) -> Map<String, Any?>
typealias SqlDataMap = Map<String, Map<String, List<Map<String, Any?>>>>
