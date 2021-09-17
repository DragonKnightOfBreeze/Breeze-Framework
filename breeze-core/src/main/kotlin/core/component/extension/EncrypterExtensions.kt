// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("EncrypterExtensions")

package icu.windea.breezeframework.core.component.extension

import icu.windea.breezeframework.core.annotation.UnstableApi
import icu.windea.breezeframework.core.component.Encrypter

/**
 * 根据指定的加密器，加密当前字节数组。
 *
 * @see Encrypter
 */
@UnstableApi
fun ByteArray.encryptBy(encrypter: Encrypter, secret: ByteArray? = null): ByteArray {
	return encrypter.encrypt(this, secret)
}

/**
 * 根据指定的加密器，解密当前字符串。某些加密算法可能不支持解密。
 *
 * @throws UnsupportedOperationException 如果不支持解密。
 * @see Encrypter
 */
@UnstableApi
fun ByteArray.decryptBy(encrypter: Encrypter, secret: ByteArray? = null): ByteArray {
	return encrypter.decrypt(this, secret)
}
