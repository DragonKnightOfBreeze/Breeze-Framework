// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.annotation.*
import java.security.*
import javax.crypto.*
import javax.crypto.spec.*

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

	override fun componentCopy(componentParams: Map<String, Any?>): Encrypter {
		throw UnsupportedOperationException("Cannot copy component of type: ${javaClass.name}.")
	}
}

abstract class AbstractEncrypter : Encrypter {
	override fun equals(other: Any?) = componentEquals(other)

	override fun hashCode() = componentHashcode()

	override fun toString() = componentToString()
}

object Encrypters : ComponentRegistry<Encrypter>() {
	//region implementations
	/**
	 * 基于密码器的加密器。支持解密。需要指定密钥。
	 */
	open class CipherEncrypter(
		val transformation: String
	) : AbstractEncrypter() {
		private val cipher = Cipher.getInstance(transformation)

		//处理字符串时，需要进行base64编码

		override fun encrypt(value: ByteArray, secret: ByteArray?): ByteArray {
			requireNotNull(secret)
			val keySpec = SecretKeySpec(secret, transformation)
			//使用CBC模式时，这里需要添加额外参数
			if("CBC" in transformation) {
				cipher.init(Cipher.ENCRYPT_MODE, keySpec, IvParameterSpec(secret))
			} else {
				cipher.init(Cipher.ENCRYPT_MODE, keySpec)
			}
			return cipher.doFinal(value)
		}

		override fun decrypt(value: ByteArray, secret: ByteArray?): ByteArray {
			requireNotNull(secret)
			val keySpec = SecretKeySpec(secret, transformation)
			//使用CBC模式时，这里需要添加额外参数
			if("CBC" in transformation) {
				cipher.init(Cipher.DECRYPT_MODE, keySpec, IvParameterSpec(secret))
			} else {
				cipher.init(Cipher.DECRYPT_MODE, keySpec)
			}
			return cipher.doFinal(value)
		}
	}

	/**
	 * 基于DES加密算法的加密器。使用8字节的密钥。
	 *
	 * @see CipherEncrypter
	 */
	object DesEncrypter : CipherEncrypter("DES")

	/**
	 * 基于AES加密算法的加密器。使用16字节的密钥。
	 *
	 * @see CipherEncrypter
	 */
	object AesEncrypter : CipherEncrypter("AES")

	/**
	 * 基于消息摘要的加密器。不支持解密。不需要指定密钥。
	 */
	open class MessageDigestEncrypter(
		val algorithm: String
	) : AbstractEncrypter() {
		private val messageDigest = MessageDigest.getInstance(algorithm)

		override fun encrypt(value: ByteArray, secret: ByteArray?): ByteArray {
			return messageDigest.digest(value)
		}

		override fun decrypt(value: ByteArray, secret: ByteArray?): ByteArray {
			throw UnsupportedOperationException("Decrypt operation is not supported by message digest encrypter.")
		}
	}

	/**
	 * 基于MD5加密算法的加密器。
	 *
	 * @see MessageDigestEncrypter
	 */
	object MD5Encrypter : MessageDigestEncrypter("MD5")

	/**
	 * 基于SHA-1加密算法的加密器。
	 *
	 * @see MessageDigestEncrypter
	 */
	object SHA1Encrypter : MessageDigestEncrypter("SHA-1")

	/**
	 * 基于SHA-256加密算法的加密器。
	 *
	 * @see MessageDigestEncrypter
	 */
	object SHA256Encrypter : MessageDigestEncrypter("SHA-256")

	/**
	 * 基于SHA3-512加密算法的加密器。
	 *
	 * @see MessageDigestEncrypter
	 */
	object SHA512Encrypter : MessageDigestEncrypter("SHA3-512")

	/**
	 * 基于SHA3-256加密算法的加密器。
	 *
	 * @see MessageDigestEncrypter
	 */
	object SHA3256Encrypter : MessageDigestEncrypter("SHA3-256")

	/**
	 * 基于SHA3-512加密算法的加密器。
	 *
	 * @see MessageDigestEncrypter
	 */
	object SHA3512Encrypter : MessageDigestEncrypter("SHA3-512")

	//TODO 数字签名
	//endregion

	override fun registerDefault() {
		register(DesEncrypter)
		register(AesEncrypter)
		register(MD5Encrypter)
		register(SHA1Encrypter)
		register(SHA256Encrypter)
		register(SHA512Encrypter)
		register(SHA3256Encrypter)
		register(SHA3512Encrypter)
	}
}

