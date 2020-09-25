/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.springboot.data

import com.windea.breezeframework.springboot.data.converters.*
import org.springframework.boot.autoconfigure.condition.*
import org.springframework.context.annotation.*
import org.springframework.data.domain.*

@Configuration
@ConditionalOnClass(Pageable::class, Sort::class)
class DataAutoConfiguration {
	@Bean
	fun stringToPageableConverter() = StringToPageableConverter(stringToSortConverter())

	@Bean
	fun stringToSortConverter() = StringToSortConverter()
}
