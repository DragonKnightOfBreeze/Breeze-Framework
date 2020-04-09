import org.gradle.jvm.tasks.Jar

buildscript {
	//配置插件仓库
	repositories {
		maven("https://maven.aliyun.com/nexus/content/groups/public")
		mavenCentral()
		jcenter()
	}
}

//配置要用到的插件
plugins {
	id("org.gradle.maven-publish")
	id("org.jetbrains.kotlin.jvm") version "1.3.70"
	id("org.jetbrains.dokka") version "0.9.18"
	id("com.jfrog.bintray") version "1.8.4"
}

allprojects {
	group = "com.windea.breezeframework"
	version = "1.1.1"

	//应用插件
	apply {
		plugin("org.jetbrains.kotlin.jvm")
		plugin("org.jetbrains.dokka")
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

	//配置kotlin的编译选项
	tasks {
		this.create("codeCleanup") {
			allprojects.asSequence().forEach {
				File(it.path.let { "$it\\src\\main\\kotlin" }).walk().filter { it.extension == "kt" }.forEach {
					val text = it.readText().optimizeComment()
					it.writeText(text)
				}
			}
		}

		compileKotlin {
			incremental = true
			kotlinOptions {
				jvmTarget = "11"
				freeCompilerArgs = listOf(
					"-Xinline-classes",
					"-Xopt-in=kotlin.RequiresOptIn",
					"-Xopt-in=kotlin.ExperimentalStdlibApi",
					"-Xopt-in=kotlin.contracts.ExperimentalContracts",
					"-Xopt-in=com.windea.breezeframework.core.annotations.InternalUsageApi",
					"-Xopt-in=com.windea.breezeframework.core.annotations.UnstableUsageApi",
					"-Xopt-in=com.windea.breezeframework.core.annotations.TrickUsageApi"
				)
			}
		}
		compileTestKotlin {
			incremental = true
			kotlinOptions {
				jvmTarget = "11"
				freeCompilerArgs = listOf(
					"-Xinline-classes",
					"-Xopt-in=kotlin.RequiresOptIn",
					"-Xopt-in=kotlin.ExperimentalStdlibApi",
					"-Xopt-in=kotlin.contracts.ExperimentalContracts",
					"-Xopt-in=com.windea.breezeframework.core.annotations.InternalUsageApi",
					"-Xopt-in=com.windea.breezeframework.core.annotations.UnstableUsageApi",
					"-Xopt-in=com.windea.breezeframework.core.annotations.TrickUsageApi"
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
					name.set("Breeze-Framework")
					description.set("""
						Integrated code framework based on Kotlin,
						provides many useful extensions for standard library and some frameworks.
						What it can do is more than what you think it can do.
					""".trimIndent())
					packaging = "jar"
					url.set("https://github.com/DragonKnightOfBreeze/breeze-framework")
					licenses {
						license {
							name.set("MIT License")
							url.set("https://github.com/DragonKnightOfBreeze/breeze-framework/blob/master/LICENSE")
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
						url.set("https://github.com/DragonKnightOfBreeze/breeze-framework")
						connection.set("scm:git://git@github.com/DragonKnightOfBreeze/breeze-framework.git")
						developerConnection.set("scm:git://git@github.com/DragonKnightOfBreeze/breeze-framework.git")
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

	//bintray远程仓库，可间接上传到jcenter
	bintray {
		//从系统环境变量得到bintray的user和api key，可能需要重启电脑生效
		user = System.getenv("BINTRAY_USER")
		key = System.getenv("BINTRAY_API_KEY")
		setPublications("maven")
		pkg.repo = rootProject.name
		pkg.name = project.name
		pkg.websiteUrl = "https://github.com/DragonKnightOfBreeze/breeze-framework"
		pkg.vcsUrl = "https://github.com/DragonKnightOfBreeze/breeze-framework.git"
		pkg.setLicenses("MIT")
		pkg.version.name = version.toString()
		pkg.version.vcsTag = version.toString()
		publish = true
	}
}

fun String.optimizeComment():String {
	return replace("""(/\*\*)\s*\n(.*)\n\s*(\*/)""".toRegex(), "$1$2$3")
		.replace("""(//|/\*|\*/) """.toRegex(), "$1")
		.replace("* */", "*/")
}
