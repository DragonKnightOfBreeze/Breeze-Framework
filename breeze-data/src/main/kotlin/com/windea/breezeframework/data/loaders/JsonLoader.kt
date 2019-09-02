package com.windea.breezeframework.data.loaders

import com.windea.breezeframework.data.loaders.impl.*

/**Json数据读取器的接口。*/
interface JsonLoader : DataLoader {
	companion object {
		/**得到一个单例实例。*/
		val instance = GsonJsonLoader()
	}
}
