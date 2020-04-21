import org.gradle.jvm.tasks.Jar

//配置要用到的插件
plugins {
	id("org.gradle.maven-publish")
	id("org.jetbrains.kotlin.jvm") version "1.3.70"
	id("org.jetbrains.dokka") version "0.10.1"
	id("com.jfrog.bintray") version "1.8.5"
}

allprojects {
	group = "com.windea.breezeframework"
	version = "1.1.2"

	//应用插件
	apply {
		plugin("org.jetbrains.kotlin.jvm")
		plugin("org.jetbrains.dokka")
	}

	buildscript {
		//配置插件仓库
		repositories {
			maven("https://maven.aliyun.com/nexus/content/groups/public")
			mavenCentral()
			jcenter()
		}
	}

	//配置依赖仓库
	repositories {
		maven("https://maven.aliyun.com/nexus/content/groups/public")
		mavenCentral()
		jcenter()
	}

	//配置依赖
	dependencies {
		implementation(kotlin("stdlib"))
		testImplementation(kotlin("test-junit"))
	}

	//从模块名获取包名并设置为包的前缀
	val modulePrefix = when{
		project.parent  != rootProject -> project.name.removePrefix("breeze-").replaceFirst("-",".").replace("-","")
		else -> project.name.removePrefix("breeze-").replace("-","")
	}
	val prefix = when{
		project == rootProject -> "com.windea.breezeframework"
		project.name == "breeze-unstable" -> "com.windea.breezeframework"
		else -> "com.windea.breezeframework.$modulePrefix"
	}

	//配置kotlin的编译选项
	tasks {
		compileKotlin {
			javaPackagePrefix = prefix
			incremental = true
			kotlinOptions {
				jvmTarget = "11"
				freeCompilerArgs = listOf(
					"-Xinline-classes",
					"-Xopt-in=kotlin.RequiresOptIn",
					"-Xopt-in=kotlin.ExperimentalStdlibApi",
					"-Xopt-in=kotlin.contracts.ExperimentalContracts",
					"-Xopt-in=com.windea.breezeframework.core.annotations.InternalUsageApi",
					"-Xopt-in=com.windea.breezeframework.core.annotations.UnstableImplementationApi",
					"-Xopt-in=com.windea.breezeframework.core.annotations.TrickImplementationApi"
				)
			}
		}
		compileTestKotlin {
			javaPackagePrefix = prefix
			incremental = true
			kotlinOptions {
				jvmTarget = "11"
				freeCompilerArgs = listOf(
					"-Xinline-classes",
					"-Xopt-in=kotlin.RequiresOptIn",
					"-Xopt-in=kotlin.ExperimentalStdlibApi",
					"-Xopt-in=kotlin.contracts.ExperimentalContracts",
					"-Xopt-in=com.windea.breezeframework.core.annotations.InternalUsageApi",
					"-Xopt-in=com.windea.breezeframework.core.annotations.UnstableImplementationApi",
					"-Xopt-in=com.windea.breezeframework.core.annotations.TrickImplementationApi"
				)
			}
		}
	}
}

subprojects {
	apply {
		plugin("org.gradle.maven-publish")
		plugin("com.jfrog.bintray")
	}

	//构建source jar
	val sourcesJar by tasks.creating(Jar::class) {
		archiveClassifier.set("sources")
		from(sourceSets.main.get().allSource)
	}

	//构建javadoc jar
	val javadocJar by tasks.creating(Jar::class) {
		archiveClassifier.set("javadoc")
		from(tasks.dokka)
	}

	//上传的配置
	publishing {
		//配置包含的jar
		publications {
			//创建maven的jar
			register<MavenPublication>("maven") {
				from(components["java"])
				artifact(sourcesJar)
				artifact(javadocJar)
				pom {
					name.set(project.name.formatModuleName())
					description.set("Integrated code framework based on Kotlin.")
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
				url = uri("https://maven.pkg.github.com/dragonknightofbreeze/Breeze-Framework")
				credentials {
					username = System.getenv("GITHUB_USERNAME")
					password = System.getenv("GITHUB_TOKEN")
				}
			}
		}
	}

	//bintray远程仓库，可间接上传到jcenter
	//建议将publish和override设为true，否则会报各种让人服气的错误
	bintray {
		//从系统环境变量得到bintray的user和api key，可能需要重启电脑生效
		user = System.getenv("BINTRAY_USER")
		key = System.getenv("BINTRAY_API_KEY")
		setPublications("maven")
		pkg.repo = rootProject.name
		pkg.name = project.name
		pkg.desc = "Integrated code framework based on Kotlin."
		pkg.githubRepo = "DragonKnightOfBreeze/Breeze-Framework"
		pkg.githubReleaseNotesFile = "CHANGELOG.md"
		pkg.websiteUrl = "https://github.com/DragonKnightOfBreeze/Breeze-Framework"
		pkg.vcsUrl = "https://github.com/DragonKnightOfBreeze/Breeze-Framework.git"
		pkg.setLicenses("MIT")
		pkg.version.name = version.toString()
		pkg.version.vcsTag = version.toString()
		publish = true
		override = true
	}
}

fun String.formatModuleName():String {
	return this.split("-").map { it.capitalize() }.joinToString(" ")
}
