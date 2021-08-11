// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.annotation

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**
 * 应当仅在这个框架内部使用的api。
 *
 * Api that should be used only inside this framework.
 */
@MustBeDocumented
@RequiresOptIn("This api should be used only inside Breeze-Framework itself.", RequiresOptIn.Level.ERROR)
@Target(CLASS, PROPERTY, FIELD, LOCAL_VARIABLE, VALUE_PARAMETER, CONSTRUCTOR, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER, TYPEALIAS)
@Retention(BINARY)
annotation class InternalApi

/**
 * 实现方式不稳定的api。这意味着它在未来可能会被重构。
 *
 * Api that is implemented unstably. It may be refactored or removed in the future.
 */
@MustBeDocumented
@RequiresOptIn("This api is unstable and should be used carefully.", RequiresOptIn.Level.WARNING)
@Target(CLASS, PROPERTY, FIELD, LOCAL_VARIABLE, VALUE_PARAMETER, CONSTRUCTOR, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER, TYPEALIAS)
@Retention(BINARY)
annotation class UnstableApi

/**
 * 实现方式较为取巧的api。这意味着它几乎不可能被完全实现。
 *
 * Api that is implemented tricky. It it hard to be fully implemented.
 */
@MustBeDocumented
@RequiresOptIn("This api is tricky and should be used carefully.", RequiresOptIn.Level.WARNING)
@Target(CLASS, PROPERTY, FIELD, LOCAL_VARIABLE, VALUE_PARAMETER, CONSTRUCTOR, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER, TYPEALIAS)
@Retention(BINARY)
annotation class TrickApi

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
