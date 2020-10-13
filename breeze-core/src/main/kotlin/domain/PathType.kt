// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.domain

/**
 * 路径类型。
 *
 * 路径用于表示一个文件/组件/类/属性/方法在其系统中的位置，可以可以包含多个子路径和变量，可以用于匹配和查询。
 */
interface PathType {
	/**
	 * 判断指定的字符串是否匹配指定的路径。
	 */
	fun matches(value:String,path:String):Boolean

	/**
	 * 将指定的字符串分隔成子路径列表。
	 */
	fun split(value:String):List<String>

	/**
	 * 将子路径列表组合成完整路径。
	 */
	fun join(value:List<String>):String

	/**
	 * 判断是否支持查询指定的对象。
	 */
	fun supportsQuery(target:Any?):Boolean{
		return false
	}

	/**
	 * 根据指定的字符串，将其作为路径查询指定的对象。（数组/列表/映射/序列/...）
	 */
	fun query(value:String,target:Any?):Map<String,Any?>{
		throw UnsupportedOperationException("Path type '${this.javaClass.simpleName}' does not support query.")
	}
}
