package com.windea.breezeframework.core.annotations.api

/**有望被后续版本的Kotlin标准库，或者指定框架实现的api。*/
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class OutlookImplementationApi(
	/**备注信息。*/
	val value: String = ""
)
