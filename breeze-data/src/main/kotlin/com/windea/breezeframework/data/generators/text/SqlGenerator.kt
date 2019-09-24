@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.data.generators.text

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.data.enums.*
import com.windea.breezeframework.data.generators.*
import java.io.*

/**Sql语句的生成器。*/
object SqlGenerator : Generator {
	/**
	 * 根据输入文本和输入数据类型，生成Sql数据。默认使用Yaml类型。
	 *
	 * 输入文本的格式：`#/{database}/{table}/[]/{columns}/{column}`。
	 */
	fun generateSqlData(inputText: String, inputType: DataType = DataType.Yaml): String {
		val rawInputMap = inputType.serializer.loadAsMap(inputText)
		val inputMap = rawInputMap as SqlDataMap
		val databaseName = inputMap.keys.first()
		val database = inputMap.values.first()
		
		return """
		use $databaseName;
		
		${database.joinToString("\n\n") { (tableName, table) ->
			val columnNamesSnippet = table.first().keys.joinToString()
			
			"""
			insert into $tableName ($columnNamesSnippet) values
			${table.joinToString(",\n", "", ";\n") { data ->
				val columnsSnippet = data.values.joinToString {
					it.toString().wrapQuote("'").escape()
				}
				
				"""  ($columnsSnippet)"""
			}}
			""".toMultilineText()
		}}
		""".toMultilineText()
	}
	
	/**
	 * 根据输入文件和输入数据类型，生成Sql数据到指定输出文件。默认使用Yaml类型。
	 *
	 * 输入文本的格式：`#/{database}/{table}/[]/{columns}/{column}`。
	 */
	fun generateSqlData(inputFile: File, outputFile: File, inputType: DataType = DataType.Yaml) {
		val outputText = generateSqlData(inputFile.readText(), inputType)
		outputFile.writeText(outputText)
	}
}
