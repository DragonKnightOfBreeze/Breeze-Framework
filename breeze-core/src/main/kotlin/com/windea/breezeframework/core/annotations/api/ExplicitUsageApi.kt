package com.windea.breezeframework.core.annotations.api

/**需要显式使用的api。*/
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class ExplicitUsageApi(
	/**备注信息。*/
	val message: String = ""
)
