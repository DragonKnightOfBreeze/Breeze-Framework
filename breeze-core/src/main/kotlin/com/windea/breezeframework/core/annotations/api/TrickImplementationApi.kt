package com.windea.breezeframework.core.annotations.api

/**难以或根本不可能实现的api。限于内部实现、访问限制、语法等。*/
@MustBeDocumented
annotation class TrickImplementationApi(
	val value: String = "Can never implemented."
)