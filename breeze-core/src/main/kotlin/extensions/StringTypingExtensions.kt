/***********************************************************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 *
 *                                     ...]]]]]]..
 *                             ...,]OOOOOOOOOOOOOOO].
 *                            ./]/[[[[\OOOOOOOOOO@@@@O].
 *                                        .OOOOOO@@@@@@@@].
 *                                         =OOOOO@@@@@@@@@@@`.
 *                                          =@@OO@@@@@@@@@@@@@\.
 *                                          .\@@@@@@@@@@@@@@@@@@\.
 *                                   .. .    .O@@@@@@@@@@@@@@@@@@@@`.
 *                                    .``=\.  ,@@@@@@@^=@^O@@@@@@@@@@\.              .`  ..,]]]]]].
 *                      ... .....       .=OO` .O@@@@@^=^..O@@@@@@@@@@@.           .,@@]@@@@@@@@@@@.
 *               ........*[]],\OOOOO\]..  .\O^ O@@@@^O@`..@@@@@@@@@@@@@].      ....OO@@@@@@@@@@@@^
 *          ...*.......**[oOOOOOOOOO@@@@@`. =@`=@@@`=@@`.=@@@@@@@@@@@@@@\......./^.,@@@@@@@@@@@@^
 *                .........[\OOO@@@@@@@@@@@`]O@OO@`.=@@.`=@@@@@@@@@@@@@@@@^...,@`.=@@@@@@@@@@@@`
 *                            ......[[\OO@@@O\O@@`...@@@.@@@@@@@@O@@@@@@@@\..//..O@@@@@@O]`.\/.
 *                    ..,]/OOOO@@@@@@@@@@@^,`,`O@....@@\=@@OO@@@@@@@@@@@@@@]@^./@@@@@@@/`..OO@`
 *                 ..*...,]OO@@@@@@@@@@@O`.,,/@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/O@@@@@@@@@@@@@@@@@.
 *               ....*[*=o/\OO@@@@@@@@@O@@@@@@@@@@@`.=[\O@@@@@@@@@@@@@@@@@@@@@@@@@@@@O[=@@@@@@@O.
 *             ...*....*]/OOO@@@@@[.,/@@@@@@@@@@@@^...........[OOO@@@@@@@@@@@@@@@/.    .O@@@@@@@^
 *           ........**`,OO/oO/. ,/@@@@@@@@@@@@O`***..........**......*....*.=O[.        .=@@@@/\^
 *           *..   ,`.,`**,.. .,@@@@@@@@@@@@@@@^.**.**.....*`...*...../`.........         .\/[..=^.
 *           .    ..*\*. .. ./O@@@@@@@@@@@@@@@O^/....*....=@@@[[^*...=@OO..*... ..        =@@...@@^
 * \.            .*,.     .,O@@@@@@@@@@@@@@@O@O/^*...*.....\]*....**./=.o`*^.... ..      ,@@@@\=@@@`
 *  .`.          ..      ,OOO@@@@@@@OOO/[[[[..*........................**==.* ..         /@@@@@@@@@@.
 *    .,.               ,/`..            ... .........*................*.**.. ..       ./@@@@@@@@@@@.
 *       ,`            ./.               ..  .........*...=*`......../O...... ..     .O@@@@@@@@@@@@^
 *         ,..    ..   =.                ..   ...`...**...,**,**..][=@@`*=@@@@]]]\.]@@@@@@@@@@@@@@@^
 *    ..     .*.  ..   =.                ...,/@@@@@@@O^`..=*****.,\\*./ooO@@@@@@@@@@@@@@@@@@@@@/[...
 *   ..        .\.......`.                 .,O@@@@@@@@\O`.*****...*^`*O\OO@@@@@@@@@@@@@@@@@@@@/.
 *  ...    ..... .\.....\^..                 ./@@@@@@@O@@**......`...`=@O@@@@@@@@@@@@@@@@@@@`
 *   ...  ..       ....  ....          ....*./@@@@@@@@@@@O\.....*.....*@@@@@@@@@@@@@@@@@@@/.
 *   .... ..        ...`  ................*.=@@@@@@@@@@@@@@@\...`.....**@@@@@@@@@@@@@@@@@@@@`
 *    .......         ..,`.        ....*....[[O@@@@@@@@@@@@@@@\@@@\*.....\@@@@@@@@@@@@@@@@@@@@\.
 *     .......          ..,................./@/=@@@@@@@@@@@@@@@@@@@@@]...*\@@@@@@@@@@@@@@@@@@@@@\.
 *       ........           .*.   .........=@^*\/=@@@@@@@@@@@@@@@@@@@@@@\./@@@@@@@@@@@@@@@@@@@@@@@\.
 *      .. ........           .\.....**]/]/\]o\]]@@@@@@@@@@@@@@@@@@OOOO@@@@@@^/@@@@@@@@@@@@@@@@@@@@@`                 ..
 *       ...............    ......,/@@@@O@@Oo@@@@@@@@@@@@@@@@@@@@@@@@@@@@@=@@@O*[@@@@@@@@@@@@@@@@@@@@.               =O`
 *            .......*..........  .[@@O@@@OO@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O@\..\@@@@@@@@@@@@@@@@@@@@.              /\]
 *   =`                    ,]`    ,/@@@@@OO@@@@@@@@@@@@@@@@@@@@@@@@@@O@@@@o@@OO\.,@@@@@@@@@@@@@@@@@@@@\.          ./O@O.
 *   =.       .O`        ,@@@@\.,@@@@@@@OO@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@o@@OO@O.=@@@@@@@@@@@@@@@@@@@@\.        ,O@@^=.
 * . .\.   =OO@.O.     ,@@@@@@@@@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O/O@OOOOO`\@@@@@@@@@@@@@@@@@@@@O.     ./@@/=.=.
 * `  .,`. .@,\.O ....OOO@@@@@@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O=..[\OOOOO`O@@@^ .,\@@@@@O@@@@@/\\]`./@@`. =^^
 * \`    ,\\/@@@`  ./@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/.=^...\oOOOO\@@@^          ./@@[`=@OO@@/.   .O.
 *  .\].     ./@@]]@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O`.......***OOOO@@@^        .,@@`  =/@@@/.    .OO.
 *      .[[[[`..\@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O`..........**=OO@O@@`        /@@\]]/@@@@^O..  .OOO^
 *              .,O@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\*..........*,/@@@O@^]].    ./@@@@@OO@@@@o@@`  =OOO^
 *               .,O@@@@@@@@@@@@@@@@@@@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@Oo*......,O@@@OO^.\@@@].,@@@@@@@O@@@\/O=`  .=OO`.
 * .......`.   .\@@@@@@@@@@@@@@@@@@@@@@@@@/` =@@@@@@@@@@@@@@@@@@@@@@@@@@@@OO\]/@@@OOOO@@@@@@@@@\@@@@@@@@@@@`.    ,OO`.
 *   ,O`. ,O\].. ,@@@@@@@@@@@@@@@@@@@@@/.    ./@@@@@@@@@@@@@\@@OO@***O@@`[\@@O@@@@@@@O/`*@@@@`/@@@@@@@@@@^=O.  ..=O.
 *     .\OOO@O@@@O]`\@@@@@@@@@@@@@@@O\O\]..  .@@@@@@@@@@@@@@\OOo`OO@@@`..,O@@@@//\..[*....O@@@@@@@@@@@@@^./@.   ...
 *           ..[[O@@@@@@\]`. .....,@@@\/@@@@O/@@@@@@@@@.,@@\O@@\=O@@[.../@@@@[.=^.\.......==@@@@@@@@@^.  ,@O.
 *    .,]OO]`..     ..[O@@@@@@@O].. ./@@@@@@@@@@@@@@@@@^O@@@@OOOOoO`*,O@@@O`...=\..,\...,/./@@@@@@@@/.  .@`
 *  .O@@@/\O`...  ..,`.    .,\@@@@@@@@@@OOO@@@@@@@@@@@@\@O@@@@@OoOO@@@@@O......@@`..O..../@@@@@@@@O^.  ,O.
 *  .     .....,@@@O`            .[@@@@@@OOO@@@@@@@@@@@@\@@@@@@@@@@@@/[*......=@@@`...]@@@@O@@@@@/.  .=/.
 *          ..=@@@\.               .O@@OoooO@@@@@@@@@@@@@@@@OO\@OO**..........=@@@@@@@@@@`./@@@@\.  ./^
 *            .*O@@@\.              ....OOO@@@@@@@@@@@@@@@@@OO@@O@\]]]]]]]/@@@@@@@@@/[..../^,@@O\^ =@`
 *            ... .[\O^.          ./@@@@@@@@@@@@@@@@@@@@@@@@@@@@/,\@@@@@@@@@@@@@@]....../O`......,@O.
 *            ....                      ...\@@@@@@@@@@@@@@@@@@@`......[[`.@@@@@@@@/\OO[`......../@^
 *             ...                           .[\O^,@@@@@@@O@@@@..........=@@@@@@@@@`.........=@@@@].
 *             ...                            ...,O@OO@@@OOOO@@..........=@@@@@@@@@@^......,@@@@@@@^
 *             ..*.                           .,/OOO@@@@@OOOO@@`.........@@@@@@@@@@@@@@@@@@@@@@@@@@@\`..
 *             .....                          .OOOO@@@@OO@@@@@@@\......,@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@O\]..
 *              ......                      ./OOOOOOO@@OOO@@@@@@@@@@@@@@@@@@@^.    ./@@@@@@@@@@@@@@@@@@@@@@@@@@O]`..
 *               ......                  .]OOOOOO[./@OOOOO@@@@@@@@@@@@@@@@@/.  .,/@@/`O@@@@@@@@@@@@@@@@@@@@O@@@@@@@@@@\]
 *               .........               .......*.,OOOOOO@@@@@@@@@@@@@@@@@@^.]@@@/.   .@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 *                ...........                 .../OOOOO//@@@@@@@@@@@@@@@@O@@@/`.       =@O@@@@@@@@@@@@@...[\@@@@@@@@@@@O
 *                 .........................*`.]OOOO/`.=@@@@@@@@@@@@@@@@@/\O.          .O/@@@@@@@@@@@O^         .[\O@@@@
 *                   .....................*.,[[[......=@@@`.\@@@@@@@@@@@@^.^            ,^,@@@@@@@@@@.                ..
 *                    .................*.............=@@@`./@@@@@@@@@@@@^ ..             .. \@@@@@@@@^`.         ...*[`.
 *                     ............................*,@@@^/@@@@@@@@@@@@@@.                    =@@@@@@@@..\...*[..
 *                      ...........................=@@@O@@@@@@@@@@@@@@@^                     .@@@@@@@@^. .\.
 *                        ..........................\/@@@@@@@\@@@@@@@@O.                ....*[,@@@@@@@^    .*.
 *                          ........................O@@@@@@/..@@@@@@@@^         ...**[.       .@@@@@@@@.     .,`.
 *                            ..................../@@@@@@@@\..@@@@@@@@^ ...*,[..               =@@@@@@@.        ,`.
 *                              ................/@@@@@@@`@@@^ =@@@@@@@^                        .\@@@@@@^          ,*.
 *                                  ..........,O@@@@@@` ..[O[`=@@@@@@@^                         ,@@@@@@^            .\.
 *                                          ,OO@@@@@/`.       .@@@@@@@O.                         @@@@@@\.             .\
 *                                      ..,/O@@@@@/.          .O@@@@@@@.                         .@@@@@^
 *                             ....*[`. ./OOOO@@/.             =@@@@@@O.                          =@@@@@.
 *                     ....*[..       .,OOOOOOO`               .@@@@@@@.                          =@@@@@^
 *
 * Breeze is blowing ...
 **********************************************************************************************************************/

