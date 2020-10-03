// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.markdown

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*

/**
 * Dsl config of [MarkdownDsl].
 */
@MarkdownDslMarker
object MarkdownDslConfig : DslConfig {
	//init {
	//	require(listNodeMarker in charArrayOf('*', '-', '+'))
	//	require(horizontalLineMarker in charArrayOf('*', '-', '_'))
	//	require(codeFenceMarker in charArrayOf('`', '~'))
	//	require(markerCount in 3..12)
	//	require(wrapLength in 60..240)
	//}

	var indent: String = "  "
	var truncated: String = "..."
	var listNodeMarker: Char = '*'
	var horizontalLineMarker: Char = '*'
	var codeFenceMarker: Char = '`'
	var doubleQuoted: Boolean = true
	var addPrefixHeadingMarkers: Boolean = false
	var markerCount: Int = 3
	var emptyColumnLength: Int = 3
	var wrapLength: Int = 120

	internal val quote get() = if(doubleQuoted) '\"' else '\''
	internal val horizontalLineMarkers get() = horizontalLineMarker.repeat(markerCount)
	internal val codeFenceMarkers get() = codeFenceMarker.repeat(markerCount)
	internal val emptyColumnText: String get() = " ".repeat(emptyColumnLength)
	internal val emptyColumnSeparatorText: String = "-".repeat(emptyColumnLength)
}
