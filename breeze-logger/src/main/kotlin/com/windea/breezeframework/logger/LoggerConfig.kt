package com.windea.breezeframework.logger

/**日志器的配置。*/
data class LoggerConfig(
	var isLevelIncluded: Boolean = true,
	var isDateIncluded: Boolean = true,
	var isPathIncluded: Boolean = true,
	var isPathAbbreviated: Boolean = false,
	var isColorful: Boolean = true,
	var minLogLevel: LogLevel = LogLevel.Info,
	var dateFormat: String = "yyyy-MM-dd HH:mm:ss",
	var delimiter: String = " - ",
	var outputPath: String? = null
)
