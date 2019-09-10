@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import java.io.*
import java.util.*
import javax.script.*

/**访问系统属性。*/
object SystemProperties {
	private val properties: Properties = System.getProperties()
	
	/**操作系统名。*/
	val osName: String? = this["os.name"]
	/**用户名。*/
	val userName: String? = this["user.name"]
	/**用户首页目录。*/
	val userHome: String? = this["user.home"]
	/**用户目录。即，项目的当前工作路径。*/
	val userDir: String? = this["user.dir"]
	/**用户所属国家。*/
	val userCountry: String? = this["user.country"]
	/**用户所用语言。*/
	val userLanguage: String? = this["user.language"]
	/**文件分隔符。*/
	val fileSeparator: String? = this["file.separator"]
	/**文件编码。*/
	val fileEncoding: String? = this["file.encoding"]
	/**行分隔符。*/
	val lineSeparator: String? = this["line.separator"]
	
	/**得到指定的系统属性。*/
	operator fun get(name: String): String? = properties.getProperty(name)
}


/**访问环境变量。*/
object EnvironmentVariables {
	private val env = System.getenv()
	
	/**得到Java Home。*/
	val javaHome: String? = this["JAVA_HOME"]
	/**得到Kotlin Home。*/
	val kotlinHome: String? = this["KOTLIN_HOME"]
	
	/**得到指定的环境变量。*/
	operator fun get(name: String): String? = env[name]
}


/**执行一段懒加载的脚本。默认为kts。需要自行提供脚本语言支持。*/
inline fun <reified T> eval(extension: String = "kts", lazyScript: () -> String): T? {
	return scriptEngineManager.getEngineByExtension(extension)?.eval(lazyScript()) as? T
}

/**执行一段懒加载的脚本。绑定一组属性。可指定语言后缀，默认为kts。需要自行提供脚本语言支持。*/
inline fun <reified T> eval(extension: String = "kts", bindings: Bindings, lazyScript: () -> String): T? {
	return scriptEngineManager.getEngineByExtension(extension)?.eval(lazyScript(), bindings) as? T
}

/**执行一段懒加载的脚本。绑定多组属性。可指定语言后缀，默认为kts。需要自行提供脚本语言支持。*/
inline fun <reified T> eval(extension: String = "kts", context: ScriptContext, lazyScript: () -> String): T? {
	return scriptEngineManager.getEngineByExtension(extension)?.eval(lazyScript(), context) as? T
}

@PublishedApi internal val scriptEngineManager by lazy { ScriptEngineManager() }


/**执行一段懒加载的命令。可指定工作目录，默认为当前目录；可指定环境变量，默认为空。*/
inline fun exac(workDirectory: File? = null, vararg environmentVariables: String, lazyCommand: () -> String): Process {
	return Runtime.getRuntime().exec(lazyCommand(), environmentVariables, workDirectory)
}
