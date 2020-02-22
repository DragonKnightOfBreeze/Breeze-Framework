package com.windea.breezeframework.serializer.impl.json

import java.io.*
import java.lang.reflect.*

internal object KotlinJsonSerializer : JsonSerializer {
	override fun <T> read(string: String, type: Class<T>): T {
		TODO("not implemented")
	}

	override fun <T> read(string: String, type: Type): T {
		TODO("not implemented")
	}

	override fun <T> read(file: File, type: Class<T>): T {
		TODO("not implemented")
	}

	override fun <T> read(file: File, type: Type): T {
		TODO("not implemented")
	}

	override fun <T> write(data: T): String {
		TODO("not implemented")
	}

	override fun <T> write(data: T, file: File) {
		TODO("not implemented")
	}
}
