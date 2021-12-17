// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.linq

import icu.windea.breezeframework.linq.impl.*

/**创建Linq语句。可选Linq的实现类型，默认为委托给Kotlin集合框架实现。*/
@JvmOverloads
fun <T> from(type: LinqType = LinqType.Default): Linq<T, T> = when(type) {
	LinqType.Default -> KotlinLinq.init()
	LinqType.ByStream -> StreamLinq.init()
	LinqType.ByParallelStream -> StreamLinq.init(true)
}


/**对当前字符串进行语言集成查询。*/
infix fun <R> String.linq(linqStatement: Linq<Char, R>): List<R> = linqStatement(this.toList())

/**对当前数组进行语言集成查询。*/
infix fun <T, R> Array<out T>.linq(linqStatement: Linq<T, R>): List<R> = linqStatement(this.toList())

/**对当前集合进行语言集成查询。*/
infix fun <T, R> Collection<T>.linq(linqStatement: Linq<T, R>): List<R> = linqStatement(this)

/**对当前映射进行语言集成查询。*/
infix fun <K, V, R> Map<K, V>.linq(linqStatement: Linq<Pair<K, V>, R>): List<R> = linqStatement(this.toList())
