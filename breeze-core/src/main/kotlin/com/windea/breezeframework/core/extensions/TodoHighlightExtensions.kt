@file:Suppress("FunctionName", "NOTHING_TO_INLINE", "UNUSED_PARAMETER")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.api.*

//DONE 追踪目标方法体
//TODO 让TODO插件能够正确地提供高亮

/**表明一个方法体推迟了实现。*/
@OutlookImplementationApi
inline fun DELAY() = Unit

/**返回一个模拟结果，以表明一个方法体推迟了实现。*/
@OutlookImplementationApi
inline fun <reified T> DELAY(lazyDummyResult: () -> T): T = lazyDummyResult()
	.also { nearestLogger().warn("An operation is delay-implemented.") }

/**返回一个模拟结果，以表明一个方法体推迟了实现，并指定原因。*/
@OutlookImplementationApi
inline fun <reified T> DELAY(reason: String, lazyDummyResult: () -> T): T = lazyDummyResult()
	.also { nearestLogger().warn("An operation is delay-implemented: $reason") }


/**打印一段消息，以表明一个方法体中存在问题。*/
@OutlookImplementationApi
inline fun FIXME() = run { nearestLogger().warn("There is an issue in this operation.") }

/**打印一段消息，以表明一个方法体中存在问题。*/
@OutlookImplementationApi
inline fun FIXME(message: String) = run { nearestLogger().warn("There is an issue in this operation :$message") }


/**为Kotlin文件中的项提供分组。*/
@TrickImplementationApi
inline fun REGION(regionName: String = "REGION@${Randoms.uuid()}") = Unit
