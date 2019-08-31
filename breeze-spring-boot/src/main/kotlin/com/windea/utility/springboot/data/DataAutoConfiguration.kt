package com.windea.utility.springboot.data

import com.windea.utility.springboot.data.components.converters.*
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
