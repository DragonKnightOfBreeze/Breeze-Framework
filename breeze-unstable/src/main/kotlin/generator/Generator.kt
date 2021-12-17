// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.generator

/**生成器。*/
interface Generator

internal typealias SchemaDefinitionMap = Map<String, SchemaMap>
internal typealias SchemaMap = Map<String, Map<String, Any>>
internal typealias SchemaRule = (Pair<String, Any?>) -> Map<String, Any?>
internal typealias SqlDataMap = Map<String, Map<String, List<Map<String, Any?>>>>
