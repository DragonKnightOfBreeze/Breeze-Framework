package com.windea.breezeframework.core.domain.collections.deprecated

data class MapEntry<K, V>(
	override val key: K,
	override val value: V
) : Map.Entry<K, V>

