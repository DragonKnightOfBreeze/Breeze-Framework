repositories {
	maven("https://nexus.gluonhq.com/nexus/content/repositories/releases/")
}

dependencies {
	api(project(":breeze-core"))
	
	implementation("com.github.almasb:fxgl:11.5")
}
