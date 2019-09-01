package com.windea.breezeframework.core

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.core.generators.text.*
import org.junit.*

class GeneratorTests {
	@Test
	fun test1() {
		val path = "D:\\OneDrive\\My Documents\\My Projects\\Java\\Utility\\Kotlin-Utility\\src\\test\\resources\\Annotation.yml"
		val outputPath = path.replace(".yml", ".xml")
		IdeaConfigGenerator.generateYamlAnnotation(path.toFile(), outputPath.toFile())
	}
}
