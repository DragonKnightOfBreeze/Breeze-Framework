package com.windea.breezeframework.serializer.impl.yaml

import com.fasterxml.jackson.dataformat.yaml.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.serializer.impl.json.*
import org.yaml.snakeyaml.*
import java.io.*

/**
 * Yaml的序列化器。
 *
 * 注意：其实现依赖于第三方库，如`jackson`，`snakeyaml`。
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
			else -> throw IllegalStateException("No supported yaml serializer implementation found in classpath.")
		}


		/**配置JacksonYaml的序列化器。注意需要在使用前配置，并且仅当对应的序列化器适用时才应调用。*/
		fun configureJacksonYaml(block: (YAMLMapper) -> Unit) {
			block(JacksonYamlSerializer.mapper)
		}

		/**配置SnakeYaml的序列化器。注意需要在使用前配置，并且仅当对应的序列化器适用时才应调用。*/
		fun configureSnakeYaml(block: (LoaderOptions, DumperOptions) -> Unit) {
			block(SnakeYamlSerializer.loaderOptions, SnakeYamlSerializer.dumperOptions)
		}
	}
}

