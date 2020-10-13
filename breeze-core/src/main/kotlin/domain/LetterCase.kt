// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("ObjectPropertyName")

package com.windea.breezeframework.core.domain

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*

/**
 * 单词格式。
 *
 * 单词格式用于以某种大小写和分割方式，表示包含一个或多个单词的名字。
 */
@ComponentMarker
interface LetterCase {
	/**
	 * 用于进行推断的正则表达式。
	 */
	@NotOptimized
	val regex: Regex

	/**
	 * 基于单词格式，分割字符串。
	 */
	fun split(value: String): List<String>

	/**
	 * 基于单词格式，分割字符串。
	 */
	fun splitToSequence(value: String): Sequence<String>

	/**
	 * 基于单词格式，拼接字符串。
	 */
	fun joinToString(value: Array<String>): String

	/**
	 * 基于单词格式，拼接字符串。
	 */
	fun joinToString(value: Iterable<String>): String

	/**
	 * 基于单词格式，拼接字符串。
	 */
	fun joinToString(value: Sequence<String>): String

	companion object {
		/**
		 * 注册单词格式。
		 */
		@JvmStatic fun register(letterCase: LetterCase) {
			letterCaseRegistry += letterCase
		}

		/**
		 * 推断单词格式。
		 */
		@JvmStatic fun infer(value: String): LetterCase? {
			for(letterCase in letterCaseRegistry) {
				if(letterCase.regex.matches(value)) return letterCase
			}
			return null
		}

		private fun CharSequence.firstCharToUpperCase(): String {
			return when {
				isEmpty() -> this.toString()
				else -> this[0].toUpperCase() + this.substring(1)
			}
		}

		private fun CharSequence.firstCharToLowerCase(): String {
			return when {
				isEmpty() -> this.toString()
				else -> this[0].toLowerCase() + this.substring(1)
			}
		}

		private val splitWordsRegex = """\B([A-Z][a-z])""".toRegex()

		private fun CharSequence.splitWords(): String {
			return this.replace(splitWordsRegex, " $1")
		}


		private val letterCaseRegistry = mutableListOf<LetterCase>()

		init {
			registerDefaultLetterCases()
			registerPathLikeLetterCases()
		}

		private fun registerDefaultLetterCases() {
			register(LowerCase)
			register(UpperCase)
			register(Capitalized)
			register(LowerCaseWords)
			register(UpperCaseWords)
			register(FirstWordCapitalized)
			register(CapitalizedWords)
			register(Words)

			register(CamelCase)
			register(PascalCase)
			register(SnakeCase)
			register(ScreamingSnakeCase)
			register(UnderscoreWords)
			register(KebabCase)
			register(KebabUpperCase)
			register(HyphenWords)
		}

		private fun registerPathLikeLetterCases(){
			register(ReferencePath)
			register(LinuxPath)
			register(WindowsPath)
		}
	}

	//region Default letter cases
	/**
	 * 全小写。
	 *
	 * 示例：`lowercase`
	 */
	object LowerCase : LetterCase {
		val a = listOf(1,2,3)
		override val regex = """[a-z]+""".toRegex()
		override fun split(value: String) = listOf(value)
		override fun splitToSequence(value: String) = sequenceOf(value)
		override fun joinToString(value: Array<String>) = value.joinToString("").toLowerCase()
		override fun joinToString(value: Iterable<String>) = value.joinToString("").toLowerCase()
		override fun joinToString(value: Sequence<String>) = value.joinToString("").toLowerCase()
	}

	/**
	 * 全大写。
	 *
	 * 示例：`UPPERCASE`
	 */
	object UpperCase : LetterCase {
		override val regex = """[A-Z]+""".toRegex()
		override fun split(value: String) = listOf(value)
		override fun splitToSequence(value: String) = sequenceOf(value)
		override fun joinToString(value: Array<String>) = value.joinToString("").toUpperCase()
		override fun joinToString(value: Iterable<String>) = value.joinToString("").toUpperCase()
		override fun joinToString(value: Sequence<String>) = value.joinToString("").toUpperCase()
	}

