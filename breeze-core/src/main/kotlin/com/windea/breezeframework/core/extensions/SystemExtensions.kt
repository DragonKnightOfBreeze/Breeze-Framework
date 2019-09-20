@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import java.io.*
import javax.script.*

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
