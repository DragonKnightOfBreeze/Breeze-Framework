@file:Suppress("ObjectPropertyName")

package com.windea.breezeframework.core.enums.text

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*

/**字母的格式。*/
enum class LetterCase(
	override val splitter: (CharSequence) -> List<String> = { listOf(it.toString()) },
	override val sequenceSplitter: (CharSequence) -> Sequence<String> = { sequenceOf(it.toString()) },
	override val joiner: (Iterable<CharSequence>) -> String = { it.joinToString("") },
	override val arrayJoiner: (Array<out CharSequence>) -> String = { it.joinToString("") },
	override val regex: Regex? = null,
	override val predicate: (String) -> Boolean = { regex == null || it matches regex }
) : CaseStrategy {
	/**全小写的单词。*/
	LowerCase(
		joiner = { it.joinToString("").toLowerCase() },
		arrayJoiner = { it.joinToString("").toLowerCase() },
		regex = """[a-z]+""".toRegex()
	),

	/**全大写的单词。*/
	UpperCase(
		joiner = { it.joinToString("").toUpperCase() },
		arrayJoiner = { it.joinToString("").toUpperCase() },
		regex = """[A-Z]+""".toRegex()
	),

	/**首字母大写的单词。*/
	Capitalized(
		joiner = { it.joinToString("").firstCharToUpperCase() },
		arrayJoiner = { it.joinToString("").firstCharToUpperCase() },
		regex = """[A-Z][a-z]+""".toRegex()
	),

	/**全小写的单词组。*/
	LowerCaseWords(
		{ it.split(' ').filterNotEmpty<String>() },
		{ it.splitToSequence(' ').filterNotEmpty<String>() },
		{ it.joinToString(" ").toLowerCase() },
		{ it.joinToString(" ").toLowerCase() },
		"""[a-z']+(?:\s+[a-z']+)+""".toRegex()
	),

	/**全大写的单词组。*/
	UpperCaseWords(
		{ it.split(' ').filterNotEmpty<String>() },
		{ it.splitToSequence(' ').filterNotEmpty<String>() },
		{ it.joinToString(" ").toUpperCase() },
		{ it.joinToString(" ").toUpperCase() },
		"""[A-Z']+(?:\s+[A-Z']+)+""".toRegex()
	),

	/**首个单词的首字母大写的单词组。*/
	FirstWordCapitalized(
		{ it.split(' ').filterNotEmpty<String>() },
		{ it.splitToSequence(' ').filterNotEmpty<String>() },
		{ it.joinToString(" ").firstCharToUpperCase() },
		{ it.joinToString(" ").firstCharToUpperCase() },
		"""[A-Z][a-z']*(?:\s+[a-z']+)+""".toRegex()
	),

	/**首字母大写的单词组。*/
	CapitalizedWords(
		{ it.split(' ').filterNotEmpty<String>() },
		{ it.splitToSequence(' ').filterNotEmpty<String>() },
		{ it.joinToString(" ") { s -> s.firstCharToUpperCase() } },
		{ it.joinToString(" ") { s -> s.firstCharToUpperCase() } },
		"""[A-Z][a-z]*(?:\s+[a-z]+)+""".toRegex()
	),

	/**单词组。*/
	GenericWords(
		{ it.split(' ').filterNotEmpty<String>() },
		{ it.splitToSequence(' ').filterNotEmpty<String>() },
		{ it.joinToString(" ") },
		{ it.joinToString(" ") },
		"""\S+(?:\s+\S+)+""".toRegex()
	),

	/**以单个点分割的格式。*/
	DotCase(
		{ it.split('.') },
		{ it.splitToSequence('.') },
		{ it.joinToString(".") },
		{ it.joinToString(".") },
		"""[a-zA-Z_{}\[\]$]+(?:\.[a-zA-Z_{}\[\]$]+)+""".toRegex()
	),

	/**以单词边界分割，首个单词全小写，后续单词首字母大写的格式。*/
	CamelCase(
		{ it.splitWords().split(' ') },
		{ it.splitWords().splitToSequence(' ') },
		{ it.joinToString("") { S -> S.firstCharToUpperCase() }.firstCharToLowerCase() },
		{ it.joinToString("") { S -> S.firstCharToUpperCase() }.firstCharToLowerCase() },
		"""\$?[a-z]+(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+)+""".toRegex()
	),

	/**以单词边界分割，所有单词首字母大写的格式。*/
	PascalCase(
		{ it.splitWords().split(' ') },
		{ it.splitWords().splitToSequence(' ') },
		{ it.joinToString("") { s -> s.firstCharToUpperCase() } },
		{ it.joinToString("") { s -> s.firstCharToUpperCase() } },
		"""\$?(?:[A-Z][a-z]+|[A-Z]+)(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+)+""".toRegex()
	),

	/**以单个下划线分割，所有单词全小写的格式。*/
	SnakeCase(
		{ it.split('_') },
		{ it.splitToSequence('_') },
		{ it.joinToString("_") { s -> s.toString().toLowerCase() } },
		{ it.joinToString("_") { s -> s.toString().toLowerCase() } },
		"""\$?[a-z]+(?:_(?:\$?[a-z]+\$?|\d+))+""".toRegex()
	),

	/**以单个下划线分割，所有单词全大写的格式。*/
	ScreamingSnakeCase(
		{ it.split('_') },
		{ it.splitToSequence('_') },
		{ it.joinToString("_") { s -> s.toString().toUpperCase() } },
		{ it.joinToString("_") { s -> s.toString().toUpperCase() } },
		"""\$?[A-Z]+(?:_(?:\$?[A-Z]+|\d+))+""".toRegex()
	),

	/**以数个下划线分割的格式。*/
	UnderscoreWords(
		{ it.split('_').filterNotEmpty<String>() },
		{ it.splitToSequence('_').filterNotEmpty<String>() },
		{ it.joinToString("_") },
		{ it.joinToString("_") },
		"""_*[a-zA-Z$]+(?:_+(?:[a-zA-Z$]+|\d+))+""".toRegex()
	),

	/**以单个连接线分割，所有单词全小写的格式。*/
	KebabCase(
		{ it.split('-') },
		{ it.splitToSequence('-') },
		{ it.joinToString("-") { s -> s.toString().toLowerCase() } },
		{ it.joinToString("-") { s -> s.toString().toLowerCase() } },
		"""[a-z]+(?:-(?:[a-z]+|\d+))+""".toRegex()
	),

	/**以单个连接线分割，所有单词全大写的格式。*/
	KebabUppercase(
		{ it.split('-') },
		{ it.splitToSequence('-') },
		{ it.joinToString("-") { s -> s.toString().toUpperCase() } },
		{ it.joinToString("-") { s -> s.toString().toUpperCase() } },
		"""[A-Z]+(?:-(?:[A-Z]+|\d+))+""".toRegex()
	),

	/**以数个连接线分割的格式。*/
	HyphenWords(
		{ it.split('-').filterNotEmpty<String>() },
		{ it.splitToSequence('-').filterNotEmpty<String>() },
		{ it.joinToString("-") },
		{ it.joinToString("-") },
		"""-*[a-zA-Z]+(?:-+(?:[a-zA-Z]+|\d+))+""".toRegex()
	),

	/**以单词边界或者数个下划线分割，首个单词全小写，后续单词首字母大写的格式。*/
	@ExplicitUsageApi
	CamelCaseAllowUnderscore(
		{ it.splitWords().split(' ', '_').filterNotEmpty<String>() },
		{ it.splitWords().splitToSequence(' ', '_').filterNotEmpty<String>() },
		{ it.joinToString("") { s -> s.firstCharToUpperCase() }.firstCharToLowerCase() },
		{ it.joinToString("") { s -> s.firstCharToUpperCase() }.firstCharToLowerCase() },
		"""_*\$?[a-z]+(?:_*(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+))+""".toRegex()
	),

	/**以单词边界或者数个下划线分割，所有单词首字母大写的格式。*/
	@ExplicitUsageApi
	PascalCaseAllowUnderscore(
		{ it.splitWords().split(' ', '_').filterNotEmpty<String>() },
		{ it.splitWords().splitToSequence(' ', '_').filterNotEmpty<String>() },
		{ it.joinToString("") { s -> s.firstCharToUpperCase() }.firstCharToLowerCase() },
		{ it.joinToString("") { s -> s.firstCharToUpperCase() }.firstCharToLowerCase() },
		"""_*\$?(?:[A-Z][a-z]+|[A-Z]+)(?:_*(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+))+""".toRegex()
	),

	/**未知的字母格式。*/
	Unknown;

	companion object {
		/**@see LetterCase.LowerCase*/
		@JvmSynthetic val lowercase: LetterCase = LowerCase

		/**@see LetterCase.UpperCase*/
		@JvmSynthetic val UPPERCASE: LetterCase = UpperCase

		/**@see LetterCase.LowerCaseWords*/
		@JvmSynthetic val `lower case words`: LetterCase = LowerCaseWords

		/**@see LetterCase.UpperCaseWords*/
		@JvmSynthetic val `UPPER CASE WORDS`: LetterCase = UpperCaseWords

		/**@see LetterCase.FirstWordCapitalized*/
		@JvmSynthetic val `First word capitalized`: LetterCase = FirstWordCapitalized

		/**@see LetterCase.CapitalizedWords*/
		@JvmSynthetic val `Capitalized Words`: LetterCase = CapitalizedWords

		/**@see LetterCase.GenericWords*/
		@JvmSynthetic val `Generic Words`: LetterCase = GenericWords

		/**@see LetterCase.CamelCase*/
		@JvmSynthetic val `camelCase`: LetterCase = CamelCase

		/**@see LetterCase.SnakeCase*/
		@JvmSynthetic val `snake_case`: LetterCase = SnakeCase

		/**@see LetterCase.ScreamingSnakeCase*/
		@JvmSynthetic val SCREAMING_SNAKE_CASE: LetterCase = ScreamingSnakeCase

		/**@see LetterCase.UnderscoreWords*/
		@JvmSynthetic val `underscore_Words`: LetterCase = UnderscoreWords

		/**@see LetterCase.KebabCase*/
		@JvmSynthetic val `kebab-case`: LetterCase = KebabCase

		/**@see LetterCase.KebabUppercase*/
		@JvmSynthetic val `KEBAB-UPPERCASE`: LetterCase = KebabUppercase

		/**@see LetterCase.HyphenWords*/
		@JvmSynthetic val `hyphen-Words`: LetterCase = HyphenWords

		/**@see LetterCase.CamelCaseAllowUnderscore*/
		@JvmSynthetic val `camelCase_AllowUnderscore`: LetterCase = CamelCaseAllowUnderscore

		/**@see LetterCase.PascalCaseAllowUnderscore*/
		@JvmSynthetic val `PascalCase_AllowUnderscore`: LetterCase = PascalCaseAllowUnderscore


		/**将第一个字符转为大写。*/
		private fun CharSequence.firstCharToUpperCase(): String {
			return this[0].toUpperCase() + this.substring(1)
		}

		/**将第一个字符转为小写。*/
		private fun CharSequence.firstCharToLowerCase(): String {
			return this[0].toLowerCase() + this.substring(1)
		}

		/**基于小写字母与大写字母的边界，将当前字符串转化为以空格分割的单词组成的字符串。允许全大写的单词。*/
		private fun CharSequence.splitWords(): String {
			return this.replace("""\B([A-Z][a-z])""".toRegex(), " $1")
		}
	}
}
