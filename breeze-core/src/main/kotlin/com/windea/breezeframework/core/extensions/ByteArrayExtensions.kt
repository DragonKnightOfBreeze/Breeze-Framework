package com.windea.breezeframework.core.extensions

import java.util.*

/**将当前字节数组编码为base64格式的字节数组。*/
fun ByteArray.encodeToBase64(): ByteArray = Base64.getEncoder().encode(this)

/**将当前字节数组编码为base64格式的字符串。*/
fun ByteArray.encodeToBase64String(): String = Base64.getEncoder().encodeToString(this)

/**将当前字节数组解码为base64格式的字符串。*/
fun ByteArray.decodeToBase64(): ByteArray = Base64.getDecoder().decode(this)
