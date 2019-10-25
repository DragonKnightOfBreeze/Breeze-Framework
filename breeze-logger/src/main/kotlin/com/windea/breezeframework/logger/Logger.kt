package com.windea.breezeframework.logger

interface Logger {
	val name: String
	
	val isTraceEnabled: Boolean
	val isDebugEnabled: Boolean
	val isInfoEnabled: Boolean
	val isWarnEnabled: Boolean
	val isErrorEnabled: Boolean
	
	val isFatalEnabled: Boolean
	
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
