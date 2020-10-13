// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.domain

import com.windea.breezeframework.core.annotations.*

/**
 * 路径类型。
 *
 * 路径用于表示目标查询对象在其系统中的位置，可以可以包含多个子路径和变量，可以用于匹配和查询。
 *
 * 查询对象可能是：文件，组件，元素，数组，列表，映射，序列，...
 */
@ComponentMarker
interface PathType {
	/**
	 * 判断指定的字符串是否匹配指定的路径。
	 */
	fun matches(value:String,path:String):Boolean

	/**
	 * 将指定的字符串分隔成子路径列表。
	 */
	fun split(path:String):List<String>

	/**
	 * 将指定的字符串分隔成子路径序列。
	 */
	fun splitToSequence(path:String):Sequence<String>

	/**
	 * 将子路径数组组合成完整路径。
	 */
	fun join(paths:Array<String>):String

	/**
	 * 将子路径列表组合成完整路径。
	 */
	fun join(paths:List<String>):String

	/**
	 * 将子路径序列组合成完整路径。
	 */
	fun join(paths:Sequence<String>):String

	/**
	 * 根据指定类型的路径，查询目标查询对象。返回匹配的路径和查询结果的映射。
	 */
	fun query(path: String, target: Any): Map<String,Any?>

	companion object{
		//不需要进行注册
	}

	//region Default Path Types
	/**
	 * 标准路径。
	 *
	 * 规则：
	 * * `/` - 分隔符。
	 */
	object StandardPath:PathType{
		private const val delimiter = '/'
		private const val delimiterString = delimiter.toString()

		override fun matches(value: String, path: String): Boolean {
			return value.trimEnd(delimiter) == path.trimEnd(delimiter)
		}

		override fun split(path: String): List<String> {
			return path.trim(delimiter).split(delimiter)
		}

		override fun splitToSequence(path: String): Sequence<String> {
			return path.trim(delimiter).splitToSequence(delimiter)
		}

		override fun join(paths: List<String>): String {
			return paths.joinToString(delimiterString)
		}

		override fun join(paths: Array<String>): String {
			return paths.joinToString(delimiterString)
		}

		override fun join(paths: Sequence<String>): String {
			return paths.joinToString(delimiterString)
		}

		override fun query(path: String, target: Any): Map<String,Any?> {
			val paths = split(path)
			var pathValuePairs = listOf(arrayOf<String>() to target)
			for(p in paths){
				pathValuePairs = pathValuePairs.flatMap { (key,value)->
					listOf()
				}
			}
			return pathValuePairs.toMap().mapKeys { (k, _) -> join(k) }
		}
	}

	/**
	 * 扩展路径。
	 *
	 * 规则：
	 * * `/` - 分隔符。
	 * * `{name}` - 匹配指定名字的查询对象变量，可以不指定名字。
	 * * `{name:regex}` - 匹配指定正则表达式的指定名字的查询对象变量，可以不指定正则表达式和名字。
	 * * `m-n` - 匹配指定索引范围的元素。
	 */
	object ExtendedPath{

	}

	object AntPath{

	}

	object JsonPointerPath{

	}

	object ReferencePath{

	}
	//endregion
}
