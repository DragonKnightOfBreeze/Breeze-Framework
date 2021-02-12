// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.api

/**
 * 包含有可被视为转化的子元素。
 */
@DslApiMarker
interface WithTransition<in N : WithId, T : WithNode> {
	/**根据节点元素创建过渡元素。*/
	@DslApiMarker
	infix fun String.links(other: String): T

	/**根据节点元素创建过渡元素。*/
	@DslApiMarker
	infix fun String.links(other: N): T = this@WithTransition.run { this@links links other.id }

	/**根据节点元素创建过渡元素。*/
	@DslApiMarker
	infix fun N.links(other: String): T = this@WithTransition.run { this@links.id links other }

	/**根据节点元素创建过渡元素。*/
	@DslApiMarker
	infix fun N.links(other: N): T = this@WithTransition.run { this@links.id links other.id }

	/**根据节点元素连续创建过渡元素。*/
	@DslApiMarker
	infix fun T.links(other: String): T = this@WithTransition.run { this@links.targetNodeId links other }

	/**根据节点元素连续创建过渡元素。*/
	@DslApiMarker
	infix fun T.links(other: N): T = this@WithTransition.run { this@links.targetNodeId links other.id }
}
