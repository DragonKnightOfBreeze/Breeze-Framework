// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.domain

/**Sets of common-used system properties.*/
object SystemProperties {
	/**The work directory of current project.*/
	@JvmField val workDirectory:String = System.getProperty("user.dir")

	/**The work platform of current project.*/
	@JvmField val workPlatform:String = System.getProperty("os.name")

	/**The user's name of current work platform.*/
	@JvmField val userName:String = System.getProperty("user.name")

	/**The user's country of current work platform.*/
	@JvmField val userCountry:String = System.getProperty("user.country")

	/**The user's language of current work platform.*/
	@JvmField val userLanguage:String = System.getProperty("user.language")

	/**The file separator of current work platform. Normally be `"\r"`, `"\n"` or `"\r\n"`.*/
	@JvmField val fileSeparator:String = System.getProperty("file.separator")

	/**The file encoding of current work platform. Normally be `"utf-8"`.*/
	@JvmField val fileEncoding:String = System.getProperty("file.encoding")

	/**The file encoding of current work platform. Normally be `"/"` or `"\\"`.*/
	@JvmField val lineSeparator:String = System.getProperty("line.separator")
}
