package com.windea.breezeframework.core.annotations

import kotlin.annotation.AnnotationRetention.*

/**需要显式使用的api。*/
@MustBeDocumented
@Retention(BINARY)
annotation class ExplicitUsageApi(
	/**备注信息。*/
	val message: String = ""
)

/**可能引起无限循环的api。*/
@MustBeDocumented
@Retention(BINARY)
annotation class InfiniteLoopApi(
	/**备注信息。*/
	val message: String = ""
)

/**可能执行低性能操作的api。*/
@MustBeDocumented
@Retention(BINARY)
annotation class LowPerformanceApi(
	/**备注信息。*/
	val message: String = ""
)

/**难以或根本不可能实现的api。*/
@MustBeDocumented
@Retention(BINARY)
annotation class TrickImplementationApi(
	/**备注信息。*/
	val message: String = ""
)
