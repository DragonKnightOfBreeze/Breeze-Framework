@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

//region operator overrides
/**@see com.windea.breezeframework.core.extensions.repeat */
inline operator fun Char.times(n: Int): String = String(CharArray(n) { this })
//endregion

//region other extensions
/**重复当前字符到指定次数。*/
fun Char.repeat(n: Int): String = String(CharArray(n) { this })
//endregion
