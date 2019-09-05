package com.windea.breezeframework.data.serializers

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.data.enums.*
import java.io.*

//TODO Remain to add comments
//TODO 允许读取指定泛型类型的数据
//TODO 考虑编写自己的简洁而灵活的实现
//TODO 考虑使用扩展库`kotlinx-serialization`，但是缺少具体的对于yaml、xml等格式的实现

/**通用数据持久化器。其实现依赖于第三方库，如Gson。需要将必要的实现库添加到classpath中。*/
@NotTested
interface DataSerializer<S, C> : DataLoader, DataDumper {
	/**配置持久化选项。这个方法必须首先被调用。*/
	fun configure(handler: (C) -> Unit): S
}

/**通用数据读取器。其实现依赖于第三方库，如Gson。需要将必要的实现库添加到classpath中。*/
interface DataLoader {
	/**可处理的数据类型。*/
	val dataType: DataType
	
	/**从指定字符串读取指定类型的数据。*/
	fun <T : Any> load(string: String, type: Class<T>): T
	
	/**从指定字符串读取列表类型的数据。*/
	fun loadAsList(string: String): List<Any?> = load(string, List::class.java)
	
	/**从指定字符串读取映射类型的数据。*/
	fun loadAsMap(string: String): Map<String, Any?> = load(string, Map::class.java).toStringKeyMap()
	
	/**从指定文件读取指定类型的数据。*/
	fun <T : Any> load(file: File, type: Class<T>): T
	
	/**从指定文件读取列表类型的数据。*/
	fun loadAsList(file: File): List<Any?> = load(file, List::class.java)
	
	/**从指定文件读取映射类型的数据。*/
	fun loadAsMap(file: File): Map<String, Any?> = load(file, Map::class.java).toStringKeyMap()
	
	/**从指定读取器读取指定类型的数据。*/
	fun <T : Any> load(reader: Reader, type: Class<T>): T
	
	/**从指定读取器读取列表类型的数据。*/
	fun loadAsList(reader: Reader): List<Any?> = load(reader, List::class.java)
	
	/**从指定读取器读取映射类型的数据。*/
	fun loadAsMap(reader: Reader): Map<String, Any?> = load(reader, Map::class.java).toStringKeyMap()
}

/**通用数据转储器。其实现依赖于第三方库，如Gson。需要将必要的实现库添加到classpath中。*/
interface DataDumper {
	/**可处理的数据类型。*/
	val dataType: DataType
	
	/**转储数据到字符串。*/
	fun <T : Any> dump(data: T): String
	
	/**转储数据到文件。*/
	fun <T : Any> dump(data: T, file: File)
	
	/**转储数据到写入器。*/
	fun <T : Any> dump(data: T, writer: Writer)
}