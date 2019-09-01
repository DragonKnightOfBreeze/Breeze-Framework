package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.domain.*
import org.junit.Test
import kotlin.test.*

class AnnotationExtensionsKtTest {
	@Test //TESTED
	fun getAnnotatedValues() {
		assertEquals(::a.annotatedValues.name(), "A")
		assertEquals(::a.annotatedValues.tags(), listOf("A"))
		assertEquals(::a.annotatedValues.summary("zh_CN"), "A")
		assertEquals(::a.annotatedValues.description("en"), "A")
	}
}

@Name("A")
@Tags(["A"])
@Summary("A")
@Description("A", LocaleType.English)
val a = "A"
