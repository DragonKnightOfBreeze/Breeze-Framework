package com.windea.breezeframework.data.generators.string

import com.windea.breezeframework.data.generators.*

/**地址生成器。*/
object UrlGenerator : Generator {
	/**根据指定参数生成Html链接。*/
	fun generateHtmlUrl(name: String, url: String, title: String? = null, doubleQuote: Boolean = true): String {
		val quote = if(doubleQuote) "\"" else "'"
		val titleSnippet = title?.let { " title=$quote$title$quote" } ?: ""
		return "<a href=$quote$url$quote$titleSnippet>$name</a>"
	}
	
	
	/**根据指定参数生成Markdown链接。*/
	fun generateMdUrl(name: String, url: String, title: String? = null, doubleQuote: Boolean = true): String {
		val quote = if(doubleQuote) "\"" else "'"
		val titleSnippet = title?.let { " $quote$title$quote" } ?: ""
		return "[$name]($url$titleSnippet)"
	}
	
	/**根据指定参数生成Markdown图片链接。*/
	fun generateMdImgUrl(name: String, url: String, title: String? = null, doubleQuote: Boolean = true): String {
		val quote = if(doubleQuote) "\"" else "'"
		val titleSnippet = title?.let { " $quote$title$quote" } ?: ""
		return "![$name]($url$titleSnippet)"
	}
	
	/**根据指定参数生成Markdown引用链接。*/
	fun generateMdRefUrl(id: String, name: String, url: String, title: String? = null, doubleQuote: Boolean = true): Pair<String, String> {
		val quote = if(doubleQuote) "\"" else "'"
		val titleSnippet = title?.let { " $quote$title$quote" } ?: ""
		return "[$name][$id]" to "[$id]: $url$titleSnippet"
	}
	
	/**根据指定参数生成Markdown图片引用链接。*/
	fun generateMdImgRefUrl(id: String, name: String, url: String, title: String? = null, doubleQuote: Boolean = true): Pair<String, String> {
		val quote = if(doubleQuote) "\"" else "'"
		val titleSnippet = title?.let { " $quote$title$quote" } ?: ""
		return "![$name][$id]" to "[$id]: $url$titleSnippet"
	}
}
