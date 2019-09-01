package com.windea.breezeframework.springboot.core

import com.windea.breezeframework.springboot.core.components.converters.*
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
