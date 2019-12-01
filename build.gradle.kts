import org.gradle.jvm.tasks.Jar

//配置要用到的插件
plugins {
	id("org.gradle.maven-publish")
	id("org.jetbrains.kotlin.jvm") version "1.3.60"
	id("org.jetbrains.dokka") version "0.9.18"
	id("com.jfrog.bintray") version "1.8.4"
}

allprojects {
	//version需要写到allprojects里面
	group = "com.windea.breezeframework"
	version = "1.0.7"
	
	//应用插件
	apply {
		plugin("org.gradle.maven-publish")
		plugin("org.jetbrains.kotlin.jvm")
		plugin("org.jetbrains.dokka")
		plugin("com.jfrog.bintray")
	}
	
	//配置依赖仓库
	repositories {
		//使用阿里云代理解决Gradle构建过慢的问题
		maven("http://maven.aliyun.com/nexus/content/groups/public/")
		mavenCentral()
		jcenter()
	}
	
	//配置依赖
	//implementation表示不能传递依赖，api表示能传递依赖，test为测试期，compile为编译器，runtime为运行时
	//optional只能依靠插件实现
	dependencies {
		implementation(kotlin("stdlib"))
		testImplementation(kotlin("test-junit"))
	}
	
	//配置kotlin的一些选项，增量编译需在gradle.properties中配置
	tasks {
		compileKotlin {
			incremental = true
			kotlinOptions {
				jvmTarget = "11"
				freeCompilerArgs = listOf(
					"-Xjsr305=strict",
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
					"-Xuse-experimental=kotlin.ExperimentalStdlibApi",
					"-Xuse-experimental=kotlin.contracts.ExperimentalContracts"
				)
			}
		}
	}
	
	//构建source jar
	val sourcesJar by tasks.creating(Jar::class) {
		archiveClassifier.set("sources")
		from(sourceSets.main.get().allJava)
	}
	
	//构建javadoc jar
	val javadocJar by tasks.creating(Jar::class) {
		archiveClassifier.set("javadoc")
		group = JavaBasePlugin.DOCUMENTATION_GROUP
		from(tasks.dokka)
	}
	
	val siteUrl = "https://github.com/DragonKnightOfBreeze/breeze-framework"
	val gitUrl = "https://github.com/DragonKnightOfBreeze/breeze-framework.git"
	
	//上传的配置
	//虽然并不知道为什么会显示上传两次，但是不这样做就会报错，姑且这样了
	publishing {
		//配置包含的jar
		publications {
			//创建maven的jar
			create<MavenPublication>("maven") {
				from(components["java"])
				artifact(sourcesJar)
				artifact(javadocJar)
				//生成的pom文件的配置
				pom {
					name.set("Breeze Framework")
					description.set("""
						Integrated code framework base on Kotlin,
						provide many useful extensions for standard library and some frameworks.
					""".trimIndent())
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
						connection.set(gitUrl)
						developerConnection.set(gitUrl)
						url.set(siteUrl)
					}
				}
			}
			//配置上传到的仓库
			repositories {
				//maven本地仓库
				maven("$buildDir/repository")
			}
		}
	}
	
	//bintray远程公共仓库，可间接上传到jcenter
	bintray {
		//从系统环境变量得到bintray的user和api key，可能需要重启电脑生效
		user = System.getenv("BINTRAY_USER")
		key = System.getenv("BINTRAY_API_KEY")
		setPublications("maven")
		pkg.repo = rootProject.name
		pkg.name = project.name
		pkg.websiteUrl = siteUrl
		pkg.vcsUrl = gitUrl
		pkg.setLabels("kotlin", "framework")
		pkg.setLicenses("MIT")
		pkg.version.name = version.toString()
		pkg.version.vcsTag = version.toString()
	}
}





