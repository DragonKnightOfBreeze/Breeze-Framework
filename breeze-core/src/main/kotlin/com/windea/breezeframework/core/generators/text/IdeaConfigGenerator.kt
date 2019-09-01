@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.core.generators.text

import com.windea.breezeframework.core.enums.*
import com.windea.breezeframework.core.extensions.*
import java.io.*

/**Intellij IDEA配置文件文本的生成器。*/
object IdeaConfigGenerator : TextGenerator {
	/**
	 * 根据输入文本和输入数据类型，生成自定义Yaml注解的动态模版配置文件文本。默认使用Yaml类型。
	 *
	 * 输入文本的格式：Json Schema。
	 */
	fun generateYamlAnnotation(inputText: String, inputType: DataType): String {
		val rawInputMap = inputType.loader.fromString(inputText)
		val inputMap = rawInputMap as SchemaDefinitionMap
		val definitions = inputMap["definitions"] as SchemaMap
		
		return """
		<templateSet group="YamlAnnotation">
		${definitions.joinToString("\n\n") { (templateName, template) ->
			val description = (template.getOrDefault("description", "") as String).unescape()
			val params = if("properties" in template) template["properties"] as SchemaMap else mapOf()
			val paramSnippet = if(params.isEmpty()) "" else ": {${params.keys.joinToString(", ") { "$it: $$it$" }}}"
			
			"""
			  <template name="@$templateName" value="@$templateName$paramSnippet"
		                description="$description"
		                toReformat="true" toShortenFQNames="true" useStaticImport="true">${
			params.joinToString("\n") { (paramName, param) ->
				val defaultValue = (param.getOrDefault("default", "") as String).unescape()
				
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
			""".toMultilineText()
		}}
		</templateSet>
		""".toMultilineText()
	}
	
	/**
	 * 根据输入文件和输入数据类型，生成自定义Yaml注解的动态模版配置文件文本。默认使用Yaml类型。
	 *
	 * 输入文本的格式：Json Schema。
	 */
	fun generateYamlAnnotation(inputFile: File, outputFile: File, inputType: DataType = DataType.Yaml) {
		val outputText = generateYamlAnnotation(inputFile.readText(), inputType)
		outputFile.writeText(outputText)
	}
}
