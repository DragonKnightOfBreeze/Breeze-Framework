package com.windea.utility.springboot.core

import com.windea.utility.springboot.core.components.converters.*
import org.springframework.context.annotation.*

@Configuration
class CoreAutoConfiguration {
	@Bean
	fun stringToDoubleRangeConverter() = StringToDoubleRangeConverter()
	
	@Bean
	fun stringToFloatRangeConverter() = StringToFloatRangeConverter()
	
	@Bean
	fun stringToIntRangeConverter() = StringToIntRangeConverter()
	
	@Bean
	fun stringToLongRangeConverter() = StringToLongRangeConverter()
}
