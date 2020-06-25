package com.windea.breezeframework.dsl

/**设置是否缩进内容。*/
@DslApiMarker
infix fun <T : Indentable> T.indent(value:Boolean) = apply { indentContent = value }

/**设置是否换行内容。*/
@DslApiMarker
infix fun <T : Wrappable> T.wrap(value:Boolean) = apply { wrapContent = value }

/**设置是否分割内容。*/
@DslApiMarker
infix fun <T : Splitable> T.split(value:Boolean) = apply { splitContent = value }

/**设置是否生成内容。*/
@DslApiMarker
infix fun <T : Generatable> T.generate(value:Boolean) = apply { generateContent = value }