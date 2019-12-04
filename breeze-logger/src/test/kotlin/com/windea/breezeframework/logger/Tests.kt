package com.windea.breezeframework.logger

import com.windea.breezeframework.logger.internal.*
import mu.*
import kotlin.test.*

val logger = ColorfulLogger
val loggerConfig = LoggerConfig

class Tests {
	@Test
	fun test() {
		loggerConfig.minLogLevel = LogLevel.Trace
		loggerConfig.outputPath = "src\\test\\resources\\log.txt"
		
		logger.trace("trace")
		logger.debug("debug")
		logger.info("info")
		logger.warn("warn")
		logger.error("error")
		logger.fatal("fatal")
	}
	
	@Test
	fun test2() {
		SimpleLogger.info("INFO")
		SimpleLogger.warn("WARN")
		SimpleLogger.error("ERROR")
	}
	
	@Test
	fun test3() {
		val kLogger = KotlinLogging.logger { }
		kLogger.trace("trace")
		kLogger.debug("debug")
		kLogger.info("info")
		kLogger.warn("warn")
		kLogger.error("error")
	}
}
