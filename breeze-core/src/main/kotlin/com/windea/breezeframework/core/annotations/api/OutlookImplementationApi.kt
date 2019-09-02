package com.windea.breezeframework.core.annotations.api

/**可能被后续版本的Kotlin标准库，或者指定框架实现的api。*/
@MustBeDocumented
annotation class OutlookImplementationApi(
	val value: String = "Stdlib"
)
