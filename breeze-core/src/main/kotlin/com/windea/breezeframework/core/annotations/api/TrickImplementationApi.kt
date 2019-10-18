package com.windea.breezeframework.core.annotations.api

/**难以或根本不可能实现的api。*/
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class TrickImplementationApi(
	/**备注信息。*/
	val message: String = ""
)
