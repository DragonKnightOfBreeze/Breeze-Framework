package com.windea.breezeframework.text.extensions

import java.util.*

/**将字节数组编码成Base64格式。*/
fun ByteArray.encodeToBase64(): ByteArray = Base64.getEncoder().encode(this)

/**将字节数组编码成Base64格式并转化成字符串。*/
fun ByteArray.encodeToBase64String(): String = Base64.getEncoder().encodeToString(this)

/**将字节数组从Base64格式解码。*/
fun ByteArray.decodeToBase64(): ByteArray = Base64.getDecoder().decode(this)

/**将字符串转化成字节数组并从Base64格式解码。*/
fun String.decodeToBase64ByteArray(): ByteArray = Base64.getDecoder().decode(this)
