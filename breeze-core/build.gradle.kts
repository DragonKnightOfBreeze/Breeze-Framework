plugins {
	kotlin("plugin.noarg") version "1.3.70"
	kotlin("plugin.allopen") version "1.3.70"
}

noArg {
	annotation("com.windea.breezeframework.core.annotations.NoArg")
}

allOpen {
	annotation("com.windea.breezeframework.core.annotations.AllOpen")
}

dependencies {

}
