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

@file:Suppress("UNCHECKED_CAST", "UNCHECKED_CAST")

package com.windea.breezeframework.core.domain

import com.windea.breezeframework.core.extensions.*
import java.io.*
import java.net.*
import java.nio.charset.*
import java.nio.file.*

//TODO 支持转化成泛型类型

object ConverterService {
	private val converters: MutableMap<Class<*>, MutableList<Pair<Class<*>, Converter<*, *>>>> = mutableMapOf()

	init {
		registerDefaultConverters()
		registerExtendedConverters()
	}

	inline fun <reified T : Any, reified R : Any> register(converter: Converter<T, R>,override:Boolean = false) {
		register(converter, T::class.java, R::class.java,override)
	}

	fun <T : Any, R : Any> register(converter: Converter<T, R>, sourceType: Class<T>, targetType: Class<R>,override:Boolean = false) {
		val converterPairs = converters.getOrPut(targetType) { mutableListOf() }
		val converterPair = sourceType to converter
		if(override) converterPairs.add(0,converterPair) else converterPairs.add(converterPair)
	}

	inline fun <reified T : Any> convert(value: Any?): T {
		return convert(value, T::class.java)
	}

	fun <T> convert(value: Any?, targetType: Class<T>): T {
		try {
			if(value == null) return null as T
			val matchedConverters = converters[targetType]
			if(matchedConverters != null) {
				for((sourceType, converter) in matchedConverters) {
					if(sourceType.isAssignableFrom(value.javaClass)) {
						return (converter as Converter<Any, Any>).convert(value) as T
					}
				}
			}
			throw IllegalStateException("Cannot convert '${value}' to type '${targetType.name}'.")
		} catch(e: Exception) {
			throw IllegalStateException("Cannot convert '${value}' to type '${targetType.name}'.")
		}
	}

	inline fun <reified T : Any> convertOrNull(value: Any?): T? {
		return convert(value, T::class.java)
	}

	fun <T : Any> convertOrNull(value: Any?, targetType: Class<T>): T? {
		try {
			if(value == null) return null
			val matchedConverters = converters[targetType]
			if(matchedConverters != null) {
				for((sourceType, converter) in matchedConverters) {
					if(sourceType.isAssignableFrom(value.javaClass)) {
						return (converter as Converter<Any, Any>).convertOrNull(value) as T
					}
				}
			}
			return null
		} catch(e: Exception) {
			return null
		}
	}

