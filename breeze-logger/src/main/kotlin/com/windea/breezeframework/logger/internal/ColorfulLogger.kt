package com.windea.breezeframework.logger.internal

import com.windea.breezeframework.logger.*
import com.windea.breezeframework.logger.internal.LoggerConfig.delimiter
import com.windea.breezeframework.logger.internal.LoggerConfig.isColorful
import com.windea.breezeframework.logger.internal.LoggerConfig.isDateIncluded
import com.windea.breezeframework.logger.internal.LoggerConfig.isLevelIncluded
import com.windea.breezeframework.logger.internal.LoggerConfig.isPathAbbreviated
import com.windea.breezeframework.logger.internal.LoggerConfig.isPathIncluded
import com.windea.breezeframework.logger.internal.LoggerConfig.minLogLevel
import com.windea.breezeframework.logger.internal.LoggerConfig.output
import java.io.*
import java.text.*
import java.util.*

internal object ColorfulLogger : Logger {
	override val name: String get() = "ColorfulLogger"
	
	override val isTraceEnabled: Boolean get() = minLogLevel <= LogLevel.Trace
	override val isDebugEnabled: Boolean get() = minLogLevel <= LogLevel.Debug
	override val isInfoEnabled: Boolean get() = minLogLevel <= LogLevel.Info
	override val isWarnEnabled: Boolean get() = minLogLevel <= LogLevel.Warn
	override val isErrorEnabled: Boolean get() = minLogLevel <= LogLevel.Error
	override val isFatalEnabled: Boolean get() = minLogLevel <= LogLevel.Fatal
	
	override fun trace(message: Any?) = log(LogLevel.Trace, message)
	
	override fun trace(lazyMessage: () -> Any?) = log(LogLevel.Trace, lazyMessage())
	
	override fun debug(message: Any?) = log(LogLevel.Debug, message)
	
	override fun debug(lazyMessage: () -> Any?) = log(LogLevel.Debug, lazyMessage())
	
	override fun info(message: Any?) = log(LogLevel.Info, message)
	
	override fun info(lazyMessage: () -> Any?) = log(LogLevel.Info, lazyMessage())
	
	override fun warn(message: Any?) = log(LogLevel.Warn, message)
	
	override fun warn(lazyMessage: () -> Any?) = log(LogLevel.Warn, lazyMessage)
	
	override fun error(message: Any?) = log(LogLevel.Error, message)
	
	override fun error(lazyMessage: () -> Any?) = log(LogLevel.Error, lazyMessage())
	
	override fun fatal(message: Any?) = log(LogLevel.Fatal, message)
	
	override fun fatal(lazyMessage: () -> Any?) = log(LogLevel.Fatal, lazyMessage())
	
	private fun log(level: LogLevel, message: Any?) {
		if(minLogLevel > level) return
		
		val levelSnippet = if(isLevelIncluded) level.text else null
		val dateSnippet = if(isDateIncluded) currentDate.let { "[$it]" } else null
		val pathSnippet = if(isPathIncluded && !isPathAbbreviated) currentClassName
		else if(isPathAbbreviated) currentClassNameAbbreviation else null
		val markersSnippet = arrayOf(dateSnippet, levelSnippet, pathSnippet)
			.filterNotNull().joinToString(" ", "", delimiter)
		val messageSnippet = message.toString()
		val logSnippet = "$markersSnippet$messageSnippet\n"
		
		output?.let { File(it).appendText(logSnippet) }
		
		val (prefix, suffix) = if(isColorful) level.colorCommandPair else "" to ""
		print(logSnippet.let { "$prefix$it$suffix" })
	}
	
	private val currentDate
		get() = SimpleDateFormat(LoggerConfig.dateFormat).format(Date())
	
	private val currentClassName
		get() = RuntimeException().stackTrace[3].className
	
	private val currentClassNameAbbreviation
		get() = currentClassName.split(".").joinToString(".") { it.substring(0, 1) } +
		        currentClassName.substring(currentClassName.lastIndexOf('.') + 2, currentClassName.length)
	
	private val LogLevel.colorCommandPair: Pair<String, String>
		get() = when(this) {
			LogLevel.Off -> "" to ""
			LogLevel.Trace -> "\u001B[37m" to "\u001B[0m" //light gray
			LogLevel.Debug -> "\u001B[32m" to "\u001B[0m" //green
			LogLevel.Info -> "\u001B[34m" to "\u001B[0m" //blue
			LogLevel.Warn -> "\u001B[91m" to "\u001B[0m" //light red
			LogLevel.Error -> "\u001B[31m" to "\u001B[0m" //red
			LogLevel.Fatal -> "\u001B[31;4m" to "\u001B[0m" //red, underline
		}
}
