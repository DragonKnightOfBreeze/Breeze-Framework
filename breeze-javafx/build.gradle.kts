plugins {
	//javafx的插件，仅当java版本为1.8以上时才需要配置
	id("org.openjfx.javafxplugin") version "0.0.9"
}

dependencies {
	api(project(":breeze-core"))
}

//javafx的配置，仅当java版本为1.8以上时才需要配置
//项目仅会导入模块列表对应的依赖，不需要显式导入依赖
javafx {
	version = "11"
	modules = listOf("javafx.base", "javafx.controls", "javafx.fxml", "javafx.graphics", "javafx.media")
}
