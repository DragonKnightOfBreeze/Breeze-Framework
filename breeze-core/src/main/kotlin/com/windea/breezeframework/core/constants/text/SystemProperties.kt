package com.windea.breezeframework.core.constants.text

/**系统属性集。*/
object SystemProperties {
	/**项目的工作路径。*/
	@JvmField val workDirectory: String = System.getProperty("user.dir")

	/**项目的工作平台。*/
	@JvmField val workPlatform: String = System.getProperty("os.name")

	/**项目工作环境的用户名。*/
	@JvmField val userName: String = System.getProperty("user.name")

	/**项目工作环境的用户所在国家。*/
	@JvmField val userCountry: String = System.getProperty("user.country")

	/**项目工作环境的用户所用语言。*/
	@JvmField val userLanguage: String = System.getProperty("user.language")

	/**项目工作环境的文件分隔符。*/
	@JvmField val fileSeparator: String = System.getProperty("file.separator")

	/**项目工作环境的文件编码。*/
	@JvmField val fileEncoding: String = System.getProperty("file.encoding")

	/**项目工作环境的行分隔符。*/
	@JvmField val lineSeparator: String = System.getProperty("line.separator")
}
