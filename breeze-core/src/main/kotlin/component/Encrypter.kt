// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

import com.windea.breezeframework.core.annotations.*
import java.security.*

/**
 * 加密器。
 *
 * 加密器用于根据指定的加密算法，对字符串进行加密和解密，某些算法可能不支持解密。
 */
@BreezeComponent
interface Encrypter {
	/**
	 * 加密指定的字节数组。
	 */
	fun encrypt(value:ByteArray):ByteArray

	/**
	 * 解密指定的字节数组。某些算法可能不支持解密。
	 */
	fun decrypt(value:ByteArray):ByteArray

	companion object{
		//不需要进行注册
	}

	/**
	 * 基于消息摘要的加密器。不支持解密。
	 */
	abstract class MessageDigestEncrypter(
		algorithm:String
	):Encrypter{
		private val delegate = MessageDigest.getInstance(algorithm)

		override fun encrypt(value: ByteArray): ByteArray {
			return delegate.digest(value)
		}

		override fun decrypt(value: ByteArray): ByteArray {
			throw UnsupportedOperationException("Decrypt operation is not supported by message digest encrypter.")
		}
	}

	/**
	 * 基于MD5加密算法的加密器。
	 */
	object Md5Encrypter:MessageDigestEncrypter("MD5")

	/**
	 * 基于SHA-1加密算法的加密器。
	 */
	object Sha1Encrypter:MessageDigestEncrypter("SHA-1")

	/**
	 * 基于SHA-256加密算法的加密器。
	 */
	object Sha256Encrypter:MessageDigestEncrypter("SHA-256")

	/**
	 * 基于SHA3-512加密算法的加密器。
	 */
	object Sha512Encrypter:MessageDigestEncrypter("SHA3-512")

	/**
	 * 基于SHA3-256加密算法的加密器。
	 */
	object Sha3256Encrypter:MessageDigestEncrypter("SHA3-256")

	/**
	 * 基于SHA3-512加密算法的加密器。
	 */
	object Sha3512Encrypter:MessageDigestEncrypter("SHA3-512")
}
