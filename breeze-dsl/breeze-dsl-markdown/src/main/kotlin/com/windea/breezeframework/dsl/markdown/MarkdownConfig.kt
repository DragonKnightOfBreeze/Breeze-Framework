package com.windea.breezeframework.dsl.markdown

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*

/**Markdown配置。*/
@MarkdownDsl
object MarkdownConfig : DslConfig {
	private val indentSizeRange = -2..8
	private val wrapLengthRange = 60..240
	private val repeatableMarkerCountRange = 3..12
	private val listNodeMarkerArray = charArrayOf('*', '-', '+')
	private val horizontalListMarkerArray = charArrayOf('*', '-', '_')
	private val codeFenceMarkerArray = charArrayOf('`', '~')

	var indentSize:Int = 4
		set(value) = run { if(value in indentSizeRange) field = value }
	var preferDoubleQuote:Boolean = true
	var truncated:String = "..."
	var wrapLength:Int = 120
		set(value) = run { if(value in wrapLengthRange) field = value }
	var addPrefixHeadingMarkers:Boolean = false
	var repeatableMarkerCount:Int = 3
		set(value) = run { if(value in repeatableMarkerCountRange) field = value }
	var listNodeMarker:Char = '*'
		set(value) = run { if(value in listNodeMarkerArray) field = value }
	var horizontalLineMarker:Char = '*'
		set(value) = run { if(value in horizontalListMarkerArray) field = value }
	var codeFenceMarker:Char = '`'
		set(value) = run { if(value in codeFenceMarkerArray) field = value }

	@PublishedApi internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	@PublishedApi internal val quote get() = if(preferDoubleQuote) '\"' else '\''

	@PublishedApi internal val horizontalLineMarkers get() = horizontalLineMarker * repeatableMarkerCount
	@PublishedApi internal val codeFenceMarkers get() = codeFenceMarker * repeatableMarkerCount
	@PublishedApi internal val emptyColumnLength = 4
	@PublishedApi internal val emptyColumnText = " " * emptyColumnLength
}
