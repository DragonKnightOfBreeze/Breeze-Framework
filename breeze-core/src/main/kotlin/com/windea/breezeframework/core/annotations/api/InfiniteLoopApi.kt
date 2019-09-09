package com.windea.breezeframework.core.annotations.api

/**可能引起无限循环的api。*/
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class InfiniteLoopApi(
	/**备注信息。*/
	val value: String = ""
)
