plugins {
	kotlin("plugin.noarg") version "1.4.0-rc"
	kotlin("plugin.allopen") version "1.4.0-rc"
}

noArg {
	annotation("com.windea.breezeframework.core.annotation.NoArg")
}

allOpen {
	annotation("com.windea.breezeframework.core.annotation.AllOpen")
}

dependencies {

}
