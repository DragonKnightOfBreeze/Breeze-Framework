package com.windea.breezeframework.core.extensions

/**访问系统属性。*/
object SystemProperties {
	private val properties = System.getProperties()
	
	/**操作系统名。*/
	val osName: String? = this["os.name"]
	/**用户名。*/
	val userName: String? = this["user.name"]
	/**用户首页目录。*/
	val userHome: String? = this["user.home"]
	/**用户目录。即，项目的当前工作路径。*/
	val userDir: String? = this["user.dir"]
	/**用户所属国家。*/
	val userCountry: String? = this["user.country"]
	/**用户所用语言。*/
	val userLanguage: String? = this["user.language"]
	/**文件分隔符。*/
	val fileSeparator: String? = this["file.separator"]
	/**文件编码。*/
	val fileEncoding: String? = this["file.encoding"]
	/**行分隔符。*/
	val lineSeparator: String? = this["line.separator"]
	
	/**得到指定的系统属性。*/
	operator fun get(name: String): String? = properties.getProperty(name)
}
