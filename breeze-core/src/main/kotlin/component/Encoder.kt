// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

import com.windea.breezeframework.core.annotations.*
import java.net.*
import java.nio.charset.*
import java.util.*

/**
 * 编码器。
 *
 * 编码器用于对字符串和二级制数据进行编码和解码，
 */
@BreezeComponent
interface Encoder {
	fun encode(value:String,charset:Charset=Charsets.UTF_8):String

	fun decode(value:String,charset:Charset=Charsets.UTF_8):String

	companion object{
		//不需要进行注册
	}

	//region Specific Encoders
	/**
	 * Base64编码器。
	 */
	object Base64Encoder:Encoder{
		override fun encode(value: String, charset: Charset): String {
			return Base64.getEncoder().encodeToString(value.toByteArray(charset))
		}

		override fun decode(value: String, charset: Charset): String {
			return Base64.getDecoder().decode(value).let { String(it,charset) }
		}
	}

	/**
	 * Url安全的Base64编码器。
	 */
	object Base64UrlEncoder:Encoder{
		override fun encode(value: String, charset: Charset): String {
			return Base64.getUrlEncoder().encodeToString(value.toByteArray(charset))
		}

		override fun decode(value: String, charset: Charset): String {
			return Base64.getUrlDecoder().decode(value).let { String(it,charset) }
		}
	}

	/**
	 * 基于Mime类型的Base64编码器。
	 */
	object Base64MimeEncoder:Encoder{
		override fun encode(value: String, charset: Charset): String {
			return Base64.getMimeEncoder().encodeToString(value.toByteArray(charset))
		}

		override fun decode(value: String, charset: Charset): String {
			return Base64.getMimeDecoder().decode(value).let { String(it,charset) }
		}
	}

	/**
	 * Url编码器。
	 */
	object UrlEncoder:Encoder{
		override fun encode(value: String, charset: Charset): String {
			return URLEncoder.encode(value,charset)
		}

		override fun decode(value: String, charset: Charset): String {
			return URLDecoder.decode(value,charset)
		}
	}
	//endregion

	//region Encrypt Encoders
	/**
	 * Md5加密方式的编码器。
	 */
	object Md5Encoder:Encoder{
		override fun encode(value: String, charset: Charset): String {
			TODO()
		}

		override fun decode(value: String, charset: Charset): String {
			TODO()
		}
	}

	/**
	 * AES加密方式的编码器。
	 */
	object AesEncoder:Encoder{
		override fun encode(value: String, charset: Charset): String {
			TODO()
		}

		override fun decode(value: String, charset: Charset): String {
			TODO()
		}
	}

	/**
	 * DES加密方式的编码器。
	 */
	object DesEncoder:Encoder{
		override fun encode(value: String, charset: Charset): String {
			TODO()
		}

		override fun decode(value: String, charset: Charset): String {
			TODO()
		}
	}

	/**
	 * SHA256加密方式的编码器。
	 */
	object Sha256Encoder:Encoder{
		override fun encode(value: String, charset: Charset): String {
			TODO()
		}

		override fun decode(value: String, charset: Charset): String {
			TODO()
		}
	}

	/**
	 * SHA512加密方式的编码器。
	 */
	object Sha512Encoder:Encoder{
		override fun encode(value: String, charset: Charset): String {
			TODO()
		}

		override fun decode(value: String, charset: Charset): String {
			TODO()
		}
	}
	//endregion
}
