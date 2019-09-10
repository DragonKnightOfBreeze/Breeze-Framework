package com.windea.breezeframework.core.annotations.api

/**稳定的api。*/
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class StableApi(
	/**备注信息。*/
	val value: String = ""
)
