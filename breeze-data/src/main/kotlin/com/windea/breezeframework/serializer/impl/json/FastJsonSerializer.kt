package com.windea.breezeframework.serializer.impl.json

import com.alibaba.fastjson.*
import java.io.*
import java.lang.reflect.*

internal object FastJsonSerializer : JsonSerializer {
	override fun <T> read(string: String, type: Class<T>): T {
		return JSON.parseObject(string, type)
	}

	override fun <T> read(file: File, type: Class<T>): T {
		return file.readText().let { JSON.parseObject(it, type) }
	}

	override fun <T> read(string: String, type: Type): T {
		return JSON.parseObject(string, type)
	}

	override fun <T> read(file: File, type: Type): T {
		return file.readText().let { JSON.parseObject(it, type) }
	}

	override fun <T> write(data: T): String {
		return JSON.toJSONString(data, true)
	}

	override fun <T> write(data: T, file: File) {
		return JSON.writeJSONString(file.writer(), data)
	}
}
