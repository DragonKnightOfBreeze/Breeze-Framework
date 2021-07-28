// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.annotation.*
import icu.windea.breezeframework.core.extension.*

@NotOptimized
object CaseFormats : ComponentRegistry<CaseFormat>() {
	//region Implementations
	/**
	 * 全小写。
	 *
	 * 示例：`lowercase`
	 */
	object LowerCaseFormat : AbstractCaseFormat() {
		private val regex = """[a-z]+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
		override fun split(value: String) = listOf(value)
		override fun splitToSequence(value: String) = sequenceOf(value)
		override fun joinToString(value: Array<String>) = value.joinToString("").lowercase()
		override fun joinToString(value: Iterable<String>) = value.joinToString("").lowercase()
		override fun joinToString(value: Sequence<String>) = value.joinToString("").lowercase()
	}

	/**
	 * 全大写。
	 *
	 * 示例：`UPPERCASE`
	 */
	object UpperCaseFormat : AbstractCaseFormat() {
		private val regex = """[A-Z]+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
		override fun split(value: String) = listOf(value)
		override fun splitToSequence(value: String) = sequenceOf(value)
		override fun joinToString(value: Array<String>) = value.joinToString("").uppercase()
		override fun joinToString(value: Iterable<String>) = value.joinToString("").uppercase()
		override fun joinToString(value: Sequence<String>) = value.joinToString("").uppercase()
	}

