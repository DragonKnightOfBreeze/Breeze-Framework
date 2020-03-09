import org.gradle.jvm.tasks.Jar

//配置要用到的插件
plugins {
	id("org.gradle.maven-publish")
	id("org.jetbrains.kotlin.jvm") version "1.3.70"
	id("org.jetbrains.dokka") version "0.9.18"
	id("com.jfrog.bintray") version "1.8.4"
}

allprojects {
	group = "com.windea.breezeframework"
	version = "1.0.14"

	//应用插件
	apply {
		plugin("org.jetbrains.kotlin.jvm")
		plugin("org.jetbrains.dokka")
	}

	//配置依赖仓库
	repositories {
		//使用阿里云代理解决Gradle构建过慢的问题
		maven("https://maven.aliyun.com/nexus/content/groups/public")
		mavenCentral()
		jcenter()
	}

	//配置依赖
	//implementation不能传递依赖，api能传递依赖，test为测试期，compile为编译器，runtime为运行时，optional需要依靠插件实现
	dependencies {
		implementation(kotlin("stdlib"))
		testImplementation(kotlin("test-junit"))
	}

	//配置kotlin的编译选项
	tasks {
		compileKotlin {
			incremental = true
			kotlinOptions {
				jvmTarget = "11"
				freeCompilerArgs = listOf(
					"-Xjsr305=strict",
					"-Xinline-classes",
					"-Xuse-experimental=kotlin.Experimental",
					"-Xuse-experimental=kotlin.ExperimentalStdlibApi",
					"-Xuse-experimental=kotlin.contracts.ExperimentalContracts"
				)
			}
		}
		compileTestKotlin {
			incremental = true
			kotlinOptions {
				jvmTarget = "11"
				freeCompilerArgs = listOf(
					"-Xjsr305=strict",
					"-Xinline-classes",
					"-Xjvm-default=enable",
					"-Xuse-experimental=kotlin.Experimental",
					"-Xuse-experimental=kotlin.ExperimentalStdlibApi",
					"-Xuse-experimental=kotlin.contracts.ExperimentalContracts"
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
