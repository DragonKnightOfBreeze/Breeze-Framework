// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import java.security.*
import javax.crypto.*
import javax.crypto.spec.*

@Suppress("ClassName")
object Encrypters : ComponentRegistry<Encrypter>() {
	//region Implementations
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
	object Md5Encrypter : MessageDigestEncrypter("MD5")

	/**
	 * 基于SHA-1加密算法的加密器。
	 *
	 * @see MessageDigestEncrypter
	 */
	object Sha_1Encrypter : MessageDigestEncrypter("SHA-1")

	/**
	 * 基于SHA-256加密算法的加密器。
	 *
	 * @see MessageDigestEncrypter
	 */
	object Sha_256Encrypter : MessageDigestEncrypter("SHA-256")

	/**
	 * 基于SHA3-512加密算法的加密器。
	 *
	 * @see MessageDigestEncrypter
	 */
	object Sha_512Encrypter : MessageDigestEncrypter("SHA3-512")

	/**
	 * 基于SHA3-256加密算法的加密器。
	 *
	 * @see MessageDigestEncrypter
	 */
	object Sha3_256Encrypter : MessageDigestEncrypter("SHA3-256")

	/**
	 * 基于SHA3-512加密算法的加密器。
	 *
	 * @see MessageDigestEncrypter
	 */
	object Sha3_512Encrypter : MessageDigestEncrypter("SHA3-512")

	//TODO 数字签名
	//endregion

	override fun registerDefault() {
		register(DesEncrypter)
		register(AesEncrypter)
		register(Md5Encrypter)
		register(Sha_1Encrypter)
		register(Sha_256Encrypter)
		register(Sha_512Encrypter)
		register(Sha3_256Encrypter)
		register(Sha3_512Encrypter)
	}
}
