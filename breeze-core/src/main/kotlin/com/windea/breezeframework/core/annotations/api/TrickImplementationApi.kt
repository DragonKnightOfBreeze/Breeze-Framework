package com.windea.breezeframework.core.annotations.api

import kotlin.annotation.AnnotationRetention.*

/**难以或根本不可能实现的api。*/
@MustBeDocumented
@Retention(BINARY)
annotation class TrickImplementationApi(
	/**备注信息。*/
	val message: String = ""
)
