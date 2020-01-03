package com.windea.breezeframework.data.serializers.json

import com.alibaba.fastjson.*
import java.io.*
import java.lang.reflect.*

internal object FastJsonSerializer : JsonSerializer {
	override fun <T> load(string: String, type: Class<T>): T {
		return JSON.parseObject(string, type)
	}

	override fun <T> load(file: File, type: Class<T>): T {
		return file.readText().let { JSON.parseObject(it, type) }
	}

	override fun <T> load(string: String, type: Type): T {
		return JSON.parseObject(string, type)
	}

	override fun <T> load(file: File, type: Type): T {
		return file.readText().let { JSON.parseObject(it, type) }
	}

	override fun <T> dump(data: T): String {
		return JSON.toJSONString(data, true)
	}

	override fun <T> dump(data: T, file: File) {
		return JSON.writeJSONString(file.writer(), data)
	}
}
