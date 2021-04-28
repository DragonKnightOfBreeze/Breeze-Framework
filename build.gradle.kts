//配置要用到的插件
plugins {
	id("org.gradle.maven-publish")
	id("org.jetbrains.kotlin.jvm") version "1.4.30"
	id("org.jetbrains.dokka") version "1.4.30"
}

val groupName = "com.windea.breezeframework"
val versionName = "2.0.1"
val packageRootPrefix = "com.windea.breezeframework"
val compilerArgs = listOf(
	"-Xinline-classes",
	"-Xjvm-default=all",
	"-Xopt-in=kotlin.RequiresOptIn",
	"-Xopt-in=kotlin.ExperimentalStdlibApi",
	"-Xopt-in=kotlin.contracts.ExperimentalContracts",
	"-Xopt-in=com.windea.breezeframework.core.annotation.InternalApi",
	"-Xopt-in=com.windea.breezeframework.core.annotation.UnstableApi",
	"-Xopt-in=com.windea.breezeframework.core.annotation.TrickApi"
)
val flatModuleNames = arrayOf("breeze-unstable")
val noPublishModuleNames = arrayOf("breeze-unstable")
val java11ModuleNames = arrayOf("breeze-http", "breeze-javafx", "breeze-unstable")

allprojects {
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
	//	//sourceCompatibility = JavaVersion.VERSION_11
	//	//targetCompatibility = JavaVersion.VERSION_11
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

	val version = when(project.name) {
		in java11ModuleNames -> "11"
		else -> "1.8"
	}
	val compiler = javaToolchains.compilerFor {
		when(project.name) {
			in java11ModuleNames -> languageVersion.set(JavaLanguageVersion.of(11))
			else -> languageVersion.set(JavaLanguageVersion.of(8))
		}
	}
	//从模块名获取包名并设置为包的前缀
	val packageModulePrefix = when {
		project.parent != rootProject -> project.name.removePrefix("breeze-").replaceFirst("-", ".").replace("-", "")
		else -> project.name.removePrefix("breeze-").replace("-", "")
	}
	//得到最终的前缀
	val prefix = when {
		project != rootProject && project.name !in flatModuleNames -> "$packageRootPrefix.$packageModulePrefix"
		else -> packageRootPrefix
	}
	//得到模块的名字
	val projectName = project.name.split("-").joinToString(" ") { it.capitalize() }

	tasks {
		compileJava {
			javaCompiler.set(compiler)
		}
		compileTestJava {
			javaCompiler.set(compiler)
		}
		compileKotlin {
			javaPackagePrefix = prefix
			kotlinOptions {
				jvmTarget = version
				jdkHome = compiler.get().metadata.installationPath.asFile.absolutePath
				freeCompilerArgs = compilerArgs
			}
		}
		compileTestKotlin {
			javaPackagePrefix = prefix
			kotlinOptions {
				jvmTarget = version
				jdkHome = compiler.get().metadata.installationPath.asFile.absolutePath
				freeCompilerArgs = compilerArgs
			}
		}

		withType<org.jetbrains.dokka.gradle.DokkaTaskPartial> {
			dokkaSourceSets {
				named("main") {
					moduleName.set(projectName)
					runCatching{ includes.from("README.md") }
				}
			}
		}
		//dokkaHtml {
		//	dokkaSourceSets {
		//		named("main") {
		//			moduleName.set(projectName)
		//			includes.from("README.md")
		//			sourceLink {
		//				localDirectory.set(file("src/main/kotlin"))
		//			}
		//		}
		//	}
		//}
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

	//val sourcesJar by tasks.creating(org.gradle.jvm.tasks.Jar::class) {
	//	archiveClassifier.set("sources")
	//	from(sourceSets.main.get().allSource)
	//	from("$rootDir/LICENSE") //添加LICENSE
	//}

	//上传的配置
	publishing {
		//配置包含的jar
		publications {
			//创建maven的jar
			register<MavenPublication>("maven") {
				from(components["java"])
				//artifact(dokkaJavadocJar)
				//artifact(dokkaHtmlJar)
				pom {
					name.set(projectName)
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
			maven {
				url = uri("https://maven.pkg.github.com/dragonknightofbreeze/breeze-framework")
				credentials {
					username = System.getenv("GITHUB_USERNAME")
					password = System.getenv("GITHUB_TOKEN")
				}
			}
		}
	}
}
