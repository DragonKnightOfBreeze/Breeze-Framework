package com.windea.breezeframework.core.extensions

/**访问环境变量。*/
object EnvironmentVariables {
	private val env = System.getenv()
	
	/**得到Java Home。*/
	val javaHome: String? = this["JAVA_HOME"]
	/**得到Kotlin Home。*/
	val kotlinHome: String? = this["KOTLIN_HOME"]
	
	/**得到指定的环境变量。*/
	operator fun get(name: String): String? = env[name]
}
