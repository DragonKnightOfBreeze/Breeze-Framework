// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization

class SerializationException @JvmOverloads constructor(
	message: String? = null,
	cause: Throwable? = null
) : RuntimeException(message, cause)
