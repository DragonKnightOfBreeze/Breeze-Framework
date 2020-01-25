package com.windea.breezeframework.core.annotations.api

import kotlin.annotation.AnnotationRetention.*

/**可能引起无限循环的api。*/
@MustBeDocumented
@Retention(BINARY)
annotation class InfiniteLoopApi(
	/**备注信息。*/
	val message: String = ""
)
