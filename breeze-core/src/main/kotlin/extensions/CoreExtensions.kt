@file:JvmName("CoreExtensions")
@file:Suppress("NOTHING_TO_INLINE", "FunctionName", "UseWithIndex")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.*
import java.lang.reflect.*
import kotlin.contracts.*
import kotlin.reflect.*

//region common extensions
/**得到指定类型的带有泛型参数信息的Java类型对象。*/
inline fun <reified T> javaTypeOf(): Type = object : TypeReference<T>() {}.type

/**类型引用。*/
@PublishedApi
internal abstract class TypeReference<T> {
	val type: Type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
}


/**得到指定类型的名字。*/
@TrickImplementationApi
inline fun <reified T> nameOf(): String? = T::class.java.simpleName

/**得到指定项的名字。适用于：类引用、属性引用、方法引用、实例。不适用于：类型参数，参数，局部变量。*/
@TrickImplementationApi
@JvmSynthetic
inline fun nameOf(target: Any?): String? = when {
	//无法直接通过方法的引用得到参数，也无法得到局部变量的任何信息
	target == null -> null
	target is Class<*> -> target.simpleName
	target is KClass<*> -> target.simpleName
	target is KCallable<*> -> target.name
	target is KParameter -> target.name
	else -> target::class.java.simpleName
}
//endregion

//region Standard.kt extensions
/**方法推迟实现时使用到的错误。这个错误不会在对应的TODO方法中被抛出。*/
class DelayImplementedError(message: String = "An operation is delay implemented.") : Error(message)

/**表明一个操作推迟了实现。*/
@TodoMarker
@JvmSynthetic
inline fun DELAY() {
	printTodo(DelayImplementedError(), 32)
}

/**表明一个方法体推迟了实现，并指定原因。*/
@TodoMarker
@JvmSynthetic
inline fun DELAY(reason: String) {
	printTodo(DelayImplementedError("An operation is delay implemented: $reason"), 32)
}

/**表明一个操作推迟了实现。返回模拟结果。*/
@TodoMarker
@JvmSynthetic
inline fun <T> DELAY(lazyDummyResult: () -> T): T {
	printTodo(DelayImplementedError(), 32)
	return lazyDummyResult()
}

/**表明一个方法体推迟了实现，并指定原因。返回模拟结果。*/
@TodoMarker
@JvmSynthetic
inline fun <T> DELAY(reason: String, lazyDummyResult: () -> T): T {
	printTodo(DelayImplementedError("An operation is delay implemented: $reason"), 32)
	return lazyDummyResult()
}


/**方法存在问题时使用到的错误。这个错误不会在对应的TODO方法中被抛出。*/
class ImplementedWithAnIssueError(message: String = "An operation is implemented with an issue.") : Error(message)

/**表明一个方法体中存在问题。*/
@TodoMarker
@JvmSynthetic
inline fun FIXME() {
	printTodo(ImplementedWithAnIssueError(), 33)
}

/**表明一个方法体中存在问题，并指明原因。*/
@TodoMarker
@JvmSynthetic
inline fun FIXME(message: String) {
	printTodo(ImplementedWithAnIssueError("An operation is implemented with an issue: $message"), 33)
}


@PublishedApi
internal fun printTodo(throwable: Throwable, colorCode: Int) {
	println()
	throwable.message?.let { println("\u001B[${colorCode}m$it\u001B[0m") }
	throwable.printStackTrace()
}


/**
 * 如果条件满足，则执行一段代码并返回处理后的结果，否则返回自身。
 *
 * 这个作用域方法用于执行一段当前值需要根据条件转化的代码，将条件判断加入方法的链式调用。
 */
@JvmSynthetic
inline fun <T : R, R> T.where(condition:Boolean, block:(T) -> R):R {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	return if(condition) block(this) else this
}

/**
 * 尝试执行一段代码，并在发生异常时打印错误信息。
 *
 * 这个作用域方法用于执行一段可能会抛出异常的代码，当捕获到异常时打印错误信息，并且没有返回值。
 */
@JvmSynthetic
inline fun tryOrPrint(block: () -> Unit) {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	try {
		block()
	} catch(e: Exception) {
		e.printStackTrace()
	}
}

/**
 * 尝试执行一段代码，并忽略异常。
 *
 * 这个作用域方法用于执行一段可能会抛出异常的代码，当捕获到异常时不做任何处理，并且没有返回值。
 */
@JvmSynthetic
inline fun tryOrIgnore(block: () -> Unit) {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	try {
		block()
	} catch(e: Exception) {
	}
}

/**
 * 执行一段代码且仅执行一次。默认不重置单次状态。
 *
 * 这个作用域方法用于方便地执行那些仅需执行一次的方法。
 */
@JvmSynthetic
inline fun once(resetStatus: Boolean = false, block: () -> Unit) {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	if(resetStatus) enableOnce = false
	if(enableOnce) return
	enableOnce = true
	block()
}

@PublishedApi internal var enableOnce = false
//endregion

//region Precondition.kt extensions
/**如果判定失败，则抛出一个[UnsupportedOperationException]。*/
@JvmSynthetic
inline fun accept(value: Boolean) {
	contract {
		returns() implies value
	}
	accept(value) { "Unsupported operation." }
}

/**如果判定失败，则抛出一个[UnsupportedOperationException]，带有懒加载的信息。*/
@JvmSynthetic
inline fun accept(value: Boolean, lazyMessage: () -> Any) {
	contract {
		returns() implies value
	}
	if(!value) {
		val message = lazyMessage()
		throw UnsupportedOperationException(message.toString())
	}
}

/**如果判定失败，则抛出一个[UnsupportedOperationException]。*/
@JvmSynthetic
inline fun <T> acceptNotNull(value: T?) {
	contract {
		returns() implies (value != null)
	}
	acceptNotNull(value) { "Unsupported operation." }
}

/**如果判定失败，则抛出一个[UnsupportedOperationException]，带有懒加载的信息。*/
@JvmSynthetic
inline fun <T> acceptNotNull(value: T?, lazyMessage: () -> Any): T {
	contract {
		returns() implies (value != null)
	}
	if(value == null) {
		val message = lazyMessage()
		throw UnsupportedOperationException(message.toString())
	} else {
		return value
	}
}


/**判断指定名字的Class是否出现在classpath中并且可加载。*/
fun presentInClassPath(className: String): Boolean {
	return runCatching { Class.forName(className) }.isSuccess
}
//endregion
