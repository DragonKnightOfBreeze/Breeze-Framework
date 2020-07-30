package com.windea.breezeframework.core.annotations

import kotlin.annotation.AnnotationRetention.*

/**Api that should be used only inside this framework.*/
@MustBeDocumented
@RequiresOptIn("This api should be used only inside Breeze-Framework itself.", RequiresOptIn.Level.ERROR)
@Retention(BINARY)
annotation class InternalUsageApi

/**Api that should be used explicitly.*/
@MustBeDocumented
@Retention(BINARY)
annotation class ExplicitUsageApi

/**Api that should be used optionally.*/
@MustBeDocumented
@Retention(BINARY)
annotation class OptionalUsageApi

/**Api that is implemented unstably. It means, this api may be refactored or removed in the future.*/
@MustBeDocumented
@RequiresOptIn("This api is unstable and should be used carefully.", RequiresOptIn.Level.WARNING)
@Retention(BINARY)
annotation class UnstableImplementationApi

/**Api that is implemented tricky. It means, this api cannot be fully implemented.*/
@MustBeDocumented
@RequiresOptIn("This api is unstable and should be used carefully.", RequiresOptIn.Level.WARNING)
@Retention(BINARY)
annotation class TrickImplementationApi
