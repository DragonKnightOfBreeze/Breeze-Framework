package com.windea.breezeframework.core.extensions

import java.util.*

inline fun <C : List<T>, T> C.initialize(block: (C) -> Unit): C {
	block(this)
	return this
}

fun main() {
	repeat(100000) {
		LinkedList<Int>().initialize {
			it.add(1)
			it.add(2)
			it.add(3)
		}
	}
}
