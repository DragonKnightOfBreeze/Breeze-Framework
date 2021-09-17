// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.annotation.*
import icu.windea.breezeframework.core.extension.*
import java.net.*
import java.nio.charset.*
import java.util.*

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

	override fun componentCopy(componentParams: Map<String, Any?>): Encoder {
		throw UnsupportedOperationException("Cannot copy component of type: ${javaClass.name}.")
	}
}

abstract class AbstractEncoder : Encoder {
	override fun equals(other: Any?) = componentEquals(other)

	override fun hashCode() = componentHashcode()

	override fun toString() = componentToString()
}

object Encoders : ComponentRegistry<Encoder>() {
	//region Implementations
	/**
	 * Base64编码器。
	 */
	object Base64Encoder : AbstractEncoder() {
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
	object Base64UrlEncoder : AbstractEncoder() {
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
	object Base64MimeEncoder : AbstractEncoder() {
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
	object UrlEncoder : AbstractEncoder() {
		override fun encode(value: String, charset: Charset): String {
			return URLEncoder.encode(value, charset.name())
		}

		override fun decode(value: String, charset: Charset): String {
			return URLDecoder.decode(value, charset.name())
		}
	}

	/**
	 * 十六进制编码器。
	 */
	object HexEncoder : AbstractEncoder() {
		override fun encode(value: String, charset: Charset): String {
			return value.hex(charset)
		}

		override fun decode(value: String, charset: Charset): String {
			return value.unhex(charset)
		}
	}
	//endregion

	override fun registerDefault() {
		register(Base64Encoder)
		register(Base64UrlEncoder)
		register(Base64MimeEncoder)
		register(UrlEncoder)
		register(HexEncoder)
	}
}
