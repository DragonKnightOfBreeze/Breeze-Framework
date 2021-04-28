//配置要用到的插件
plugins {
	id("org.gradle.maven-publish")
	id("org.jetbrains.kotlin.jvm") version "1.4.30"
	id("org.jetbrains.dokka") version "1.4.30"
}

val groupName = "icu.windea.breezeframework"
val versionName = "3.0.0"
val packageRootPrefix = "icu.windea.breezeframework"
val compilerArgs = listOf(
	"-Xinline-classes",
	"-Xjvm-default=all",
	"-Xopt-in=kotlin.RequiresOptIn",
	"-Xopt-in=kotlin.ExperimentalStdlibApi",
	"-Xopt-in=kotlin.contracts.ExperimentalContracts",
	"-Xopt-in=icu.windea.breezeframework.core.annotation.InternalApi",
	"-Xopt-in=icu.windea.breezeframework.core.annotation.UnstableApi",
	"-Xopt-in=icu.windea.breezeframework.core.annotation.TrickApi"
)
val flatModuleNames = arrayOf("breeze-unstable")
val noPublishModuleNames = arrayOf("breeze-unstable")
val java11ModuleNames = arrayOf("breeze-http", "breeze-javafx", "breeze-unstable")

allprojects {
	val projectName = project.name
	val projectTitle = projectName.split("-").joinToString(" ") { it.capitalize() }
	val projectJavaVersion = when(projectName) {
		in java11ModuleNames -> "11"
		else -> "1.8"
	}
	val projectPackageName = when {
		project.parent != rootProject -> project.name.removePrefix("breeze-").replaceFirst("-", ".").replace("-", "")
		else -> project.name.removePrefix("breeze-").replace("-", "")
	}
	val projectPackagePrefix = when {
		project != rootProject && project.name !in flatModuleNames -> "$packageRootPrefix.$projectPackageName"
		else -> packageRootPrefix
	}

	group = groupName
	version = versionName

	//应用插件
	apply {
		plugin("org.jetbrains.kotlin.jvm")
		plugin("org.jetbrains.dokka")
	}

	kotlin {
		explicitApi()
	}

	buildscript {
		//配置插件仓库
		repositories {
			maven("https://dl.bintray.com/kotlin/kotlin-eap")
			maven("https://maven.aliyun.com/nexus/content/groups/public")
			mavenCentral()
		}
	}

	//配置依赖仓库
	repositories {
		maven("https://dl.bintray.com/kotlin/kotlin-eap")
		maven("https://maven.aliyun.com/nexus/content/groups/public")
		mavenCentral()
	}

	//配置依赖
	dependencies {
		implementation(kotlin("stdlib-jdk8"))
		testImplementation(kotlin("test-junit"))
	}

	java {
		toolchain {
			when(project.name) {
				in java11ModuleNames -> languageVersion.set(JavaLanguageVersion.of(11))
				else -> languageVersion.set(JavaLanguageVersion.of(8))
			}
		}
	}

	//java{
	//	when(project.name){
	//		in java11ModuleNames ->{
	//			sourceCompatibility = JavaVersion.VERSION_11
	//			targetCompatibility = JavaVersion.VERSION_11
	//		}
	//		else ->{
	//			sourceCompatibility = JavaVersion.VERSION_1_8
	//			targetCompatibility = JavaVersion.VERSION_1_8
	//		}
	//	}
	//}

	val projectCompiler = javaToolchains.compilerFor {
		when(project.name) {
			in java11ModuleNames -> languageVersion.set(JavaLanguageVersion.of(11))
			else -> languageVersion.set(JavaLanguageVersion.of(8))
		}
	}

	tasks {
		compileJava {
			javaCompiler.set(projectCompiler)
		}
		compileTestJava {
			javaCompiler.set(projectCompiler)
		}
		compileKotlin {
			javaPackagePrefix = projectPackagePrefix
			kotlinOptions {
				jvmTarget = projectJavaVersion
				jdkHome = projectCompiler.get().metadata.installationPath.asFile.absolutePath
				freeCompilerArgs = compilerArgs
			}
		}
		compileTestKotlin {
			javaPackagePrefix = projectPackagePrefix
			kotlinOptions {
				jvmTarget = projectJavaVersion
				jdkHome = projectCompiler.get().metadata.installationPath.asFile.absolutePath
				freeCompilerArgs = compilerArgs
			}
		}

		withType<org.jetbrains.dokka.gradle.DokkaTask> {
			dokkaSourceSets {
				named("main") {
					moduleName.set(projectTitle)
					//includes.from("README.md")
				}
			}

		}
	}

	//配置需要发布的模块
	if(project == rootProject || project.name in flatModuleNames) return@allprojects

	apply {
		plugin("org.gradle.maven-publish")
	}

	val dokkaJavadocJar by tasks.register<Jar>("dokkaJavadocJar") {
		dependsOn(tasks.dokkaJavadoc)
		from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
		archiveClassifier.set("javadoc")
	}

	val dokkaHtmlJar by tasks.register<Jar>("dokkaHtmlJar") {
		dependsOn(tasks.dokkaHtml)
		from(tasks.dokkaHtml.flatMap { it.outputDirectory })
		archiveClassifier.set("html-doc")
	}

	val sourcesJar by tasks.register<Jar>("sourcesJar") {
		from(sourceSets.main.get().allSource)
		from("$rootDir/LICENSE") //添加LICENSE
		archiveClassifier.set("sources")
	}

	//上传的配置
	publishing {
		//配置包含的jar
		publications {
			//创建maven的jar
			register<MavenPublication>("maven") {
				from(components["java"])
				artifact(dokkaJavadocJar)
				artifact(dokkaHtmlJar)
				artifact(sourcesJar)
				pom {
					name.set(projectTitle)
					description.set("Integrated code framework written by Kotlin.")
					url.set("https://github.com/DragonKnightOfBreeze/Breeze-Framework")
					licenses {
						license {
							name.set("MIT License")
							url.set("https://github.com/DragonKnightOfBreeze/Breeze-Framework/blob/master/LICENSE")
						}
					}
					developers {
						developer {
							id.set("DragonKnightOfBreeze")
							name.set("Windea")
							email.set("dk_breeze@qq.com")
						}
					}
					scm {
						url.set("https://github.com/DragonKnightOfBreeze/Breeze-Framework")
						connection.set("scm:git@github.com:DragonKnightOfBreeze/Breeze-Framework.git")
						developerConnection.set("scm:git@github.com:DragonKnightOfBreeze/Breeze-Framework.git")
					}
				}
			}
		}
		//配置上传到的仓库
		repositories {
			//github pages
			maven {
				name = "github-pages"
				url = uri("https://maven.pkg.github.com/dragonknightofbreeze/breeze-framework")
				credentials {
					username = System.getenv("GITHUB_USERNAME")
					password = System.getenv("GITHUB_TOKEN")
				}
			}
		}
	}
}
