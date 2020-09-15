/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.generator.text

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.generator.*

/**地址生成器。*/
object UrlGenerator : Generator {
	var preferDoubleQuote = true

	val quote get() = if(preferDoubleQuote) '"' else '\''

	/**根据指定参数生成Html链接。*/
	fun generateHtmlUrl(name: String, url: String, title: String? = null): String {
		val titleSnippet = title?.let { " title=${title.quote(quote)}" }.orEmpty()
		return "<a href=$quote$url$quote$titleSnippet>$name</a>"
	}

	/**根据指定参数生成Markdown链接。*/
	fun generateMdUrl(name: String, url: String, title: String? = null): String {
		val titleSnippet = title?.let { " ${title.quote(quote)}" }.orEmpty()
		return "[$name]($url$titleSnippet)"
	}

	/**根据指定参数生成Markdown引用链接。*/
	fun generateMdRefUrl(id: String, name: String, url: String, title: String? = null): Pair<String, String> {
		val titleSnippet = title?.let { " ${title.quote(quote)}" }.orEmpty()
		return "[$name][$id]" to "[$id]: $url$titleSnippet"
	}

	/**根据指定参数生成Markdown图片链接。*/
	fun generateMdImgUrl(name: String, url: String, title: String? = null): String {
		return generateMdUrl(name, url, title).let { "!$it" }
	}

	/**根据指定参数生成Markdown图片引用链接。*/
	fun generateMdImgRefUrl(id: String, name: String, url: String, title: String? = null): Pair<String, String> {
		return generateMdRefUrl(id, name, url, title).let { (a, b) -> "!$a" to b }
	}
}