	/**
	 * 首字母大写。
	 *
	 * 示例：`Capitalized`
	 */
	object Capitalized : LetterCase {
		override val regex = """[A-Z][a-z]+""".toRegex()
		override fun split(value: String) = listOf(value)
		override fun splitToSequence(value: String) = sequenceOf(value)
		override fun joinToString(value: Array<String>) = value.joinToString("").firstCharToUpperCase()
		override fun joinToString(value: Iterable<String>) = value.joinToString("").firstCharToUpperCase()
		override fun joinToString(value: Sequence<String>) = value.joinToString("").firstCharToUpperCase()
	}

	/**
	 * 全小写的单词组。
	 *
	 * 示例：`lowercase words`
	 */
	object LowerCaseWords : LetterCase {
		override val regex = """[a-z']+(?:\s+[a-z']+)+""".toRegex()
		override fun split(value: String) = value.split(' ').filterNotEmpty()
		override fun splitToSequence(value: String) = value.splitToSequence(' ').filterNotEmpty()
		override fun joinToString(value: Array<String>) = value.joinToString(" ").toLowerCase()
		override fun joinToString(value: Iterable<String>) = value.joinToString(" ").toLowerCase()
		override fun joinToString(value: Sequence<String>) = value.joinToString(" ").toLowerCase()
	}

	/**
	 * 全大写的单词组。
	 *
	 * 示例：`UPPERCASE WORDS`
	 */
	object UpperCaseWords : LetterCase {
		override val regex = """[A-Z']+(?:\s+[A-Z']+)+""".toRegex()
		override fun split(value: String) = value.split(' ').filterNotEmpty()
		override fun splitToSequence(value: String) = value.splitToSequence(' ').filterNotEmpty()
		override fun joinToString(value: Array<String>) = value.joinToString(" ").toUpperCase()
		override fun joinToString(value: Iterable<String>) = value.joinToString(" ").toUpperCase()
		override fun joinToString(value: Sequence<String>) = value.joinToString(" ").toUpperCase()
	}

	/**
	 * 首个单词的首字母大写的单词组。
	 *
	 * 示例：`Uppercase words`
	 */
	object FirstWordCapitalized : LetterCase {
		override val regex = """[A-Z][a-z']*(?:\s+[a-z']+)+""".toRegex()
		override fun split(value: String) = value.split(' ').filterNotEmpty()
		override fun splitToSequence(value: String) = value.splitToSequence(' ').filterNotEmpty()
		override fun joinToString(value: Array<String>) = value.joinToString(" ").firstCharToUpperCase()
		override fun joinToString(value: Iterable<String>) = value.joinToString(" ").firstCharToUpperCase()
		override fun joinToString(value: Sequence<String>) = value.joinToString(" ").firstCharToUpperCase()
	}

	/**
	 * 首字母大写的单词组。
	 *
	 * 示例：`Uppercase Words`
	 */
	object CapitalizedWords : LetterCase {
		override val regex = """[A-Z][a-z']*(?:\s+[a-z']+)+""".toRegex()
		override fun split(value: String) = value.split(' ').filterNotEmpty()
		override fun splitToSequence(value: String) = value.splitToSequence(' ').filterNotEmpty()
		override fun joinToString(value: Array<String>) = value.joinToString(" ") { it.firstCharToUpperCase() }
		override fun joinToString(value: Iterable<String>) = value.joinToString(" ") { it.firstCharToUpperCase() }
		override fun joinToString(value: Sequence<String>) = value.joinToString(" ") { it.firstCharToUpperCase() }
	}

	/**
	 * 单词组。
	 *
	 * 示例：`Words words Words`
	 */
	object Words : LetterCase {
		override val regex = """[a-zA-Z']+(?:\s+[a-zA-Z']+)+""".toRegex()
		override fun split(value: String) = value.split(' ').filterNotEmpty()
		override fun splitToSequence(value: String) = value.splitToSequence(' ').filterNotEmpty()
		override fun joinToString(value: Array<String>) = value.joinToString(" ")
		override fun joinToString(value: Iterable<String>) = value.joinToString(" ")
		override fun joinToString(value: Sequence<String>) = value.joinToString(" ")
	}

