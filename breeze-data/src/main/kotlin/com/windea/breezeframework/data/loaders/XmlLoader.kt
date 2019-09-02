package com.windea.breezeframework.data.loaders

import com.windea.breezeframework.data.loaders.impl.*

/**Xml数据读取器的接口。*/
interface XmlLoader : DataLoader {
	companion object {
		/**得到一个单例实例。*/
		val instance = JacksonXmlLoader()
	}
}