	/**
	 * 首字母大写。
	 *
	 * 示例：`Capitalized`
	 */
	object Capitalized : AbstractCaseFormat() {
		private val regex = """[A-Z][a-z]+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
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
	object LowerCaseFormatWords : AbstractCaseFormat() {
		private val regex = """[a-z']+(?:\s+[a-z']+)+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
		override fun split(value: String) = value.split(' ').filterNotEmpty()
		override fun splitToSequence(value: String) = value.splitToSequence(' ').filterNotEmpty()
		override fun joinToString(value: Array<String>) = value.joinToString(" ").lowercase()
		override fun joinToString(value: Iterable<String>) = value.joinToString(" ").lowercase()
		override fun joinToString(value: Sequence<String>) = value.joinToString(" ").lowercase()
	}

	/**
	 * 全大写的单词组。
	 *
	 * 示例：`UPPERCASE WORDS`
	 */
	object UpperCaseFormatWords : AbstractCaseFormat() {
		private val regex = """[A-Z']+(?:\s+[A-Z']+)+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
		override fun split(value: String) = value.split(' ').filterNotEmpty()
		override fun splitToSequence(value: String) = value.splitToSequence(' ').filterNotEmpty()
		override fun joinToString(value: Array<String>) = value.joinToString(" ").uppercase()
		override fun joinToString(value: Iterable<String>) = value.joinToString(" ").uppercase()
		override fun joinToString(value: Sequence<String>) = value.joinToString(" ").uppercase()
	}

	/**
	 * 首个单词的首字母大写的单词组。
	 *
	 * 示例：`Uppercase words`
	 */
	object FirstWordCapitalized : AbstractCaseFormat() {
		private val regex = """[A-Z][a-z']*(?:\s+[a-z']+)+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
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
	object CapitalizedWords : AbstractCaseFormat() {
		private val regex = """[A-Z][a-z']*(?:\s+[a-z']+)+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
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
	object Words : AbstractCaseFormat() {
		private val regex = """[a-zA-Z']+(?:\s+[a-zA-Z']+)+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
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
	object CamelCaseFormat : AbstractCaseFormat() {
		private val regex = """\$?[a-z]+(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+)+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
		override fun split(value: String) = value.splitWords().split(' ')
		override fun splitToSequence(value: String) = value.splitWords().splitToSequence(' ')
		override fun joinToString(value: Array<String>) =
			value.joinToString("") { it.firstCharToUpperCase() }.firstCharToLowerCase()

		override fun joinToString(value: Iterable<String>) =
			value.joinToString("") { it.firstCharToUpperCase() }.firstCharToLowerCase()

		override fun joinToString(value: Sequence<String>) =
			value.joinToString("") { it.firstCharToUpperCase() }.firstCharToLowerCase()
	}

	/**
	 * 以单词边界分隔，所有单词首字母大写。
	 *
	 * 示例：`PascalCaseFormat`
	 */
	object PascalCaseFormat : AbstractCaseFormat() {
		private val regex = """\$?(?:[A-Z][a-z]+|[A-Z]+)(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+)+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
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
	object SnakeCaseFormat : AbstractCaseFormat() {
		private val regex = """\$?[a-z]+(?:_(?:\$?[a-z]+\$?|\d+))+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
		override fun split(value: String) = value.split('_')
		override fun splitToSequence(value: String) = value.splitToSequence('_')
		override fun joinToString(value: Array<String>) = value.joinToString("_") { it.lowercase() }
		override fun joinToString(value: Iterable<String>) =
			value.joinToString("_") { it.lowercase() }

		override fun joinToString(value: Sequence<String>) =
			value.joinToString("_") { it.lowercase() }
	}

	/**
	 * 以单个下划线分隔，所有单词全部大写。
	 *
	 * 示例：`SCREAMING_SNAKE_CASE`
	 */
	object ScreamingSnakeCaseFormat : AbstractCaseFormat() {
		private val regex = """\$?[A-Z]+(?:_(?:\$?[A-Z]+|\d+))+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
		override fun split(value: String) = value.split('_')
		override fun splitToSequence(value: String) = value.splitToSequence('_')
		override fun joinToString(value: Array<String>) = value.joinToString("_") { it.uppercase() }
		override fun joinToString(value: Iterable<String>) = value.joinToString("_") { it.uppercase() }
		override fun joinToString(value: Sequence<String>) = value.joinToString("_") { it.uppercase() }
	}

	/**
	 * 以单个下划线分割的单词组。
	 *
	 * 示例：`Underscore_words`
	 */
	object UnderscoreWords : AbstractCaseFormat() {
		private val regex = """_*[a-zA-Z$]+(?:_+(?:[a-zA-Z$]+|\d+))+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
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
	object KebabCaseFormat : AbstractCaseFormat() {
		private val regex = """[a-z]+(?:-(?:[a-z]+|\d+))+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
		override fun split(value: String) = value.split('-')
		override fun splitToSequence(value: String) = value.splitToSequence('-')
		override fun joinToString(value: Array<String>) = value.joinToString("-") { it.lowercase() }
		override fun joinToString(value: Iterable<String>) =
			value.joinToString("-") { it.lowercase() }

		override fun joinToString(value: Sequence<String>) =
			value.joinToString("-") { it.lowercase() }
	}

	/**
	 * 以单个连接线分隔，所有单词全部大写。
	 *
	 * 示例：`KEBAB-UPPER-CASE`
	 */
	object KebabUpperCaseFormat : AbstractCaseFormat() {
		private val regex = """[A-Z]+(?:-(?:[A-Z]+|\d+))+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
		override fun split(value: String) = value.split('-')
		override fun splitToSequence(value: String) = value.splitToSequence('-')
		override fun joinToString(value: Array<String>) = value.joinToString("-") { it.uppercase() }
		override fun joinToString(value: Iterable<String>) = value.joinToString("-") { it.uppercase() }
		override fun joinToString(value: Sequence<String>) = value.joinToString("-") { it.uppercase() }
	}

	/**
	 * 以单个连接线分隔。
	 *
	 * 示例：`Hyphen-words`
	 */
	object HyphenWords : AbstractCaseFormat() {
		private val regex = """-*[a-zA-Z]+(?:-+(?:[a-zA-Z]+|\d+))+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
		override fun split(value: String) = value.split('-')
		override fun splitToSequence(value: String) = value.splitToSequence('-')
		override fun joinToString(value: Array<String>) = value.joinToString("-")
		override fun joinToString(value: Iterable<String>) = value.joinToString("-")
		override fun joinToString(value: Sequence<String>) = value.joinToString("-")
	}


	/**
	 * 类路径的字母格式。
	 */
	interface PathLikeCaseFormat

	/**
	 * 以单个点分隔的路径。
	 *
	 * 示例：`doc.path`
	 */
	object ReferencePath : CaseFormat, PathLikeCaseFormat {
		private val regex = """[a-zA-Z_\-$]+(?:\.[a-zA-Z_\-$]+)+""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
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
	object LinuxPath : CaseFormat, PathLikeCaseFormat {
		private val regex = """/?[^/\\\s]+(?:/[^/\\\s]+]+)+/?""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
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
	object WindowsPath : CaseFormat, PathLikeCaseFormat {
		private val regex = """\\?[^/\\\s]+(?:\\[^/\\\s]+]+)+\\?""".toRegex()
		override fun matches(value: String): Boolean = regex.matches(value)
		override fun split(value: String) = value.trim('\\').split('\\')
		override fun splitToSequence(value: String) = value.trim('\\').splitToSequence('\\')
		override fun joinToString(value: Array<String>) = value.joinToString("\\")
		override fun joinToString(value: Iterable<String>) = value.joinToString("\\")
		override fun joinToString(value: Sequence<String>) = value.joinToString("\\")
	}
	//endregion

	override fun registerDefault() {
		register(LowerCaseFormat)
		register(UpperCaseFormat)
		register(Capitalized)
		register(LowerCaseFormatWords)
		register(UpperCaseFormatWords)
		register(FirstWordCapitalized)
		register(CapitalizedWords)
		register(Words)
		register(CamelCaseFormat)
		register(PascalCaseFormat)
		register(SnakeCaseFormat)
		register(ScreamingSnakeCaseFormat)
		register(UnderscoreWords)
		register(KebabCaseFormat)
		register(KebabUpperCaseFormat)
		register(HyphenWords)

		register(ReferencePath)
		register(LinuxPath)
		register(WindowsPath)
	}

	/**
	 * 推断单词格式。
	 */
	fun infer(value: String): CaseFormat? {
		for(caseFormat in components.values) {
			if(caseFormat.matches(value)) return caseFormat
		}
		return null
	}
}
