// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.core.extension.*
import java.net.*
import java.nio.charset.*
import java.util.*

/**
 * 编码器。
 *
 * 编码器用于对字符串进行编码和解码，
 */
@UnstableApi
@BreezeComponent
interface Encoder {
	/**
	 * 编码指定的字符串，以指定的字符集显示。
	 */
	fun encode(value: String, charset: Charset = Charsets.UTF_8): String

	/**
	 * 解码指定的字符串，以指定的字符集显示。
	 */
	fun decode(value: String, charset: Charset = Charsets.UTF_8): String

	//region Default Encoders
	/**
	 * Base64编码器。
	 */
	object Base64Encoder : Encoder {
		override fun encode(value: String, charset: Charset): String {
			return Base64.getEncoder().encodeToString(value.toByteArray(charset))
		}

		override fun decode(value: String, charset: Charset): String {
			return Base64.getDecoder().decode(value).let { String(it, charset) }
		}
	}

	/**
	 * Url安全的Base64编码器。
	 */
	object Base64UrlEncoder : Encoder {
		override fun encode(value: String, charset: Charset): String {
			return Base64.getUrlEncoder().encodeToString(value.toByteArray(charset))
		}

		override fun decode(value: String, charset: Charset): String {
			return Base64.getUrlDecoder().decode(value).let { String(it, charset) }
		}
	}

	/**
	 * 基于Mime类型的Base64编码器。
	 */
	object Base64MimeEncoder : Encoder {
		override fun encode(value: String, charset: Charset): String {
			return Base64.getMimeEncoder().encodeToString(value.toByteArray(charset))
		}

		override fun decode(value: String, charset: Charset): String {
			return Base64.getMimeDecoder().decode(value).let { String(it, charset) }
		}
	}

	/**
	 * Url编码器。
	 */
	object UrlEncoder : Encoder {
		override fun encode(value: String, charset: Charset): String {
			return URLEncoder.encode(value, charset)
		}

		override fun decode(value: String, charset: Charset): String {
			return URLDecoder.decode(value, charset)
		}
	}

	/**
	 * 十六进制编码器。
	 */
	object HexEncoder : Encoder {
		override fun encode(value: String, charset: Charset): String {
			return value.hex(charset)
		}

		override fun decode(value: String, charset: Charset): String {
			return value.unhex(charset)
		}
	}
	//endregion

	companion object {
		private val encoders = mutableListOf<Encoder>()

		/**
		 * 得到已注册的编码器列表。
		 */
		@JvmStatic fun values(): List<Encoder> {
			return encoders
		}

		/**
		 *  注册指定的编码器。
		 */
		@JvmStatic fun register(encoder: Encoder) {
			encoders.add(encoder)
		}

		init {
			registerDefaultEncoders()
		}

		private fun registerDefaultEncoders() {
			register(Base64Encoder)
			register(Base64UrlEncoder)
			register(Base64MimeEncoder)
			register(UrlEncoder)
			register(HexEncoder)
		}
	}
}
