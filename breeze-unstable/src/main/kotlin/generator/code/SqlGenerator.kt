// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("UNCHECKED_CAST")

package icu.windea.breezeframework.generator.code

import icu.windea.breezeframework.core.component.*
import icu.windea.breezeframework.core.extension.*
import icu.windea.breezeframework.generator.*
import icu.windea.breezeframework.serializer.*
import java.io.*

/**Sql语句的生成器。*/
object SqlGenerator : Generator {
	/**
	 * 根据输入文本和输入数据格式，生成Sql数据。默认使用Yaml格式。
	 *
	 * 输入文本的格式：`#/{database}/{table}/[]/{columns}/{column}`。
	 */
	fun generateSqlData(inputText: String, inputFormat: DataFormat = DataFormats.Yaml): String {
		val inputMap = inputFormat.serializer.read<SqlDataMap>(inputText)
		return getSqlDataString(inputMap)
	}

	/**
	 * 根据输入文件和输入数据格式，生成Sql数据到指定输出文件。默认使用Yaml格式。
	 *
	 * 输入文本的格式：`#/{database}/{table}/[]/{columns}/{column}`。
	 */
	fun generateSqlData(inputFile: File, outputFile: File, inputFormat: DataFormat = DataFormats.Yaml) {
		val inputMap = inputFormat.serializer.read<SqlDataMap>(inputFile)
		outputFile.writeText(getSqlDataString(inputMap))
	}

	private fun getSqlDataString(inputMap: SqlDataMap): String {
		val databaseName = inputMap.keys.first()
		val database = inputMap.values.first()

		return """
		use $databaseName;

		${
			database.joinToString("\n\n") { (tableName, table) ->
				val columnNamesSnippet = table.first().keys.joinToString()

				"""
			insert into $tableName ($columnNamesSnippet) values
			${
					table.joinToString(",\n", "", ";\n") { data ->
						val columnsSnippet = data.values.joinToString {
							it.toString().quote('\'').escapeBy(Escapers.JavaEscaper)
						}

						"""  ($columnsSnippet)"""
					}
				}
			""".trimRelativeIndent()
			}
		}
		""".trimRelativeIndent()
	}
}
