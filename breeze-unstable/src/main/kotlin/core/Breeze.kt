// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

import com.windea.breezeframework.core.extension.*

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class Breeze<T : Any> @PublishedApi internal constructor(
	@PublishedApi internal val value: T,
) {
	fun equalsBr(other: Any?, deepOp: Boolean = true): Boolean {
		return when {
			value !is Array<*> || other !is Array<*> -> value == other
			deepOp -> value.contentDeepEquals(other)
			else -> value.contentEquals(other)
		}
	}

	fun hashCodeBr(deepOp: Boolean = true): Int {
		return when {
			value !is Array<*> -> value.hashCode()
			deepOp -> value.contentDeepHashCode()
			else -> value.contentHashCode()
		}
	}

	fun toStringBr(deepOp: Boolean = true): String {
		return when {
			value !is Array<*> -> value.toString()
			deepOp -> value.contentDeepToString()
			else -> value.contentToString()
		}
	}

	fun isEmptyBr(): Boolean {
		return when {
			//value == null -> true
			value is Array<*> -> value.isEmpty()
			value is Collection<*> -> value.isEmpty()
			value is Iterable<*> -> value.none()
			value is Map<*, *> -> value.isEmpty()
			value is Sequence<*> -> value.none()
			else -> unsupported()
		}
	}

	fun isNotEmptyBr(): Boolean {
		return when {
			//value == null -> true
			value is Array<*> -> value.isNotEmpty()
			value is Collection<*> -> value.isNotEmpty()
			value is Iterable<*> -> value.any()
			value is Map<*, *> -> value.isNotEmpty()
			value is Sequence<*> -> value.any()
			else -> unsupported()
		}
	}

	inline fun ifEmptyBr(transform: (T) -> T): T {
		return if(isEmptyBr()) transform(value) else value
	}

	inline fun ifNotEmptyBr(transform: (T) -> T): T {
		return if(isNotEmptyBr()) transform(value) else value
	}

	fun <R> getBr(path: String): R {
		return when {
			//value == null -> null
			value is Array<*> -> value[path.toInt()]
			value is List<*> -> value[path.toInt()]
			value is Map<*, *> -> value[path]
			else -> {
				val javaClass = value::class.java
				javaClass.getDeclaredField(path).apply { trySetAccessible() }.get(value)
			}
		} as R
	}

	fun <R> getOrNullBr(path: String): R? {
		return when {
			//value == null -> null
			value is Array<*> -> value.getOrNull(path.toInt())
			value is List<*> -> value.getOrNull(path.toInt())
			value is Map<*, *> -> value[path]
			else -> {
				val javaClass = value::class.java
				runCatching { javaClass.getDeclaredField(path).apply { trySetAccessible() }.get(value) }.getOrNull()
			}
		} as R
	}

	fun <R> getOrElseBr(path: String, defaultValue: () -> R): R {
		return when {
			//value == null -> null
			value is Array<*> -> value.getOrNull(path.toInt()) ?: defaultValue()
			value is List<*> -> value.getOrNull(path.toInt()) ?: defaultValue()
			value is Map<*, *> -> value.cast<Map<String, *>>().getOrElse(path, defaultValue)
			else -> {
				val javaClass = value::class.java
				runCatching { javaClass.getDeclaredField(path).apply { trySetAccessible() }.get(value) }.getOrElse { defaultValue() }
			}
		} as R
	}

	fun <R> setBr(path: String, value: R) {
		TODO()
	}

	fun <R> getOrSetBr(path: String, defaultValue: () -> R): R {
		TODO()
	}

	fun <R> addBr(path: String, vararg values: R) {
		TODO()
	}

	fun <R> removeBr(value: R): R {
		TODO()
	}

	fun <R> removeAtBr(path: String): R {
		TODO()
	}

	private fun unsupported(): Nothing {
		throw UnsupportedOperationException("Unsupported value type '${value::class.java.name}'.")
	}
}
