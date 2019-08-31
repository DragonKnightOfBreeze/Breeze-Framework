@file:Suppress("SpellCheckingInspection")

import org.gradle.kotlin.dsl.*

plugins {
	id("org.jetbrains.kotlin.jvm")
}

dependencies {
	module("com.windea.breezeframework:breeze-core")
}