	private fun registerDefaultConverters() {
		register(object : Converter<Any, String> {
			override fun convert(value: Any) = value.toString()
			override fun convertOrNull(value: Any) = value.toString()
		})

		register(object : Converter<String, Int> {
			override fun convert(value: String) = value.toInt()
			override fun convertOrNull(value: String) = value.toIntOrNull()
		})
		register(object : Converter<String, Long> {
			override fun convert(value: String) = value.toLong()
			override fun convertOrNull(value: String) = value.toLongOrNull()
		})
		register(object : Converter<String, Float> {
			override fun convert(value: String) = value.toFloat()
			override fun convertOrNull(value: String) = value.toFloatOrNull()
		})
		register(object : Converter<String, Double> {
			override fun convert(value: String) = value.toDouble()
			override fun convertOrNull(value: String) = value.toDoubleOrNull()
		})
		register(object : Converter<String, Byte> {
			override fun convert(value: String) = value.toByte()
			override fun convertOrNull(value: String) = value.toByteOrNull()
		})
		register(object : Converter<String, Short> {
			override fun convert(value: String) = value.toShort()
			override fun convertOrNull(value: String) = value.toShortOrNull()
		})
		register(object : Converter<String, Char> {
			override fun convert(value: String) = value.toChar()
			override fun convertOrNull(value: String) = value.toCharOrNull()
		})
		register(object : Converter<String, Boolean> {
			override fun convert(value: String) = value.toBoolean()
			override fun convertOrNull(value: String) = value.toBooleanOrNull()
		})

		register(object:Converter<Int,Long>{
			override fun convert(value: Int) = value.toLong()
		})
		register(object:Converter<Int,Float>{
			override fun convert(value: Int) = value.toFloat()
		})
		register(object:Converter<Int,Double>{
			override fun convert(value: Int) = value.toDouble()
		})
		register(object:Converter<Int,Byte>{
			override fun convert(value: Int) = value.toByte()
		})
		register(object:Converter<Int,Short>{
			override fun convert(value: Int) = value.toShort()
		})
		register(object:Converter<Int,Char>{
			override fun convert(value: Int) = value.toChar()
		})

		register(object:Converter<Long,Int>{
			override fun convert(value: Long) = value.toInt()
		})
		register(object:Converter<Long,Float>{
			override fun convert(value: Long) = value.toFloat()
		})
		register(object:Converter<Long,Double>{
			override fun convert(value: Long) = value.toDouble()
		})
		register(object:Converter<Long,Byte>{
			override fun convert(value: Long) = value.toByte()
		})
		register(object:Converter<Long,Short>{
			override fun convert(value: Long) = value.toShort()
		})
		register(object:Converter<Long,Char>{
			override fun convert(value: Long) = value.toChar()
		})

		register(object:Converter<Float,Int>{
			override fun convert(value: Float) = value.toInt()
		})
		register(object:Converter<Float,Long>{
			override fun convert(value: Float) = value.toLong()
		})
		register(object:Converter<Float,Double>{
			override fun convert(value: Float) = value.toDouble()
		})
		register(object:Converter<Float,Byte>{
			@Suppress("DEPRECATION")
			override fun convert(value: Float) = value.toByte()
		})
		register(object:Converter<Float,Short>{
			@Suppress("DEPRECATION")
			override fun convert(value: Float) = value.toShort()
		})
		register(object:Converter<Float,Char>{
			override fun convert(value: Float) = value.toChar()
		})

		register(object:Converter<Double,Int>{
			override fun convert(value: Double) = value.toInt()
		})
		register(object:Converter<Double,Long>{
			override fun convert(value: Double) = value.toLong()
		})
		register(object:Converter<Double,Float>{
			override fun convert(value: Double) = value.toFloat()
		})
		register(object:Converter<Double,Byte>{
			@Suppress("DEPRECATION")
			override fun convert(value: Double) = value.toByte()
		})
		register(object:Converter<Double,Short>{
			@Suppress("DEPRECATION")
			override fun convert(value: Double) = value.toShort()
		})
		register(object:Converter<Double,Char>{
			override fun convert(value: Double) = value.toChar()
		})

		register(object:Converter<Byte,Int>{
			override fun convert(value: Byte) = value.toInt()
		})
		register(object:Converter<Byte,Long>{
			override fun convert(value: Byte) = value.toLong()
		})
		register(object:Converter<Byte,Float>{
			override fun convert(value: Byte) = value.toFloat()
		})
		register(object:Converter<Byte,Double>{
			override fun convert(value: Byte) = value.toDouble()
		})
		register(object:Converter<Byte,Short>{
			override fun convert(value: Byte) = value.toShort()
		})
		register(object:Converter<Byte,Char>{
			override fun convert(value: Byte) = value.toChar()
		})

		register(object:Converter<Short,Int>{
			override fun convert(value: Short) = value.toInt()
		})
		register(object:Converter<Short,Long>{
			override fun convert(value: Short) = value.toLong()
		})
		register(object:Converter<Short,Float>{
			override fun convert(value: Short) = value.toFloat()
		})
		register(object:Converter<Short,Double>{
			override fun convert(value: Short) = value.toDouble()
		})
		register(object:Converter<Short,Byte>{
			override fun convert(value: Short) = value.toByte()
		})
		register(object:Converter<Short,Char>{
			override fun convert(value: Short) = value.toChar()
		})

		register(object:Converter<Char,Int>{
			override fun convert(value: Char) = value.toInt()
		})
		register(object:Converter<Char,Long>{
			override fun convert(value: Char) = value.toLong()
		})
		register(object:Converter<Char,Float>{
			override fun convert(value: Char) = value.toFloat()
		})
		register(object:Converter<Char,Double>{
			override fun convert(value: Char) = value.toDouble()
		})
		register(object:Converter<Char,Byte>{
			override fun convert(value: Char) = value.toByte()
		})
		register(object:Converter<Char,Short>{
			override fun convert(value: Char) = value.toShort()
		})
	}

	private fun registerExtendedConverters(){
		register(object : Converter<String, Regex> {
			override fun convert(value: String) = value.toRegex()
		})
		register(object : Converter<String, File> {
			override fun convert(value: String) = value.toFile()
		})
		register(object : Converter<String, Path> {
			override fun convert(value: String) = value.toPath()
		})
		register(object : Converter<String, URL> {
			override fun convert(value: String) = value.toUrl()
		})
		register(object : Converter<String, URI> {
			override fun convert(value: String) = value.toUri()
		})
		register(object : Converter<String, Charset> {
			override fun convert(value: String) = value.toCharset()
			override fun convertOrNull(value: String) = value.toCharsetOrNull()
		})
		register(object : Converter<String, Class<*>> {
			override fun convert(value: String) = value.toClass()
			override fun convertOrNull(value: String) = value.toClassOrNull()
		})
	}
}
