// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.logger

/**日志器。*/
interface Logger {
	val config: LoggerConfig

	val isTraceEnabled: Boolean get() = config.minLogLevel <= LogLevel.Trace
	val isDebugEnabled: Boolean get() = config.minLogLevel <= LogLevel.Debug
	val isInfoEnabled: Boolean get() = config.minLogLevel <= LogLevel.Info
	val isWarnEnabled: Boolean get() = config.minLogLevel <= LogLevel.Warn
	val isErrorEnabled: Boolean get() = config.minLogLevel <= LogLevel.Error
	val isFatalEnabled: Boolean get() = config.minLogLevel <= LogLevel.Fatal

	fun trace(message: Any?)

	fun trace(lazyMessage: () -> Any?)

	fun debug(message: Any?)

	fun debug(lazyMessage: () -> Any?)

	fun info(message: Any?)

	fun info(lazyMessage: () -> Any?)

	fun warn(message: Any?)

	fun warn(lazyMessage: () -> Any?)

	fun error(message: Any?)

	fun error(lazyMessage: () -> Any?)

	fun fatal(message: Any?)

	fun fatal(lazyMessage: () -> Any?)
}
