package com.windea.utility.common.loaders

import com.windea.utility.common.loaders.impl.*

/**Json数据读取器的接口。*/
interface JsonLoader : DataLoader {
	companion object {
		/**得到一个单例实例。*/
		val instance = GsonJsonLoader()
	}
}
