@file:Suppress("DuplicatedCode")

package com.windea.breezeframework.logger.internal

import com.windea.breezeframework.logger.*
import java.io.*

object ColorfulLogger : Logger {
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
		if(LoggerConfig.minLogLevel > level) return
		
		val levelSnippet = if(LoggerConfig.isLevelIncluded) level.text else null
		val dateSnippet = if(LoggerConfig.isDateIncluded) currentDate.let { "[$it]" } else null
		val pathSnippet = when {
			LoggerConfig.isPathIncluded && !LoggerConfig.isPathAbbreviated -> currentClassName
			LoggerConfig.isPathAbbreviated -> currentClassNameAbbreviation
			else -> null
		}
		val markersSnippet = arrayOf(dateSnippet, levelSnippet, pathSnippet).filterNotNull()
			.joinToString(" ", "", LoggerConfig.delimiter)
		val messageSnippet = message.toString()
		val logSnippet = "$markersSnippet$messageSnippet\n"
		
		LoggerConfig.outputPath?.let { File(it).appendText(logSnippet) }
		
		val (prefix, suffix) = if(LoggerConfig.isColorful) level.colorCommandPair else "" to ""
		print(logSnippet.let { "$prefix$it$suffix" })
	}
}
