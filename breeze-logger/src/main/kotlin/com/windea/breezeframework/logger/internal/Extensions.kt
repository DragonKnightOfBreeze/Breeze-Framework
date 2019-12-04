package com.windea.breezeframework.logger.internal

import com.windea.breezeframework.logger.*
import java.text.*
import java.util.*

internal val currentDate
	get() = SimpleDateFormat(LoggerConfig.dateFormat).format(Date())

internal val currentClassName
	get() = RuntimeException().stackTrace[3].className

internal val currentClassNameAbbreviation
	get() = currentClassName.split(".").joinToString(".") { it.substring(0, 1) } +
	        currentClassName.substring(currentClassName.lastIndexOf('.') + 2, currentClassName.length)

internal val LogLevel.colorCommandPair: Pair<String, String>
	get() = when(this) {
		LogLevel.Off -> "" to ""
		LogLevel.Trace -> "\u001B[37m" to "\u001B[0m" //light gray
		LogLevel.Debug -> "\u001B[32m" to "\u001B[0m" //green
		LogLevel.Info -> "\u001B[34m" to "\u001B[0m" //blue
		LogLevel.Warn -> "\u001B[91m" to "\u001B[0m" //light red
		LogLevel.Error -> "\u001B[31m" to "\u001B[0m" //red
		LogLevel.Fatal -> "\u001B[31;4m" to "\u001B[0m" //red, underline
	}
