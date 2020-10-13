// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.core.domain

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*

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
	 * 精简指定的路径。
	 */
	fun trim(path:String):String

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
	 * 根据指定路径查询目标查询对象，返回查询结果。如果查询失败，则抛出异常。
	 *
	 * 注意：如果需要递归查询，请使用[deepQueryBy]。
	 */
	fun <T> query(path: String, target: Any): T

	/**
	 * 根据指定路径查询目标查询对象，返回查询结果。如果查询失败，则返回null。
	 *
	 * 注意：如果需要递归查询，请使用[deepQueryBy]。
	 */
	fun <T> queryOrNull(path: String, target: Any): T?

	/**
	 * 根据指定路径递归查询目标查询对象，返回查询结果列表。
	 */
	fun <T> deepQuery(path:String,target:Any):List<T>

	companion object{
		//不需要进行注册
	}

	abstract class AbstractPath(val delimiter: String, val prefix: String?=null) :PathType{
		override fun trim(path:String):String{
			return path.trim().removeSuffix(delimiter)
		}

		override fun matches(value: String, path: String): Boolean {
			return trim(value) == trim(path)
		}

		override fun split(path: String): List<String> {
			return trim(path).let{if(prefix!= null) it.removePrefix(prefix) else it}.split(delimiter)
		}

		override fun splitToSequence(path: String): Sequence<String> {
			return trim(path).let{if(prefix!= null) it.removePrefix(prefix) else it}.splitToSequence(delimiter)
		}

		override fun join(paths: List<String>): String {
			return paths.joinToString(delimiter).let{ if(prefix!= null) it.addPrefix(prefix) else it}
		}

		override fun join(paths: Array<String>): String {
			return paths.joinToString(delimiter).let{ if(prefix!= null) it.addPrefix(prefix) else it}
		}

		override fun join(paths: Sequence<String>): String {
			return paths.joinToString(delimiter).let{ if(prefix!= null) it.addPrefix(prefix) else it}
		}

		override fun <T> query(path: String, target: Any): T {
			val fqPath = if(prefix!= null) path.removePrefix(prefix) else path
			return Querier.StringQuerier.query(fqPath,target)
		}

		override fun <T> queryOrNull(path: String, target: Any): T? {
			val fqPath = if(prefix!= null) path.removePrefix(prefix) else path
			return Querier.StringQuerier.queryOrNull(fqPath,target)
		}

		override fun <T> deepQuery(path: String, target: Any):List<T> {
			val paths = split(path)
			var result = listOf( target)
			for(p in paths){
				result = result.flatMap { value ->
					try {
						val r = queryOrNull<Any?>(p,value)
						when{
							r == null -> listOf()
							r is List<*> -> r.filterNotNull()
							else -> listOf(r)
						}
					}catch(e:Exception){
						throw IllegalArgumentException("Invalid path '$path' for query.", e)
					}
				}
			}
			return result as List<T>
		}
	}

	//region Default Path Types
	/**
	 * 标准路径。
	 *
	 * 规则：
	 * * 以`/`作为分隔符。
	 */
	object StandardPath:AbstractPath("/","/")

	/**
	 * 扩展路径。
	 *
	 * 规则：
	 * * 以`/`作为分隔符。
	 * * `{name:regex}` - 匹配指定正则表达式的指定名字的查询对象，可以不指定正则表达式和名字。
	 * * `m-n` - 匹配指定索引范围的元素。
	 */
	object ExtendedPath:AbstractPath("/","/")

	/**
	 * Ant路径。
	 *
	 * 规则：
	 * * 以`/`作为分隔符。
	 * * `?` - 匹配任意单个字符。
	 * * `*` - 匹配除了分隔符之外的任意数量的任意字符。
	 * * `**` - 匹配任意数量的任意字符。
	 * * `{name:regex}` - 匹配指定正则表达式的指定名字的查询对象，可以不指定正则表达式和名字。
	 */
	object AntPath:AbstractPath("/","/")

	/**
	 * Json指针路径。
	 *
	 * 规则：
	 * * 以`/`作为分隔符。
	 * * `-` - 匹配数组最后一个元素的下一个元素（对于赋值操作）。
	 * * 如果路径为空，则返回目标查询对象本身。
	 */
	object JsonPointerPath:AbstractPath("/","/")

	/**
	 * Json Schema路径。
	 *
	 * 规则：
	 * * 以`/`作为分隔符。
	 * * 以`/#/`作为前缀。
	 */
	object JsonSchemaPath:AbstractPath("/#/","/")

	object ReferencePath:AbstractPath(".")
	//endregion
}
