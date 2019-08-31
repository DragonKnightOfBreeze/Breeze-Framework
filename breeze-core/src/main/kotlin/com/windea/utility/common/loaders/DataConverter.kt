package com.windea.utility.common.loaders

import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.enums.*
import com.windea.utility.common.extensions.*

/**数据转化器。*/
@NotSure("考虑使用扩展库`kotlinx-serialization`，但是缺少具体的对于yaml、xml等格式的实现")
class DataConverter constructor(
	private val fromType: DataType,
	private val toType: DataType
) {
	/**将一种数据文件转换成另一种数据文件，分别指定输入、输出路径。*/
	fun convert(fromPath: String, toPath: String) {
		val dataMap = fromType.loader.fromFile(fromPath)
		toType.loader.toFile(dataMap, toPath)
	}
	
	/**将一种数据文件转换成另一种数据文件，输入与输出路径仅扩展名不同。*/
	fun convert(fromPath: String) {
		val toPath = fromPath.toPathInfo().changeFileExtension(toType.extension)
		val dataMap = fromType.loader.fromFile(fromPath)
		toType.loader.toFile(dataMap, toPath)
	}
}
