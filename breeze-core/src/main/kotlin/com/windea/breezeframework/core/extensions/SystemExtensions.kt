@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.marks.*
import java.io.*
import javax.script.*
import kotlin.properties.*
import kotlin.reflect.*

/**访问系统参数。*/
val systemAttributes by SystemAttributesAccessor()

class SystemAttributesAccessor internal constructor() : ReadOnlyProperty<Nothing?, SystemAttributesAccessor> {
	/**项目的实际工作路径。*/
	val workDirectory: String? = System.getProperty("user.dir")
	
	/**项目的实际工作平台名。例如：Windows 10。*/
	val platformName: String? = System.getProperty("os.name")
	
	/**项目工作环境的用户名。*/
	val userName: String? = System.getProperty("user.name")
	
	/**项目工作环境的用户所在国家。*/
	val userCountry: String? = System.getProperty("user.country")
	
	/**项目工作环境的用户所用语言。*/
	val userLanguage: String? = System.getProperty("user.language")
	
	/**项目工作环境的文件分隔符。*/
	val fileSeparator: String? = System.getProperty("file.separator")
	
	/**项目工作环境的文件编码。*/
	val fileEncoding: String? = System.getProperty("file.encoding")
	
	/**项目工作环境的行分隔符。*/
	val lineSeparator: String? = System.getProperty("line.separator")
	
	override fun getValue(thisRef: Nothing?, property: KProperty<*>) = this
}


//TODO allows `engine.compile()` operation
/**执行一段懒加载的脚本。默认为kts。默认仅支持js、kts脚本。*/
@NotImplemented
inline fun <reified T> eval(extension: String = "kts", lazyScript: () -> String): T? {
	return scriptEngineManager.getEngineByExtension(extension)?.eval(lazyScript()) as? T
}

/**执行一段懒加载的脚本。绑定一组属性。可指定语言后缀，默认为kts。默认仅支持js、kts脚本。*/
inline fun <reified T> eval(extension: String = "kts", bindings: Bindings, lazyScript: () -> String): T? {
	return scriptEngineManager.getEngineByExtension(extension)?.eval(lazyScript(), bindings) as? T
}

/**执行一段懒加载的脚本。绑定多组属性。可指定语言后缀，默认为kts。默认仅支持js、kts脚本。*/
inline fun <reified T> eval(extension: String = "kts", context: ScriptContext, lazyScript: () -> String): T? {
	return scriptEngineManager.getEngineByExtension(extension)?.eval(lazyScript(), context) as? T
}

/**执行一段读取的脚本。可指定语言后缀，默认为kts。默认仅支持js、kts脚本。*/
inline fun <reified T> eval(extension: String = "kts", reader: Reader): T? {
	return scriptEngineManager.getEngineByExtension(extension)?.eval(reader) as? T
}

/**执行一段读取的脚本。绑定一组属性。可指定语言后缀，默认为kts。需要自行提供脚本语言支持。。*/
inline fun <reified T> eval(extension: String = "kts", reader: Reader, bindings: Bindings): T? {
	return scriptEngineManager.getEngineByExtension(extension)?.eval(reader, bindings) as? T
}

/**执行一段读取的脚本。绑定多组属性。可指定语言后缀，默认为kts。需要自行提供脚本语言支持。*/
inline fun <reified T> eval(extension: String = "kts", reader: Reader, context: ScriptContext): T? {
	return scriptEngineManager.getEngineByExtension(extension)?.eval(reader, context) as? T
}

@PublishedApi internal val scriptEngineManager by lazy { ScriptEngineManager() }


/**执行一段懒加载的命令。可指定工作目录，默认为当前目录；可指定环境变量，默认为空。*/
inline fun exac(workDirectory: File? = null, vararg environmentVariables: String, lazyCommand: () -> String): Process {
	return Runtime.getRuntime().exec(lazyCommand(), environmentVariables.ifEmpty { null }, workDirectory)
}
