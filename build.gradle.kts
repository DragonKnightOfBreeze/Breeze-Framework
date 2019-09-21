import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.*

//配置要用到的插件
plugins {
	`maven-publish`
	kotlin("jvm") version "1.3.50"
	id("nebula.optional-base") version "3.0.3"
	id("com.jfrog.bintray") version "1.8.4"
	id("org.jetbrains.dokka") version "0.9.18"
}

allprojects {
	//应用插件
	apply {
		plugin("org.gradle.maven-publish")
		plugin("org.jetbrains.kotlin.jvm")
		plugin("nebula.optional-base")
		plugin("com.jfrog.bintray")
		plugin("org.jetbrains.dokka")
	}
	
	//version需要写到allprojects里面
	group = "com.windea.breezeframework"
	version = "1.0.3"
	
	//在这里放置常量和扩展参数
	val siteUrl = "https://github.com/DragonKnightOfBreeze/breeze-framework"
	val gitUrl = "https://github.com/DragonKnightOfBreeze/breeze-framework.git"
	
	//配置依赖仓库
	repositories {
		//使用阿里云代理解决Gradle构建过慢的问题
		maven("http://maven.aliyun.com/nexus/content/groups/public/")
		mavenCentral()
		jcenter()
	}
	
	//配置依赖，implementation表示不能传递依赖，api表示能传递依赖，test为测试期，compile为编译器，runtime为运行时
	//optional只能依靠插件实现
	dependencies {
		implementation(kotlin("stdlib"))
		testImplementation(kotlin("test-junit"))
		
		implementation("io.github.microutils:kotlin-logging:1.6.26")
		implementation("org.slf4j:slf4j-simple:2.0.0-alpha0")
	}
	
	//配置kotlin的**一些**选项，增量编译需在gradle.properties中配置
	tasks.withType<KotlinCompile> {
		kotlinOptions.jvmTarget = "11"
	}
	
	//配置dokka
	tasks.dokka {
		outputFormat = "html"
		outputDirectory = "$buildDir/javadoc"
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
	
	//上传的配置
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
				maven {
					url = uri("$buildDir/repository")
				}
				//bintray远程公共仓库，可间接上传到jcenter
				bintray {
					//从系统环境变量得到bintray的user和api key，可能需要重启电脑生效
					user = System.getenv("BINTRAY_USER")
					key = System.getenv("BINTRAY_API_KEY")
					setPublications("maven")
					pkg.userOrg = "breeze-knights"
					pkg.repo = rootProject.name
					pkg.name = project.name
					pkg.websiteUrl = siteUrl
					pkg.vcsUrl = gitUrl
					pkg.setLabels("kotlin", "framework")
					pkg.setLicenses("MIT")
					pkg.version.name = "1.0.3"
					pkg.version.vcsTag = "1.0.x"
				}
			}
		}
	}
}


