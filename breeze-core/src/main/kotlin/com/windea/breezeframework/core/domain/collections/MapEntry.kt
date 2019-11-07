package com.windea.breezeframework.core.domain.collections

data class MapEntry<K, V>(
	override val key: K,
	override val value: V
) : Map.Entry<K, V>

