// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.springboot

import icu.windea.breezeframework.springboot.converter.*
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
