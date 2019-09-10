package com.windea.breezeframework.core.annotations.api

/**可能执行低性能操作的api。*/
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class LowPerformanceApi(
	/**备注信息。*/
	val value: String = ""
)
