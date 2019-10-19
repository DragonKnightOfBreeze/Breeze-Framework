package com.windea.breezeframework.text.extensions

import java.net.*
import java.util.*

/**将字节数组编码成Base64格式。*/
fun ByteArray.base64Encode(): ByteArray = Base64.getEncoder().encode(this)

/**将字节数组从Base64格式解码。*/
fun ByteArray.base64Decode(): ByteArray = Base64.getDecoder().decode(this)


/**对字符串进行url编码。*/
fun String.urlEncode(charset: String = "UTF-8"): String = URLEncoder.encode(this, charset)

/**对字符串进行url解码。*/
fun String.urlDecode(charset: String = "UTF-8"): String = URLDecoder.decode(this, charset)