package com.windea.breezeframework.core.extensions

//这些特殊的字符串处理方法相比let & joinToString更加灵活，更加适用于一般情况
//允许更多自定义项

/**
 * 使用特定的转化规则，将当前对象转化成字符串。
 * * 空值 -> 返回空字符串。
 * * 存在转化方法 -> 使用该方法将当前对象转化为字符串。
 * * 其他情况 -> 使用默认方法将当前对象转化为字符串。
 */
fun <T : Any> T?.typing(transform:((T) -> String)? = null):String {
	return if(this == null) "" else if(transform != null) transform(this) else toString()
}


/**
 * 使用特定的转化规则，将当前数组转化成字符串。
 * * 忽略空元素（默认为`true`） && 存在空元素 -> 忽略该元素.
 * * 忽略空数组（默认为`true`） && 不存在有效的元素 -> 返回空字符串。
 * * 存在转化方法 -> 使用该方法将元素转化为字符串。
 * * 其他情况 -> 使用默认方法将元素转化为字符串。
 */
fun <T : Any> Array<out T?>.typingAll(
	separator:CharSequence = ", ", prefix:CharSequence = "", postfix:CharSequence = "", indent:String? = null,
	omitEmptyElement:Boolean = true, omitEmpty:Boolean = true,
	transform:((T) -> CharSequence)? = null
):String {
	var result = buildString {
		var count = 0
		for(element in this@typingAll) {
			val snippet = if(element == null) null else if(transform != null) transform(element) else element.toString()
			if(omitEmptyElement && snippet.isNullOrEmpty()) continue
			if(count++ > 0) append(separator)
			append(snippet)
		}
	}
	if(indent != null) result = result.prependIndent(indent)
	if(omitEmpty && result.isNotEmpty()) result = "$prefix$result$postfix"
	return result
}

