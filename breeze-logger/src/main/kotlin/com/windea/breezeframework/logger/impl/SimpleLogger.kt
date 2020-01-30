@file:Suppress("DuplicatedCode")

package com.windea.breezeframework.logger.impl

import com.windea.breezeframework.logger.*
import java.io.*
import java.text.*
import java.util.*

class SimpleLogger : Logger {
	override val config: LoggerConfig = LoggerConfig()

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
		if(config.minLogLevel > level) return

		val levelSnippet = if(config.isLevelIncluded) level.text else null
		val dateSnippet = if(config.isDateIncluded) currentDate.let { "[$it]" } else null
		val pathSnippet = when {
			config.isPathIncluded && !config.isPathAbbreviated -> currentClassName
			config.isPathAbbreviated -> currentClassNameAbbreviation
			else -> null
		}
		val markersSnippet = arrayOf(dateSnippet, levelSnippet, pathSnippet).filterNotNull()
			.joinToString(" ", "", config.delimiter)
		val messageSnippet = message.toString()
		val logSnippet = "$markersSnippet$messageSnippet\n"

		config.outputPath?.let { File(it).appendText(logSnippet) }

		System.err.print(logSnippet)
	}

	private val currentDate get() = SimpleDateFormat(config.dateFormat).format(Date())

	private val currentClassName get() = Exception().stackTrace[3].className

	private val currentClassNameAbbreviation
		get() = currentClassName.split(".").joinToString(".") { it.substring(0, 1) } +
		        currentClassName.substring(currentClassName.lastIndexOf('.') + 2, currentClassName.length)
}
