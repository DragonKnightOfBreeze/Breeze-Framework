package com.windea.breezeframework.dsl.creole

import com.windea.breezeframework.dsl.*

/**Creole领域特定语言的入口。*/
@CreoleDsl
interface CreoleDslEntry : DslEntry, UPlus<Creole.TextBlock> {
	val content:MutableList<CreoleDslTopElement>

	override fun contentString() = content.joinToString("\n\n")

	override fun String.unaryPlus() = Creole.TextBlock(this).also { content += it }
}
