package com.windea.breezeframework.data.serializers

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.data.enums.*
import java.io.*

//TODO
//TODO 允许读取指定泛型类型的数据

@NotSure("考虑使用扩展库`kotlinx-serialization`，但是缺少具体的对于yaml、xml等格式的实现")
interface DataSerializer : DataLoader, DataDumper

interface DataLoader {
	val dataType: DataType
	
	fun <T : Any> load(string: String, type: Class<T>): T
	
	fun <T : Any> load(file: File, type: Class<T>): T
	
	fun <T : Any> load(reader: Reader, type: Class<T>): T
	
	fun loadList(string: String): List<Any?>
	
	fun loadList(file: File): List<Any?>
	
	fun loadList(reader: Reader): List<Any?>
	
	fun loadMap(file: File): Map<String, Any?>
	
	fun loadMap(string: String): Map<String, Any?>
	
	fun loadMap(reader: Reader): Map<String, Any?>
}

interface DataDumper {
	val dataType: DataType
	
	fun <T : Any> dump(data: T): String
	
	fun <T : Any> dump(data: T, file: File)
	
	fun <T : Any> dump(data: T, writer: Writer)
}
