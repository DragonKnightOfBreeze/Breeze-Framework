// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.annotation

import kotlin.annotation.AnnotationRetention.*

/**
 * 应当仅在这个框架内部使用的api。
 *
 * Api that should be used only inside this framework.
 */
@MustBeDocumented
@RequiresOptIn("This api should be used only inside Breeze-Framework itself.", RequiresOptIn.Level.ERROR)
@Retention(BINARY)
annotation class InternalApi

/**
 * 应当明确注明使用的api。
 *
 * Api that should be used explicitly。
 */
@MustBeDocumented
@Retention(BINARY)
annotation class ExplicitApi

/**
 * 可以选择性使用的api。
 *
 * Api that may be used optionally.
 */
@MustBeDocumented
@Retention(BINARY)
annotation class OptionalApi

/**
 * 实现方式不稳定的api。这意味着它在未来可能会被重构。
 *
 * Api that is implemented unstably. It may be refactored or removed in the future.
 */
@MustBeDocumented
@RequiresOptIn("This api is unstable and should be used carefully.", RequiresOptIn.Level.WARNING)
@Retention(BINARY)
annotation class UnstableApi

/**
 * 实现方式较为取巧的api。这意味着它几乎不可能被完全实现。
 *
 * Api that is implemented tricky. It it hard to be fully implemented.
 */
@MustBeDocumented
@RequiresOptIn("This api is tricky and should be used carefully.", RequiresOptIn.Level.WARNING)
@Retention(BINARY)
annotation class TrickApi
