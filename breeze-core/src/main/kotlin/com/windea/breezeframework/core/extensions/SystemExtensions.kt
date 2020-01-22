@file:JvmName("SystemExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import java.io.*
import javax.script.*

private val engineManager = ScriptEngineManager()
private val scriptEngines = mutableMapOf<String, ScriptEngine>()

@PublishedApi
internal fun getEngine(language: String): ScriptEngine {
	val fixedLanguage = language.toLowerCase()
	return scriptEngines.getOrPut(fixedLanguage) {
		engineManager.getEngineByName(fixedLanguage) ?: engineManager.getEngineByExtension(fixedLanguage)
		?: throw UnsupportedOperationException("No script engine library found for target script language.")
	}
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


/**执行一段懒加载的命令。可指定工作目录，默认为当前目录；可指定环境变量，默认为空。*/
@JvmSynthetic
inline fun exec(vararg environmentVariables: String, workDirectory: File? = null, lazyCommand: () -> String): Process {
	return Runtime.getRuntime().exec(lazyCommand(), environmentVariables, workDirectory)
}
