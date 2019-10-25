package com.windea.breezeframework.logger

enum class LogLevel(
	val text: String
) {
	Off("OFF"),
	Trace("TRACE"),
	Debug("DEBUG"),
	Info("INFO"),
	Warn("WARN"),
	Error("ERROR"),
	Fatal("FATAL")
}
