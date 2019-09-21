plugins {
	id("org.openjfx.javafxplugin") version "0.0.8"
}

repositories {
	maven("https://nexus.gluonhq.com/nexus/content/repositories/releases/")
}

dependencies {
	api(project(":breeze-core"))
	api(project(":breeze-javafx"))
	api(project(":breeze-game"))
	
	implementation("com.github.almasb:fxgl:11.5")
}

//javafx的配置，项目仅会导入模块列表对应的依赖，不需要显式导入依赖
javafx {
	version = "13"
	modules = listOf("javafx.base", "javafx.controls", "javafx.fxml", "javafx.graphics", "javafx.media")
}
