package com.windea.breezeframework.logger

import com.windea.breezeframework.logger.impl.*
import kotlin.test.*

val logger = ColorfulLogger()

class Tests {
	@Test
	fun test() {
		logger.config.minLogLevel = LogLevel.Trace
		logger.config.outputPath = "src\\test\\resources\\log.txt"

		logger.trace("trace")
		logger.debug("debug")
		logger.info("info")
		logger.warn("warn")
		logger.error("error")
		logger.fatal("fatal")
	}
}
