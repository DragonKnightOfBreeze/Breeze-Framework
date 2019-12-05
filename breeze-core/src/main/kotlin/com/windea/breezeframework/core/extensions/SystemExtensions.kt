@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import java.io.*
import javax.script.*

/**访问系统属性。*/
object SystemProperties {
	private val properties = System.getProperties()
	
	/**操作系统名。*/
	val osName: String? = properties.getProperty("os.name")
	/**用户名。*/
	val userName: String? = properties.getProperty("user.name")
	/**用户首页目录。*/
	val userHome: String? = properties.getProperty("user.home")
	/**用户目录。即，项目的当前工作路径。*/
	val userDir: String? = properties.getProperty("user.dir")
	/**用户所属国家。*/
	val userCountry: String? = properties.getProperty("user.country")
	/**用户所用语言。*/
	val userLanguage: String? = properties.getProperty("user.language")
	/**文件分隔符。*/
	val fileSeparator: String? = properties.getProperty("file.separator")
	/**文件编码。*/
	val fileEncoding: String? = properties.getProperty("file.encoding")
	/**行分隔符。*/
	val lineSeparator: String? = properties.getProperty("line.separator")
	
	/**得到指定的系统属性。*/
	operator fun get(name: String): String? = properties.getProperty(name)
}


/**访问环境变量。*/
object EnvironmentVariables {
	private val env = System.getenv()
	
	/**得到Java Home。*/
	val javaHome: String? = env["JAVA_HOME"]
	/**得到Kotlin Home。*/
	val kotlinHome: String? = env["KOTLIN_HOME"]
	
	/**得到指定的环境变量。*/
	operator fun get(name: String): String? = env[name]
}


/**访问脚本引擎。*/
object ScriptEngines {
	private val engineManager = ScriptEngineManager()
	
	/**
	 * 得到kotlinScript的脚本引擎。
	 *
	 * 注意：其实现依赖于第三方库，如：`kotlin-main-kts`。
	 */
	val kotlinScript: ScriptEngine by lazy { engineManager.getEngineByExtension("kts") }
	/**
	 * 得到javaScript的脚本引擎。
	 */
	val javaScript: ScriptEngine by lazy { engineManager.getEngineByExtension("js") }
	/**
	 * 得到typeScript的脚本引擎。
	 *
	 * 注意：其实现依赖于第三方库。
	 */
	val typeScript: ScriptEngine by lazy { engineManager.getEngineByExtension("ts") }
	/**
	 * 得到python的脚本引擎。
	 *
	 * 注意：其实现依赖于第三方库。
	 */
	val python: ScriptEngine by lazy { engineManager.getEngineByExtension("py") }
	/**
	 * 得到lua的脚本引擎。
	 *
	 * 注意：其实现依赖于第三方库。
	 */
	val lua: ScriptEngine by lazy { engineManager.getEngineByExtension("lua") }
	
	/**得到指定扩展名的脚本引擎。*/
	operator fun get(extension: String): ScriptEngine? = engineManager.getEngineByExtension(extension)
}

/**执行一段懒加载的脚本。*/
inline fun <reified T> ScriptEngine.eval(lazyScript: () -> String): T? {
	return this.eval(lazyScript()) as? T
}

/**绑定一组属性，执行一段懒加载的脚本。*/
inline fun <reified T> ScriptEngine.eval(bindings: Bindings, lazyScript: () -> String): T? {
	return this.eval(lazyScript(), bindings) as? T
}

/**绑定多组属性，执行一段懒加载的脚本。*/
inline fun <reified T> ScriptEngine.eval(context: ScriptContext, lazyScript: () -> String): T? {
	return this.eval(lazyScript(), context) as? T
}


/**执行一段懒加载的命令。可指定工作目录，默认为当前目录；可指定环境变量，默认为空。*/
inline fun exac(vararg environmentVariables: String, workDirectory: File? = null, lazyCommand: () -> String): Process {
	return Runtime.getRuntime().exec(lazyCommand(), environmentVariables, workDirectory)
}
