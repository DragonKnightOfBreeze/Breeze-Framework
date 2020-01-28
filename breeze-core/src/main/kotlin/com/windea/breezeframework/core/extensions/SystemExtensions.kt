@file:JvmName("SystemExtensions")

package com.windea.breezeframework.core.extensions

import java.io.*
import java.util.*
import java.util.concurrent.*
import javax.script.*

/**
 * 访问环境变量。
 *
 * @see System.getenv
 */
@JvmSynthetic
fun environmentVariables(): Map<String, String> {
	return System.getenv()
}

/**
 * 访问系统属性。
 *
 * @see System.getProperties
 */
@JvmSynthetic
@Suppress("UNCHECKED_CAST")
fun systemProperties(): Map<String, String> {
	return Collections.unmodifiableMap(System.getProperties()) as Map<String, String>
}


/**执行一段懒加载的脚本。需要指定脚本语言的名字或扩展名。其实现依赖于对应的第三方库。*/
inline fun <reified T> eval(language: String, lazyScript: () -> String): T? {
	return getEngine(language).eval(lazyScript()) as? T
}

/**绑定一组属性，执行一段懒加载的脚本。需要指定脚本语言的名字或扩展名。其实现依赖于对应的第三方库。*/
inline fun <reified T> eval(language: String, bindings: Bindings, lazyScript: () -> String): T? {
	return getEngine(language).eval(lazyScript(), bindings) as? T
}

/**绑定多组属性，执行一段懒加载的脚本。需要指定脚本语言的名字或扩展名。其实现依赖于对应的第三方库。*/
inline fun <reified T> eval(language: String, context: ScriptContext, lazyScript: () -> String): T? {
	return getEngine(language).eval(lazyScript(), context) as? T
}

@PublishedApi
internal fun getEngine(language: String): ScriptEngine {
	val fixedLanguage = language.toLowerCase()
	return scriptEngines.getOrPut(fixedLanguage) {
		engineManager.getEngineByName(fixedLanguage) ?: engineManager.getEngineByExtension(fixedLanguage)
		?: throw UnsupportedOperationException("No script engine library found for target script language.")
	}
}

private val engineManager by lazy { ScriptEngineManager() }
private val scriptEngines by lazy { concurrentMapOf<String, ScriptEngine>() }


/**执行一段懒加载的命令。可指定工作目录，默认为当前目录；默认环境变量为空。*/
@JvmSynthetic
inline fun exec(vararg environmentVariables: String, workDirectory: File? = null, lazyCommand: () -> String): Process {
	return Runtime.getRuntime().exec(lazyCommand(), environmentVariables, workDirectory)
}

/**执行一段懒加载的命令，并保持线程阻塞直到执行完毕为止。默认环境变量为空，默认工作目录为当前工作目录。*/
@JvmSynthetic
inline fun execBlocking(vararg environmentVariables: String, workDirectory: File? = null, lazyCommand: () -> String): Process {
	return Runtime.getRuntime().exec(lazyCommand(), environmentVariables, workDirectory).also { it.waitFor() }
}

/**执行一段懒加载的命令，并保持线程阻塞直到执行完毕为止。默认环境变量为空，默认工作目录为当前工作目录。*/
@JvmSynthetic
inline fun execBlocking(vararg environmentVariables: String, workDirectory: File? = null,
	timeout: Long, unit: TimeUnit, lazyCommand: () -> String): Process {
	return Runtime.getRuntime().exec(lazyCommand(), environmentVariables, workDirectory).also { it.waitFor(timeout, unit) }
}
