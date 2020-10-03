// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.generator.specific

import com.windea.breezeframework.core.domain.text.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.generator.*
import com.windea.breezeframework.serializer.*
import java.io.*

/**Intellij IDEA配置文件文本的生成器。*/
object IdeaConfigGenerator : Generator {
	/**
	 * 根据输入文本和输入数据类型，生成自定义Yaml注解的动态模版配置文件文本。默认使用Yaml类型。
	 *
	 * 输入文本的格式：Json Schema。
	 */
	fun generateYamlAnnotation(inputText: String, inputFormat: DataFormat = DataFormat.Yaml): String {
		val inputMap = inputFormat.serializer.read<SchemaDefinitionMap>(inputText)
		return getYamlAnnotationString(inputMap)
	}

	/**
	 * 根据输入文件和输入数据类型，生成自定义Yaml注解的动态模版配置文件文本。默认使用Yaml类型。
	 *
	 * 输入文本的格式：Json Schema。
	 */
	fun generateYamlAnnotation(inputFile: File, outputFile: File, inputFormat: DataFormat = DataFormat.Yaml) {
		val inputMap = inputFormat.serializer.read<SchemaDefinitionMap>(inputFile)
		outputFile.writeText(getYamlAnnotationString(inputMap))
	}

	private fun getYamlAnnotationString(inputMap: SchemaDefinitionMap): String {
		val definitions = inputMap["definitions"] as SchemaMap
		return """
		<templateSet group="YamlAnnotation">
		${definitions.joinToString("\n\n") { (templateName, template) ->
			val description = (template.getOrDefault("description", "") as String).escapeBy(EscapeType.Java)
			val params = if("properties" in template) template["properties"] as Map<String, Map<String, Any?>> else mapOf()
			val paramSnippet = if(params.isEmpty()) "" else ": {${params.keys.joinToString(", ") { "$it: $$it$" }}}"

			"""
			  <template name="@$templateName" value="@$templateName$paramSnippet"
		                description="$description"
		                toReformat="true" toShortenFQNames="true" useStaticImport="true">${
			params.joinToString("\n") { (paramName, param) ->
				val defaultValue = (param.getOrDefault("default", "") as String).escapeBy(EscapeType.Java)

				"""    <variable name="$paramName" expression="" defaultValue="&quot;$defaultValue&quot;" alwaysStopAt="true"/>"""
			}.ifNotEmpty { "\n$it" }}
			    <context>
			      <option name="CSS" value="false"/>
			      <option name="CUCUMBER_FEATURE_FILE" value="false"/>
			      <option name="CoffeeScript" value="false"/>
			      <option name="DART" value="false"/>
			      <option name="ECMAScript6" value="false"/>
			      <option name="HAML" value="false"/>
			      <option name="HTML" value="false"/>
			      <option name="Handlebars" value="false"/>
			      <option name="JADE" value="false"/>
			      <option name="JAVA_SCRIPT" value="false"/>
			      <option name="JSON" value="false"/>
			      <option name="JSP" value="false"/>
			      <option name="OTHER" value="true"/>
			      <option name="PL/SQL" value="false"/>
			      <option name="REQUEST" value="false"/>
			      <option name="SQL" value="false"/>
			      <option name="TypeScript" value="false"/>
			      <option name="Vue" value="false"/>
			      <option name="XML" value="false"/>
			    </context>
			  </template>
			""".trimRelativeIndent()
		}}
		</templateSet>
		""".trimRelativeIndent()
	}
}
