package com.windea.breezeframework.data.serializers

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.data.enums.*
import java.io.*

//TODO Remain to add comments
//TODO 允许读取指定泛型类型的数据

/**通用数据持久化器的接口。实现依赖于第三方库，如Gson。需要将必要的实现库添加到classpath中。*/
@NotSure("考虑使用扩展库`kotlinx-serialization`，但是缺少具体的对于yaml、xml等格式的实现")
interface DataSerializer<S, C> : DataLoader, DataDumper {
	/**配置持久化选项。这个方法必须首先被调用。*/
	fun configure(handler: (C) -> Unit): S
}

interface DataLoader {
	val dataType: DataType
	
	fun <T : Any> load(string: String, type: Class<T>): T
	
	fun loadAsList(string: String): List<Any?> = load(string, List::class.java)
	
	fun loadAsMap(string: String): Map<String, Any?> = load(string, Map::class.java).toStringKeyMap()
	
	fun <T : Any> load(file: File, type: Class<T>): T
	
	fun loadAsList(file: File): List<Any?> = load(file, List::class.java)
	
	fun loadAsMap(file: File): Map<String, Any?> = load(file, Map::class.java).toStringKeyMap()
	
	fun <T : Any> load(reader: Reader, type: Class<T>): T
	
	fun loadAsList(reader: Reader): List<Any?> = load(reader, List::class.java)
	
	fun loadAsMap(reader: Reader): Map<String, Any?> = load(reader, Map::class.java).toStringKeyMap()
}

interface DataDumper {
	val dataType: DataType
	
	fun <T : Any> dump(data: T): String
	
	fun <T : Any> dump(data: T, file: File)
	
	fun <T : Any> dump(data: T, writer: Writer)
}
