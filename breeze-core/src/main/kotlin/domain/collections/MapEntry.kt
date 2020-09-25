/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.core.domain.collections

/**映射入口。*/
data class MapEntry<K, V>(
	override val key: K,
	override val value: V
) : Map.Entry<K, V>
