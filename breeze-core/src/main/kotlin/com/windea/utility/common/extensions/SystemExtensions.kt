package com.windea.utility.common.extensions

object SystemExtensions {
	/**项目的实际工作路径。*/
	val workDirectory: String? get() = System.getProperty("user.dir")
	
	/**项目的实际工作平台名。例如：Windows 10。*/
	val platformName: String? get() = System.getProperty("os.name")
	
	/**项目工作环境的用户名。*/
	val userName: String? get() = System.getProperty("user.name")
	
	/**项目工作环境的用户所在国家。*/
	val userCountry: String? get() = System.getProperty("user.country")
	
	/**项目工作环境的用户所用语言。*/
	val userLanguage: String? get() = System.getProperty("user.language")
	
	/**项目工作环境的文件分隔符。*/
	val fileSeparator: String? get() = System.getProperty("file.separator")
	
	/**项目工作环境的文件编码。*/
	val fileEncoding: String? get() = System.getProperty("file.encoding")
	
	/**项目工作环境的行分隔符。*/
	val lineSeparator: String? get() = System.getProperty("line.separator")
}
