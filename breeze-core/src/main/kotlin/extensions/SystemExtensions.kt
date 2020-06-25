@file:JvmName("SystemExtensions")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.*
import java.io.*
import java.util.*
import java.util.concurrent.*
import javax.script.*
import kotlin.system.*

/**
 * 访问环境变量。
 * @see java.lang.System.getenv
 */
val environmentVariables: Map<String, String> = System.getenv()

/**
 * 访问系统属性。
 * @see java.lang.System.getProperties
 */
@Suppress("UNCHECKED_CAST")
val systemProperties: Map<String, String> = Collections.unmodifiableMap(System.getProperties()) as Map<String, String>


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
		?: throw UnsupportedOperationException("No script engine library found for value script language.")
	}
}

private val engineManager by lazy { ScriptEngineManager() }
private val scriptEngines by lazy { concurrentMapOf<String, ScriptEngine>() }


/**执行一段懒加载的命令。可指定工作目录，默认为当前目录；默认环境变量为空。*/
@JvmSynthetic
inline fun exec(vararg environmentVariables: String, workDirectory: File? = null, lazyCommand: () -> String): Process {
	return Runtime.getRuntime().exec(lazyCommand(), environmentVariables, workDirectory)
}

/**执行一段懒加载的命令，并保持进程阻塞直到执行完毕为止。默认环境变量为空，默认工作目录为当前工作目录。*/
@JvmSynthetic
inline fun execBlocking(vararg environmentVariables: String, workDirectory: File? = null, lazyCommand: () -> String): Process {
	return Runtime.getRuntime().exec(lazyCommand(), environmentVariables, workDirectory).also { it.waitFor() }
}

/**执行一段懒加载的命令，并保持进程阻塞直到执行完毕为止。默认环境变量为空，默认工作目录为当前工作目录。*/
@JvmSynthetic
inline fun execBlocking(vararg environmentVariables: String, workDirectory: File? = null,
	timeout: Long, unit: TimeUnit, lazyCommand: () -> String): Process {
	return Runtime.getRuntime().exec(lazyCommand(), environmentVariables, workDirectory).also { it.waitFor(timeout, unit) }
}


/**循环扫描命令行的下一个输入命令，以执行相应的操作。默认的退出命令是"exit"且不区分大小写。*/
@UnstableImplementationApi
@JvmSynthetic
inline fun executeCommand(exitCommand: String = "exit", block: (String) -> Unit) {
	val scanner = Scanner(System.`in`)
	val command = scanner.next()
	while(true) {
		if(command equalsIgnoreCase exitCommand) exitProcess(0)
		block(command)
	}
}
