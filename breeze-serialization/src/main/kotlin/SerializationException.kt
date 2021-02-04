// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

class SerializationException @JvmOverloads constructor(
	message: String? = null,
	cause: Throwable? = null
) : RuntimeException(message, cause)
