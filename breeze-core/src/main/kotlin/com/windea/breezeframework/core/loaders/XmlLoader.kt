package com.windea.breezeframework.core.loaders

import com.windea.breezeframework.core.loaders.impl.*

/**Xml数据读取器的接口。*/
interface XmlLoader : DataLoader {
	companion object {
		/**得到一个单例实例。*/
		val instance = JacksonXmlLoader()
	}
}
