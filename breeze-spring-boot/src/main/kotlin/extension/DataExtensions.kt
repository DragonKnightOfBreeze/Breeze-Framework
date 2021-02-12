// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("DataExtensions")

package com.windea.breezeframework.springboot.extension

import org.springframework.data.domain.*
import org.springframework.data.repository.query.*

/**将当前列表转化为分页。*/
fun <T> List<T>.toPage(): Page<T> = PageImpl<T>(this)

/**将当前列表转化为分页。*/
fun <T> List<T>.toPage(pageable: Pageable): Page<T> = PageImpl<T>(this, pageable, this.size.toLong())


/**根据Example查询一个结果，返回一个可空对象。*/
fun <T> QueryByExampleExecutor<T>.findOneOrNull(example: Example<T>): T? = this.findOne(example).orElse(null)