	/**
	 * 以单词边界分隔，首个单词全部小写，后续单词首字母大写。
	 *
	 * 示例：`camelCase`
	 */
	object CamelCase : LetterCase {
		override val regex = """\$?[a-z]+(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+)+""".toRegex()
		override fun split(value: String) = value.splitWords().split(' ')
		override fun splitToSequence(value: String) = value.splitWords().splitToSequence(' ')
		override fun joinToString(value: Array<String>) = value.joinToString("") { it.firstCharToUpperCase() }.firstCharToLowerCase()
		override fun joinToString(value: Iterable<String>) = value.joinToString("") { it.firstCharToUpperCase() }.firstCharToLowerCase()
		override fun joinToString(value: Sequence<String>) = value.joinToString("") { it.firstCharToUpperCase() }.firstCharToLowerCase()
	}

	/**
	 * 以单词边界分隔，所有单词首字母大写。
	 *
	 * 示例：`PascalCase`
	 */
	object PascalCase : LetterCase {
		override val regex = """\$?(?:[A-Z][a-z]+|[A-Z]+)(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+)+""".toRegex()
		override fun split(value: String) = value.splitWords().split(' ')
		override fun splitToSequence(value: String) = value.splitWords().splitToSequence(' ')
		override fun joinToString(value: Array<String>) = value.joinToString("") { it.firstCharToUpperCase() }
		override fun joinToString(value: Iterable<String>) = value.joinToString("") { it.firstCharToUpperCase() }
		override fun joinToString(value: Sequence<String>) = value.joinToString("") { it.firstCharToUpperCase() }
	}

	/**
	 * 以单个下划线分隔，所有单词全部小写。
	 *
	 * 示例：`snake_case`
	 */
	object SnakeCase : LetterCase {
		override val regex = """\$?[a-z]+(?:_(?:\$?[a-z]+\$?|\d+))+""".toRegex()
		override fun split(value: String) = value.split('_')
		override fun splitToSequence(value: String) = value.splitToSequence('_')
		override fun joinToString(value: Array<String>) = value.joinToString("_") { it.toLowerCase() }
		override fun joinToString(value: Iterable<String>) = value.joinToString("_") { it.toLowerCase() }
		override fun joinToString(value: Sequence<String>) = value.joinToString("_") { it.toLowerCase() }
	}

	/**
	 * 以单个下划线分隔，所有单词全部大写。
	 *
	 * 示例：`SCREAMING_SNAKE_CASE`
	 */
	object ScreamingSnakeCase : LetterCase {
		override val regex = """\$?[A-Z]+(?:_(?:\$?[A-Z]+|\d+))+""".toRegex()
		override fun split(value: String) = value.split('_')
		override fun splitToSequence(value: String) = value.splitToSequence('_')
		override fun joinToString(value: Array<String>) = value.joinToString("_") { it.toUpperCase() }
		override fun joinToString(value: Iterable<String>) = value.joinToString("_") { it.toUpperCase() }
		override fun joinToString(value: Sequence<String>) = value.joinToString("_") { it.toUpperCase() }
	}

	/**
	 * 以单个下划线分割的单词组。
	 *
	 * 示例：`Underscore_words`
	 */
	object UnderscoreWords : LetterCase {
		override val regex = """_*[a-zA-Z$]+(?:_+(?:[a-zA-Z$]+|\d+))+""".toRegex()
		override fun split(value: String) = value.split('_')
		override fun splitToSequence(value: String) = value.splitToSequence('_')
		override fun joinToString(value: Array<String>) = value.joinToString("_")
		override fun joinToString(value: Iterable<String>) = value.joinToString("_")
		override fun joinToString(value: Sequence<String>) = value.joinToString("_")
	}

