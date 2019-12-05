package com.windea.breezeframework.logger

/**日志器。*/
interface Logger {
	val isTraceEnabled: Boolean get() = LoggerConfig.minLogLevel <= LogLevel.Trace
	val isDebugEnabled: Boolean get() = LoggerConfig.minLogLevel <= LogLevel.Debug
	val isInfoEnabled: Boolean get() = LoggerConfig.minLogLevel <= LogLevel.Info
	val isWarnEnabled: Boolean get() = LoggerConfig.minLogLevel <= LogLevel.Warn
	val isErrorEnabled: Boolean get() = LoggerConfig.minLogLevel <= LogLevel.Error
	val isFatalEnabled: Boolean get() = LoggerConfig.minLogLevel <= LogLevel.Fatal
	
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
