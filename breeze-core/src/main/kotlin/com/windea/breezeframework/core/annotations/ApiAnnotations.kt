package com.windea.breezeframework.core.annotations

import kotlin.annotation.AnnotationRetention.*

/**应当仅限在框架内部使用的api。*/
@MustBeDocumented
@RequiresOptIn("This api should be used only inside Breeze-Framework itself.", RequiresOptIn.Level.ERROR)
@Retention(BINARY)
annotation class InternalUsageApi(
	/**备注信息。*/
	val message:String = ""
)

/**需要显式使用的api。*/
@MustBeDocumented
@Retention(SOURCE)
annotation class ExplicitUsageApi(
	/**备注信息。*/
	val message:String = ""
)

/**需要选择性使用的api。*/
@MustBeDocumented
@Retention(SOURCE)
annotation class OptionalUsageApi(
	/**备注信息。*/
	val message: String = ""
)

/**难以或根本不可能实现的api。*/
@MustBeDocumented
@Retention(SOURCE)
annotation class TrickImplementationApi(
	/**备注信息。*/
	val message: String = ""
)

///**可能引起无限循环的api。*/
//@MustBeDocumented
//@Retention(SOURCE)
//annotation class InfiniteLoopApi(
//	/**备注信息。*/
//	val message: String = ""
//)
//
///**可能执行低性能操作的api。*/
//@MustBeDocumented
//@Retention(SOURCE)
//annotation class LowPerformanceApi(
//	/**备注信息。*/
//	val message: String = ""
//)
