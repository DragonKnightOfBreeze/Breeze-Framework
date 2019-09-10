package com.windea.breezeframework.data.serializers.json

import com.alibaba.fastjson.*
import java.io.*

object FastJsonSerializer : JsonSerializer {
	override fun <T : Any> load(string: String, type: Class<T>): T {
		return JSON.parseObject(string, type)
	}
	
	override fun <T : Any> load(file: File, type: Class<T>): T {
		return file.readText().let { JSON.parseObject(it, type) }
	}
	
	override fun <T : Any> load(reader: Reader, type: Class<T>): T {
		return reader.readText().let { JSON.parseObject(it, type) }
	}
	
	override fun <T : Any> dump(data: T): String {
		return JSON.toJSONString(data, true)
	}
	
	override fun <T : Any> dump(data: T, file: File) {
		return JSON.writeJSONString(file.writer(), data)
	}
	
	override fun <T : Any> dump(data: T, writer: Writer) {
		return JSON.writeJSONString(writer, data)
	}
}
