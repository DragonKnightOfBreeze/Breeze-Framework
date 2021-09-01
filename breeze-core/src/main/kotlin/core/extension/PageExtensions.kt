// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("PageExtensions")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.model.EmptyPage
import icu.windea.breezeframework.core.model.Page
import icu.windea.breezeframework.core.model.PageImpl

fun <T> emptyPage(): Page<T> {
	return EmptyPage
}

fun <T> List<T>.toPage(pageNumber: Int = 1, pageSize: Int = 10): Page<T> {
	return PageImpl(this, pageNumber, pageSize)
}
