@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.generator.specific

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.data.enums.*
import com.windea.breezeframework.data.serializers.*
import com.windea.breezeframework.generator.*
import java.io.*
import java.util.concurrent.*

/**Json Schema生成器。*/
object SchemaGenerator : Generator {
	private val multiSchemaRuleNames = listOf("oneOf", "allOf", "anyOf")
	
	private val dataMap = mutableMapOf<String, Any?>()
	
	private val extendedRuleMap = mutableMapOf<String, SchemaRule>(
		"\$ref" to { (_, value) ->
			//将对yaml schema文件的引用改为对json schema文件的引用
			val newValue = (value as String).replace(".yml", ".json").replace(".yaml", ".json")
			mapOf("\$ref" to newValue)
		},
		"\$gen" to { (_, value) ->
			//提取$dataMap中的路径`$value`对应的值列表
			val newValue = dataMap.deepQuery(value as String)
			when {
				newValue.isNotEmpty() -> mapOf("enum" to newValue)
				else -> mapOf()
			}
		},
		"language" to { (_, value) ->
			//更改为Idea扩展规则
			mapOf("x-intellij-language-injection" to value as String)
		},
		"deprecated" to { (_, value) ->
			//更改为Idea扩展规则
			when(value) {
				is String -> mapOf("deprecationMessage" to value)
				true -> mapOf("deprecationMessage" to "")
				else -> mapOf()
			}
		},
		"enumConsts" to { (_, value) ->
			//提取路径`enumSchema/value`对应的值列表
			val newValue = (value as List<Map<String, Any?>>).mapNotNull { it["value"] }
			when {
				newValue.isNotEmpty() -> mapOf("enum" to newValue)
				else -> mapOf()
			}
		}
	)
	
	/**配置数据映射。先前的数据映射会被清空。*/
	fun setDataMap(dataMap: Map<String, Any?>) {
		SchemaGenerator.dataMap.clear()
		SchemaGenerator.dataMap += dataMap
	}
	
	/**
	 * 添加扩展约束规则。
	 *
	 * 默认的扩展约束规则：
	 * * $ref: string
	 * * $gen: string
	 * * language: string
	 * * deprecated: string | boolean
	 * * enumConsts: {value: string, description: string}
	 * */
	fun addExtendedRules(vararg rules: Pair<String, SchemaRule>) {
		extendedRuleMap += rules
	}
	
	
	/**
	 * 根据输入文本和输入数据类型，生成能被解析的扩展的Json Schema。默认使用Yaml类型。
	 *
	 * 输入文本的格式：扩展的Json Schema。
	 */
	fun generateExtendedSchema(inputText: String, inputType: DataType = DataType.Yaml, outputType: DataType = DataType.Yaml): String {
		val inputMap = inputType.serializer.load<MutableMap<String, Any?>>(inputText)
		convertRules(inputMap)
		return outputType.serializer.dump(inputMap)
	}
	
	/**
	 * 根据输入文本和输入数据类型，生成能被解析的扩展的Json Schema。默认使用Yaml类型。
	 *
	 * 输入文本的格式：扩展的Json Schema。
	 */
	fun generateExtendedSchema(inputFile: File, outputFile: File, inputType: DataType = DataType.Yaml, outputType: DataType = DataType.Yaml) {
		val inputMap = inputType.serializer.load<MutableMap<String, Any?>>(inputFile)
		convertRules(inputMap)
		outputType.serializer.dump(inputMap, outputFile)
	}
	
	private fun convertRules(map: MutableMap<String, Any?>) {
		//递归遍历整个约束映射的深复制，处理原本的约束映射
		//如果找到了自定义规则，则替换成规则集合中指定的官方规则
		//使用并发映射解决java.util.ConcurrentModificationException
		//过滤掉值为null的键值对，防止NPE
		for((key, value) in ConcurrentHashMap<String, Any?>(map.filterValues { it != null })) {
			when {
				//如果值为映射，则继续向下递归遍历，否则检查是否匹配规则名
				value is Map<*, *> -> convertRules(value as MutableMap<String, Any?>)
				//考虑oneOf，allOf等情况
				key in multiSchemaRuleNames -> for(elem in value as List<MutableMap<String, Any?>>) {
					convertRules(elem)
				}
				//如果找到了对应规则名的规则，则执行规则并替换
				else -> extendedRuleMap[key]?.let {
					val newRule = it(key to value)
					//居然还能直接这样写？
					map -= key
					map += newRule
				}
			}
		}
	}
}
