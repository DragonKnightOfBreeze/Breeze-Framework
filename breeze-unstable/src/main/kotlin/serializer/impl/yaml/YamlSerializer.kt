// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serializer.impl.yaml

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.mapper.impl.*
import com.windea.breezeframework.serializer.impl.json.*
import org.yaml.snakeyaml.*
import java.io.*
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper as JacksonYamlMapper

/**
 * Yaml的序列化器。
 *
 * 其实现默认由`breeze-mapper`提供，但也可以依赖于第三方库，如`jackson`，`snakeyaml`。
 */
interface YamlSerializer : JsonSerializer {
	/**从指定字符串读取所有数据。*/
	fun readAll(string: String): List<Any?>

	/**从指定文件读取所有数据。*/
	fun readAll(file: File): List<Any?>

	/**写入所有数据到字符串。*/
	fun <T : Any> writeAll(data: Iterable<T>): String

	/**写入所有数据到文件。默认以易读格式输出。*/
	fun <T : Any> writeAll(data: Iterable<T>, file: File)

	companion object {
		private const val jacksonYamlClassName = "com.fasterxml.jackson.dataformat.yaml.YAMLMapper"
		private const val snackYamlClassName = "org.yaml.snakeyaml.Yaml"

		/**得到Yaml的序列化器的实例。*/
		val instance: YamlSerializer = when {
			presentInClassPath(jacksonYamlClassName) -> JacksonYamlSerializer
			presentInClassPath(snackYamlClassName) -> SnakeYamlSerializer
			else -> BreezeYamlSerializer
			//else -> throw IllegalStateException("No supported yaml serializer implementation found in classpath.")
		}

		/**配置BreezeYaml的序列化器。注意需要在使用前配置，并且仅当对应的序列化器适用时才应调用。*/
		@OptionalApi
		fun configureBreezeYaml(block: (YamlMapper.Config.Builder) -> Unit) {
			block(BreezeYamlSerializer.configBuilder)
		}

		/**配置JacksonYaml的序列化器。注意需要在使用前配置，并且仅当对应的序列化器适用时才应调用。*/
		@OptionalApi
		fun configureJacksonYaml(block: (JacksonYamlMapper) -> Unit) {
			block(JacksonYamlSerializer.mapper)
		}

		/**配置SnakeYaml的序列化器。注意需要在使用前配置，并且仅当对应的序列化器适用时才应调用。*/
		@OptionalApi
		fun configureSnakeYaml(block: (LoaderOptions, DumperOptions) -> Unit) {
			block(SnakeYamlSerializer.loaderOptions, SnakeYamlSerializer.dumperOptions)
		}
	}
}

