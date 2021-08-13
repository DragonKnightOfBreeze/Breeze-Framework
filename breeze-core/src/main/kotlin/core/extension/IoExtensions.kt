// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("IoExtensions")

package icu.windea.breezeframework.core.extension

import java.io.*

/**
 * Unconditionally close a <code>Closeable</code>.
 *
 * Equivalent to [Closeable.close], except any exceptions will be ignored.
 * This is typically used in finally blocks.
 */
fun Closeable?.closeQuietly(){
	try {
		this?.close()
	}catch(e:Exception){
		//ignore
	}
}
