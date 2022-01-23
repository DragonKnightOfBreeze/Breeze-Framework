// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.annotation

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**
 * 注明这个api是内部的。这意味着它应当仅在框架内部使用。
 *
 * Specifies that this api is internal. Means that it should be used only inside this framework.
 */
@MustBeDocumented
@RequiresOptIn("This api is internal.", RequiresOptIn.Level.ERROR)
@Target(CLASS, PROPERTY, FIELD, LOCAL_VARIABLE, VALUE_PARAMETER, CONSTRUCTOR, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER, TYPEALIAS)
@Retention(BINARY)
annotation class InternalApi

/**
 * 注明这个api是不稳定的。这意味着它在未来可能会被重构或移除。
 *
 * Specifies that this api is unstable. Means that it may be refactored or removed in the future.
 */
@MustBeDocumented
@RequiresOptIn("This api is unstable.", RequiresOptIn.Level.WARNING)
@Target(CLASS, PROPERTY, FIELD, LOCAL_VARIABLE, VALUE_PARAMETER, CONSTRUCTOR, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER, TYPEALIAS)
@Retention(BINARY)
annotation class UnstableApi

/**
 * 注明这个api是取巧的。这意味着它难以被完全实现。
 *
 * Specifies that this api is tricky. Means that it it hard to be fully implemented.
 */
@MustBeDocumented
@RequiresOptIn("This api is tricky.", RequiresOptIn.Level.WARNING)
@Target(CLASS, PROPERTY, FIELD, LOCAL_VARIABLE, VALUE_PARAMETER, CONSTRUCTOR, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER, TYPEALIAS)
@Retention(BINARY)
annotation class TrickyApi
