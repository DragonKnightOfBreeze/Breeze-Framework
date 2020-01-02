package com.windea.breezeframework.core.consts

/**访问系统属性。*/
object SystemProperties {
	private val properties = System.getProperties()

	/**操作系统名。*/
	@JvmField
	val osName: String? = properties.getProperty("os.name")
	/**用户名。*/
	@JvmField
	val userName: String? = properties.getProperty("user.name")
	/**用户首页目录。*/
	@JvmField
	val userHome: String? = properties.getProperty("user.home")
	/**用户目录。即，项目的当前工作路径。*/
	@JvmField
	val userDir: String? = properties.getProperty("user.dir")
	/**用户所属国家。*/
	@JvmField
	val userCountry: String? = properties.getProperty("user.country")
	/**用户所用语言。*/
	@JvmField
	val userLanguage: String? = properties.getProperty("user.language")
	/**文件分隔符。*/
	@JvmField
	val fileSeparator: String? = properties.getProperty("file.separator")
	/**文件编码。*/
	@JvmField
	val fileEncoding: String? = properties.getProperty("file.encoding")
	/**行分隔符。*/
	@JvmField
	val lineSeparator: String? = properties.getProperty("line.separator")

	/**得到指定的系统属性。*/
	@JvmStatic
	operator fun get(name: String): String? = properties.getProperty(name)
}
