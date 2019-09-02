package com.windea.breezeframework.data.loaders

import com.windea.breezeframework.data.loaders.impl.*

/**Properties数据读取器的接口。*/
interface PropertiesLoader : DataLoader {
	companion object {
		/**得到一个单例实例。*/
		val instance = JacksonPropertiesLoader()
	}
}
