// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import java.lang.reflect.*

/**
 * 拥有目标类型的组件。
 */
interface TypedComponent : Component {
	/**目标类型。*/
	val targetType: Type
}
