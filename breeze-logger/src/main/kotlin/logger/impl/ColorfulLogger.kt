// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("DuplicatedCode")

package icu.windea.breezeframework.logger.impl

import icu.windea.breezeframework.logger.*
import java.io.*
import java.text.*
import java.util.*

/**输出彩色文本的日志器。这些彩色文本可在控制台正常显示。*/
class ColorfulLogger(
	override val config: LoggerConfig = LoggerConfig(),
) : Logger {
	constructor(block: LoggerConfig.() -> Unit) : this(LoggerConfig().apply(block))


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

		val (prefix, suffix) = level.colorCommandPair
		print(logSnippet.let { "$prefix$it$suffix" })
	}

	private val currentDate get() = SimpleDateFormat(config.dateFormat).format(Date())

	private val currentClassName get() = Exception().stackTrace[3].className

	private val currentClassNameAbbreviation
		get() = currentClassName.split(".").joinToString(".") { it.take(1) } +
			currentClassName.substring(currentClassName.lastIndexOf('.') + 2, currentClassName.length)

	private val LogLevel.colorCommandPair
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
