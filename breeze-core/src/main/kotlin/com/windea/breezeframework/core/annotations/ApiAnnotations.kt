package com.windea.breezeframework.core.annotations

import kotlin.annotation.AnnotationRetention.*

/**Apis that should be used only inside this framework.*/
@MustBeDocumented
@RequiresOptIn("This api should be used only inside Breeze-Framework itself.", RequiresOptIn.Level.ERROR)
@Retention(BINARY)
annotation class InternalUsageApi

/**Apis that should be used explicitly.*/
@MustBeDocumented
@Retention(BINARY)
annotation class ExplicitUsageApi

/**Apis that should be used optionally.*/
@MustBeDocumented
@Retention(BINARY)
annotation class OptionalUsageApi

/**Apis that is implemented tricky, or cannot be fully implemented.*/
@MustBeDocumented
@Retention(BINARY)
annotation class TrickImplementationApi(
	val message: String = ""
)