	/**
	 * 以单个连接线分隔，所有单词全部小写。
	 *
	 * 示例：`kebab-case`
	 */
	object KebabCase : LetterCase {
		override val regex = """[a-z]+(?:-(?:[a-z]+|\d+))+""".toRegex()
		override fun split(value: String) = value.split('-')
		override fun splitToSequence(value: String) = value.splitToSequence('-')
		override fun joinToString(value: Array<String>) = value.joinToString("-") { it.toLowerCase() }
		override fun joinToString(value: Iterable<String>) = value.joinToString("-") { it.toLowerCase() }
		override fun joinToString(value: Sequence<String>) = value.joinToString("-") { it.toLowerCase() }
	}

	/**
	 * 以单个连接线分隔，所有单词全部大写。
	 *
	 * 示例：`KEBAB-UPPER-CASE`
	 */
	object KebabUpperCase : LetterCase {
		override val regex = """[A-Z]+(?:-(?:[A-Z]+|\d+))+""".toRegex()
		override fun split(value: String) = value.split('-')
		override fun splitToSequence(value: String) = value.splitToSequence('-')
		override fun joinToString(value: Array<String>) = value.joinToString("-") { it.toUpperCase() }
		override fun joinToString(value: Iterable<String>) = value.joinToString("-") { it.toUpperCase() }
		override fun joinToString(value: Sequence<String>) = value.joinToString("-") { it.toUpperCase() }
	}

	/**
	 * 以单个连接线分隔。
	 *
	 * 示例：`Hyphen-words`
	 */
	object HyphenWords : LetterCase {
		override val regex = """-*[a-zA-Z]+(?:-+(?:[a-zA-Z]+|\d+))+""".toRegex()
		override fun split(value: String) = value.split('-')
		override fun splitToSequence(value: String) = value.splitToSequence('-')
		override fun joinToString(value: Array<String>) = value.joinToString("-")
		override fun joinToString(value: Iterable<String>) = value.joinToString("-")
		override fun joinToString(value: Sequence<String>) = value.joinToString("-")
	}
	//endregion

	//region Path-like Letter Cases
	/**
	 * 以单个点分隔的路径。
	 *
	 * 示例：`doc.path`
	 */
	object ReferencePath : LetterCase {
		override val regex = """[a-zA-Z_\-$]+(?:\.[a-zA-Z_\-$]+)+""".toRegex()
		override fun split(value: String) = value.split('.')
		override fun splitToSequence(value: String) = value.splitToSequence('.')
		override fun joinToString(value: Array<String>) = value.joinToString(".")
		override fun joinToString(value: Iterable<String>) = value.joinToString(".")
		override fun joinToString(value: Sequence<String>) = value.joinToString(".")
	}

	/**
	 * 以斜线分隔的路径。兼容开始和结尾的斜线。
	 *
	 * 示例：`linux/path`
	 */
	object LinuxPath:LetterCase{
		override val regex = """/?[^/\\\s]+(?:/[^/\\\s]+]+)+/?""".toRegex()
		override fun split(value: String) = value.trim('/').split('/')
		override fun splitToSequence(value: String) = value.trim('/').splitToSequence('/')
		override fun joinToString(value: Array<String>) = value.joinToString("/")
		override fun joinToString(value: Iterable<String>) = value.joinToString("/")
		override fun joinToString(value: Sequence<String>) = value.joinToString("/")
	}

	/**
	 * 以反斜线分隔的路径。兼容开始和结尾的反斜线。
	 *
	 * 示例：`windows\path`
	 */
	object WindowsPath:LetterCase{
		override val regex = """\\?[^/\\\s]+(?:\\[^/\\\s]+]+)+\\?""".toRegex()
		override fun split(value: String) = value.trim('\\').split('\\')
		override fun splitToSequence(value: String) = value.trim('\\').splitToSequence('\\')
		override fun joinToString(value: Array<String>) = value.joinToString("\\")
		override fun joinToString(value: Iterable<String>) = value.joinToString("\\")
		override fun joinToString(value: Sequence<String>) = value.joinToString("\\")
	}
	//endregion
}