package com.windea.breezeframework.core.loaders

import com.windea.breezeframework.core.loaders.impl.*

/**Json数据读取器的接口。*/
interface JsonLoader : DataLoader {
	companion object {
		/**得到一个单例实例。*/
		val instance = GsonJsonLoader()
	}
}
