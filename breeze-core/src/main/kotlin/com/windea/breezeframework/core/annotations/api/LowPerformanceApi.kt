package com.windea.breezeframework.core.annotations.api

import kotlin.annotation.AnnotationRetention.*

/**可能执行低性能操作的api。*/
@MustBeDocumented
@Retention(BINARY)
annotation class LowPerformanceApi(
	/**备注信息。*/
	val message: String = ""
)
