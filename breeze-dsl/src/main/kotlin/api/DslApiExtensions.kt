// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.api

/**
 * 配置是否缩进内容。
 */
@DslApiMarker
infix fun <T : Indentable> T.indent(value: Boolean): T = apply { indentContent = value }

/**
 * 配置是否换行内容。
 */
@DslApiMarker
infix fun <T : Wrappable> T.wrap(value: Boolean): T = apply { wrapContent = value }

/**
 * 配置是否分割内容。
 */
@DslApiMarker
infix fun <T : Splitable> T.split(value: Boolean): T = apply { splitContent = value }

/**
 * 配置是否生成内容。
 */
@DslApiMarker
infix fun <T : Generatable> T.generate(value: Boolean): T = apply { generateContent = value }
