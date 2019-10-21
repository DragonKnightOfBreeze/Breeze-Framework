package com.windea.breezeframework.text.extensions

import java.net.*
import java.util.*

/**将字节数组编码成Base64格式。*/
fun ByteArray.base64Encoded(): ByteArray = Base64.getEncoder().encode(this)

/**将字节数组从Base64格式解码。*/
fun ByteArray.base64Decoded(): ByteArray = Base64.getDecoder().decode(this)

/**将字符串编码成Base64格式。*/
fun String.base64Encoded(): String = Base64.getEncoder().encodeToString(this.toByteArray())

/**将字符串从Base64格式解码。*/
fun String.base64Decoded(): String = Base64.getDecoder().decode(this).decodeToString()


/**对字符串进行url编码。*/
fun String.urlEncoded(charset: String = "UTF-8"): String = URLEncoder.encode(this, charset)

/**对字符串进行url解码。*/
fun String.urlDecoded(charset: String = "UTF-8"): String = URLDecoder.decode(this, charset)
