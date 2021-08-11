// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("DataExtensions")

package icu.windea.breezeframework.springboot.extension

import org.springframework.data.domain.*

/**将当前列表转化为分页。*/
fun <T> List<T>.toPage(): Page<T> = PageImpl<T>(this)

/**将当前列表转化为分页。*/
fun <T> List<T>.toPage(pageable: Pageable): Page<T> = PageImpl<T>(this, pageable, this.size.toLong())
