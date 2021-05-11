// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.springboot

import icu.windea.breezeframework.springboot.converter.*
import org.springframework.context.annotation.*

@Configuration
class CoreAutoConfiguration {
	@Bean
	fun stringToDoubleRangeConverter() = StringToRangeConverters()

	@Bean
	fun stringToFloatRangeConverter() = StringToFloatRangeConverter()

	@Bean
	fun stringToIntRangeConverter() = StringToIntRangeConverter()

	@Bean
	fun stringToLongRangeConverter() = StringToLongRangeConverter()
}
