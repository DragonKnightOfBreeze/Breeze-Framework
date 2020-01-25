package com.windea.breezeframework.core.annotations.api

import kotlin.annotation.AnnotationRetention.*

/**需要显式使用的api。*/
@MustBeDocumented
@Retention(BINARY)
annotation class ExplicitUsageApi(
	/**备注信息。*/
	val message: String = ""
)
