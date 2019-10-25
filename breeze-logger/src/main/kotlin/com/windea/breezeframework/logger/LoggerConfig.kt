package com.windea.breezeframework.logger

object LoggerConfig {
	var isLevelIncluded: Boolean = true
	var isDateIncluded: Boolean = true
	var isPathIncluded: Boolean = true
	var isPathAbbreviated: Boolean = false
	var isColorful: Boolean = true
	
	var minLogLevel: LogLevel = LogLevel.Trace
	var dateFormat: String = "yyyy-MM-dd HH:mm:ss"
	var delimiter: String = " - "
}
