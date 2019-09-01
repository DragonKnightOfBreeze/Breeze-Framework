@file:Suppress("FunctionName", "NOTHING_TO_INLINE", "UNUSED_PARAMETER")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.TodoHighlightExtensions.logger
import mu.*

object TodoHighlightExtensions : KLogging()

/**返回一个模拟结果，或者引起一个[DelayImplementedError]，以说明一个方法体推迟了实现。*/
inline fun <reified T> DELAY(lazyDummyResult: () -> T? = { null }): T =
	lazyDummyResult().also {
		logger.warn("An operation is delay-implemented.")
	}
	?: throw DelayImplementedError()

/**返回一个模拟结果，或者引起一个[DelayImplementedError]，以说明一个方法体推迟了实现，并指定原因。*/
inline fun <reified T> DELAY(reason: String, lazyDummyResult: () -> T? = { null }): T =
	lazyDummyResult().also {
		logger.warn("An operation is delay-implemented: $reason")
	} ?: throw DelayImplementedError("An operation is delay-implemented: $reason")

class DelayImplementedError(message: String = "An operation is delay-implemented.") : Error(message)


/**为Kotlin文件中的项提供分组。*/
@NeverFeatureApi
inline fun REGION(regionName: String = "REGION@${RandomExtensions.uuid()}") {
}
