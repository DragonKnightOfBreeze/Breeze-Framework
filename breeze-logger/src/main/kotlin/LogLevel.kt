// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.logger

/**日志等级。*/
enum class LogLevel(
	val text: String,
) {
	Off("OFF"),
	Trace("TRACE"),
	Debug("DEBUG"),
	Info("INFO"),
	Warn("WARN"),
	Error("ERROR"),
	Fatal("FATAL")
}
