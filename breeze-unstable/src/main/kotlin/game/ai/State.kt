/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.game.ai

/**状态。*/
interface State<S> {
	/**状态的名字。*/
	val name: String

	/**是否为子状态。*/
	val isSubState: Boolean

	/**是否为并发的。当仅当为子状态时，允许与其他子状态同时激活。*/
	val isConcurrent: Boolean

	/**创建该状态时的事件。*/
	fun onCreate() {}

	/**销毁该状态时的事件。*/
	fun onDestroy() {}

	/**进入该状态时的事件。*/
	fun onEnter(previousState: S?) {}

	/**退出该状态时的事件。*/
	fun onExit(nextState: S?) {}
}
