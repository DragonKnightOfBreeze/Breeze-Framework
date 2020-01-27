package com.windea.breezeframework.core.extensions

import java.util.*

inline fun <C : List<T>, T> C.initialize(block: (C) -> Unit): C {
	block(this)
	return this
}

fun main() {
	val a = Collections.unmodifiableMap(System.getProperties())
	println(a)

	val b = System.getenv()
	println(b)
}