/**
 * 使用特定的转化规则，将当前集合转化成字符串。
 * * 忽略空元素（默认为`true`） && 存在空元素 -> 忽略该元素.
 * * 忽略空集合（默认为`true`） && 不存在有效的元素 -> 返回空字符串。
 * * 存在转化方法 -> 使用该方法将元素转化为字符串。
 * * 其他情况 -> 使用默认方法将元素转化为字符串。
 */
fun <T : Any> Iterable<T?>.typingAll(
	separator:CharSequence = ", ", prefix:CharSequence = "", postfix:CharSequence = "", indent:String? = null,
	omitEmpty:Boolean = true, omitEmptyElement:Boolean = true,
	transform:((T) -> CharSequence)? = null
):String {
	var result = buildString {
		var count = 0
		for(element in this@typingAll) {
			val snippet = if(element == null) null else if(transform != null) transform(element) else element.toString()
			if(omitEmptyElement && snippet.isNullOrEmpty()) continue
			if(count++ > 0) append(separator)
			append(snippet)
		}
	}
	if(indent != null) result = result.prependIndent(indent)
	if(omitEmpty && result.isNotEmpty()) result = "$prefix$result$postfix"
	return result
}

/**
 * 使用特定的转化规则，将当前映射转化成字符串。
 * * 忽略空键值对（默认为`true`） && 存在空键值对 -> 忽略该键值对.
 * * 忽略空值（默认为`true`） && 存在空值 -> 忽略该键值对.
 * * 忽略空映射（默认为`true`） && 不存在有效的键值对或值 -> 返回空字符串。
 * * 存在转化方法 -> 使用该方法将元素转化为字符串。
 * * 其他情况 -> 使用默认方法将元素转化为字符串。
 */
