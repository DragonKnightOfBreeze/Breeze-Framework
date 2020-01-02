package com.windea.breezeframework.core.consts

/**访问环境变量。*/
object EnvironmentVariables {
	private val env = System.getenv()

	/**得到Java Home。*/
	@JvmField
	val javaHome: String? = env["JAVA_HOME"]
	/**得到Kotlin Home。*/
	@JvmField
	val kotlinHome: String? = env["KOTLIN_HOME"]

	/**得到指定的环境变量。*/
	@JvmStatic
	operator fun get(name: String): String? = env[name]
}
