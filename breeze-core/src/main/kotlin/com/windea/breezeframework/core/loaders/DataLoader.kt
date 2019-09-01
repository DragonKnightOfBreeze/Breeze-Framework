package com.windea.breezeframework.core.loaders

import com.windea.breezeframework.core.annotations.marks.*

//TODO 允许读取指定泛型类型的数据，并且保证较少的api

/**数据读取器的接口。*/
@NotSure("考虑使用扩展库`kotlinx-serialization`，但是缺少具体的对于yaml、xml等格式的实现")
@NotSuitable("当类型中包含具体的泛型时")
interface DataLoader {
	/**从指定字符串[string]加载数据，返回指定类型[T]的对象。*/
	fun <T : Any> fromString(string: String, type: Class<T>): T
	
	/**从指定字符串[string]加载数据，返回映射。*/
	fun fromString(string: String): Map<String, Any?>
	
	/**从指定路径[path]的文件加载数据，返回指定类型[T]的对象。*/
	fun <T : Any> fromFile(path: String, type: Class<T>): T
	
	/**从指定路径[path]的文件加载数据，返回映射。*/
	fun fromFile(path: String): Map<String, Any?>
	
	/**存储指定类型[T]的数据[data]到字符串。*/
	fun <T : Any> toString(data: T): String
	
	/**存储指定类型[T]的数据[data]到指定路径[path]的文件。*/
	fun <T : Any> toFile(data: T, path: String)
}
