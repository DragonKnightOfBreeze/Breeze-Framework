// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("EncoderExtensions")

package icu.windea.breezeframework.core.component.extension

import icu.windea.breezeframework.core.annotation.UnstableApi
import icu.windea.breezeframework.core.component.Encoder
import java.nio.charset.Charset

/**
 * 根据指定的编码器，编码当前字符串，以指定的字符集显示。
 *
 * @see Encoder
 */
@UnstableApi
fun String.encodeBy(encoder: Encoder, charset: Charset = Charsets.UTF_8): String {
	return encoder.encode(this, charset)
}

/**
 * 根据指定的编码器，解码当前字符串，以指定的字符集显示。
 *
 * @see Encoder
 */
@UnstableApi
fun String.decodeBy(encoder: Encoder, charset: Charset = Charsets.UTF_8): String {
	return encoder.decode(this, charset)
}
