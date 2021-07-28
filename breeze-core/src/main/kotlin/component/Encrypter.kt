// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.annotation.*

/**
 * 加密器。
 *
 * 加密器用于根据指定的加密算法，对字符串进行加密和解密，某些加密算法可能不支持解密。
 */
@UnstableApi
interface Encrypter : Component {
	/**
	 * 加密指定的字节数组。
	 */
	fun encrypt(value: ByteArray, secret: ByteArray? = null): ByteArray

	/**
	 * 解密指定的字节数组。某些加密算法可能不支持解密。
	 */
	fun decrypt(value: ByteArray, secret: ByteArray? = null): ByteArray
}

