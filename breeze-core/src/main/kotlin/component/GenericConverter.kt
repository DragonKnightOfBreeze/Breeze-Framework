// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import java.lang.reflect.*

interface GenericConverter<T> : Converter<T> {
	fun convert(value: Any, targetType: Type): T

	fun convertOrNull(value: Any, targetType: Type): T? {
		return runCatching { convert(value, targetType) }.getOrNull()
	}

	override fun convert(value: Any): T {
		throw UnsupportedOperationException("Redirect to 'fun convert(value: Any, sourceType: Type, targetType: Type): T'.")
	}

	override fun convertOrNull(value: Any): T? {
		throw UnsupportedOperationException("Redirect to 'fun convertOrNull(value: Any, sourceType: Type, targetType: Type): T'.")
	}
}
