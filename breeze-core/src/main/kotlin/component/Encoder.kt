// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.annotation.*
import java.nio.charset.*

/**
 * 编码器。
 *
 * 编码器用于对字符串进行编码和解码，
 */
@UnstableApi
interface Encoder : Component {
	/**
	 * 编码指定的字符串，以指定的字符集显示。
	 */
	fun encode(value: String, charset: Charset = Charsets.UTF_8): String

	/**
	 * 解码指定的字符串，以指定的字符集显示。
	 */
	fun decode(value: String, charset: Charset = Charsets.UTF_8): String
}

