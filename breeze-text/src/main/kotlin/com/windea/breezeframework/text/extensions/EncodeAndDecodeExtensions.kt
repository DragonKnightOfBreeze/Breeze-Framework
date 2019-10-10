package com.windea.breezeframework.text.extensions

import java.util.*

/**将字节数组编码成Base64格式。*/
fun ByteArray.encodeBase64(): ByteArray = Base64.getEncoder().encode(this)

/**将字节数组从Base64格式解码。*/
fun ByteArray.decodeBase64(): ByteArray = Base64.getDecoder().decode(this)
