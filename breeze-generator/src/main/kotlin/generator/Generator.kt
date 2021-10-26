// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.generator

interface Generator<out T> {
	fun generate(): T
}