fun <K, V> Map<K, V>.typingAll(
	separator:CharSequence = ", ", prefix:CharSequence = "", postfix:CharSequence = "", indent:String? = null,
	omitEmpty:Boolean = true, omitEmptyEntry:Boolean = true, omitEmptyValue:Boolean = true,
	transform:((Map.Entry<K, V>) -> CharSequence)? = null
):String {
	var result =  buildString {
		var count = 0
		for(entry in this@typingAll) {
			val valueSnippet = entry.value?.toString()
			if(omitEmptyValue && valueSnippet.isNullOrEmpty()) continue
			val snippet = if(transform == null) entry.toString() else transform(entry)
			if(omitEmptyEntry && snippet.isEmpty()) continue
			if(count++ > 0) append(separator)
			append(snippet)
		}
		if(omitEmpty && length > 0) insert(0, prefix).append(postfix)
	}
	if(indent != null) result = result.prependIndent(indent)
	if(omitEmpty && result.isNotEmpty()) result = "$prefix$result$postfix"
	return result
}

/**
 * 使用特定的转化规则，将当前映射转化成字符串。
 * * 没有有效值的序列 && 忽略空虚列  -> 返回空字符串
 * * 空值和空字符串 && 忽略空元素 -> 忽略该元素
 * * 存在转化方法 -> 使用转化方法转化元素
 */
fun <T : Any> Sequence<T?>.typingAll(
	separator:CharSequence = ", ", prefix:CharSequence = "", postfix:CharSequence = "", indent:String? = null,
	omitEmpty:Boolean = true, omitEmptyElement:Boolean = true,
	transform:((T) -> CharSequence)? = null
):String {
	var result =  buildString {
		var count = 0
		for(element in this@typingAll) {
			val snippet = if(element == null) null else if(transform != null) transform(element) else element.toString()
			if(omitEmptyElement && snippet.isNullOrEmpty()) continue
			if(count++ > 0) append(separator)
			append(snippet)
		}
		if(omitEmpty && length > 0) insert(0, prefix).append(postfix)
	}
	if(indent != null) result = result.prependIndent(indent)
	if(omitEmpty && result.isNotEmpty()) result = "$prefix$result$postfix"
	return result
}


//fun String.typingToList(separator:CharSequence= ", ",prefix:CharSequence = "",postfix:CharSequence=""):List<String> = TODO()

//fun String.typingToSequence(separator:CharSequence = ", ",prefix:CharSequence = "",postfix:CharSequence=""):Sequence<String> = TODO()
