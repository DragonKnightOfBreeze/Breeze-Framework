package com.windea.breezeframework.dsl

import com.windea.breezeframework.core.annotations.*

/**领域特定语言的文档。*/
interface DslDocument {
	override fun toString():String
}

/**领域特定语言的入口。*/
interface DslEntry {
	@InternalUsageApi
	fun toContentString():String = ""
}

/**领域特定语言的元素。*/
interface DslElement {
	override fun toString():String
}

/**领域特定语言的常量集。*/
object DslConstants{
	@InternalUsageApi val ls:String = System.lineSeparator()
	@InternalUsageApi val ss:String = "$ls$ls"
}
