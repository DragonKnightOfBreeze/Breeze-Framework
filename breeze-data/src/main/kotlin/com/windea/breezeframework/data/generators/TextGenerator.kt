package com.windea.breezeframework.data.generators

/**文本生成器。*/
interface TextGenerator

/**文本生成器的配置。*/
interface TextGeneratorConfig


typealias SchemaDefinitionMap = Map<String, SchemaMap>
typealias SchemaMap = Map<String, Map<String, Any>>
typealias SchemaRule = (Pair<String, Any?>) -> Map<String, Any?>

typealias SqlDataMap = Map<String, Map<String, List<Map<String, Any?>>>>
