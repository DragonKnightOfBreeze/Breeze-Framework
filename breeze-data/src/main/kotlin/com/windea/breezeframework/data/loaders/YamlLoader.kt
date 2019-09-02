package com.windea.breezeframework.data.loaders

import com.windea.breezeframework.data.loaders.impl.*

/**Yaml数据读取器的接口。*/
interface YamlLoader : DataLoader {
	/**从指定字符串[string]加载数据。包含所有文档。*/
	fun fromStringAll(string: String): List<Any>
	
	/**从指定路径[path]的文件加载数据。包含所有文档。*/
	fun fromFileAll(path: String): List<Any>
	
	/**存储数据[dataList]到字符串。包含所有文档。*/
	fun <T : Any> toStringAll(dataList: List<T>): String
	
	/**存储数据[dataList]到指定路径[path]的文件。包含所有文档。*/
	fun <T : Any> toFileAll(dataList: List<T>, path: String)
	
	
	companion object {
		/**得到一个单例实例。*/
		val instance = SnakeYamlYamlLoader()
	}
}
