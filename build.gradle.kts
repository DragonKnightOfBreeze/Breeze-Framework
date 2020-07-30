/***********************************************************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 *
 *                                     ...]]]]]]..
 *                             ...,]OOOOOOOOOOOOOOO].
 *                            ./]/[[[[\OOOOOOOOOO@@@@O].
 *                                        .OOOOOO@@@@@@@@].
 *                                         =OOOOO@@@@@@@@@@@`.
 *                                          =@@OO@@@@@@@@@@@@@\.
 *                                          .\@@@@@@@@@@@@@@@@@@\.
 *                                   .. .    .O@@@@@@@@@@@@@@@@@@@@`.
 *                                    .``=\.  ,@@@@@@@^=@^O@@@@@@@@@@\.              .`  ..,]]]]]].
 *                      ... .....       .=OO` .O@@@@@^=^..O@@@@@@@@@@@.           .,@@]@@@@@@@@@@@.
 *               ........*[]],\OOOOO\]..  .\O^ O@@@@^O@`..@@@@@@@@@@@@@].      ....OO@@@@@@@@@@@@^
 *          ...*.......**[oOOOOOOOOO@@@@@`. =@`=@@@`=@@`.=@@@@@@@@@@@@@@\......./^.,@@@@@@@@@@@@^
 *                .........[\OOO@@@@@@@@@@@`]O@OO@`.=@@.`=@@@@@@@@@@@@@@@@^...,@`.=@@@@@@@@@@@@`
 *                            ......[[\OO@@@O\O@@`...@@@.@@@@@@@@O@@@@@@@@\..//..O@@@@@@O]`.\/.
 *                    ..,]/OOOO@@@@@@@@@@@^,`,`O@....@@\=@@OO@@@@@@@@@@@@@@]@^./@@@@@@@/`..OO@`
 *                 ..*...,]OO@@@@@@@@@@@O`.,,/@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/O@@@@@@@@@@@@@@@@@.
 *               ....*[*=o/\OO@@@@@@@@@O@@@@@@@@@@@`.=[\O@@@@@@@@@@@@@@@@@@@@@@@@@@@@O[=@@@@@@@O.
 *             ...*....*]/OOO@@@@@[.,/@@@@@@@@@@@@^...........[OOO@@@@@@@@@@@@@@@/.    .O@@@@@@@^
 *           ........**`,OO/oO/. ,/@@@@@@@@@@@@O`***..........**......*....*.=O[.        .=@@@@/\^
 *           *..   ,`.,`**,.. .,@@@@@@@@@@@@@@@^.**.**.....*`...*...../`.........         .\/[..=^.
 *           .    ..*\*. .. ./O@@@@@@@@@@@@@@@O^/....*....=@@@[[^*...=@OO..*... ..        =@@...@@^
 * \.            .*,.     .,O@@@@@@@@@@@@@@@O@O/^*...*.....\]*....**./=.o`*^.... ..      ,@@@@\=@@@`
 *  .`.          ..      ,OOO@@@@@@@OOO/[[[[..*........................**==.* ..         /@@@@@@@@@@.
 *    .,.               ,/`..            ... .........*................*.**.. ..       ./@@@@@@@@@@@.
 *       ,`            ./.               ..  .........*...=*`......../O...... ..     .O@@@@@@@@@@@@^
 *         ,..    ..   =.                ..   ...`...**...,**,**..][=@@`*=@@@@]]]\.]@@@@@@@@@@@@@@@^
 *    ..     .*.  ..   =.                ...,/@@@@@@@O^`..=*****.,\\*./ooO@@@@@@@@@@@@@@@@@@@@@/[...
 *   ..        .\.......`.                 .,O@@@@@@@@\O`.*****...*^`*O\OO@@@@@@@@@@@@@@@@@@@@/.
 *  ...    ..... .\.....\^..                 ./@@@@@@@O@@**......`...`=@O@@@@@@@@@@@@@@@@@@@`
 *   ...  ..       ....  ....          ....*./@@@@@@@@@@@O\.....*.....*@@@@@@@@@@@@@@@@@@@/.
 *   .... ..        ...`  ................*.=@@@@@@@@@@@@@@@\...`.....**@@@@@@@@@@@@@@@@@@@@`
 *    .......         ..,`.        ....*....[[O@@@@@@@@@@@@@@@\@@@\*.....\@@@@@@@@@@@@@@@@@@@@\.
 *     .......          ..,................./@/=@@@@@@@@@@@@@@@@@@@@@]...*\@@@@@@@@@@@@@@@@@@@@@\.
 *       ........           .*.   .........=@^*\/=@@@@@@@@@@@@@@@@@@@@@@\./@@@@@@@@@@@@@@@@@@@@@@@\.
 *      .. ........           .\.....**]/]/\]o\]]@@@@@@@@@@@@@@@@@@OOOO@@@@@@^/@@@@@@@@@@@@@@@@@@@@@`                 ..
 *       ...............    ......,/@@@@O@@Oo@@@@@@@@@@@@@@@@@@@@@@@@@@@@@=@@@O*[@@@@@@@@@@@@@@@@@@@@.               =O`
 *            .......*..........  .[@@O@@@OO@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O@\..\@@@@@@@@@@@@@@@@@@@@.              /\]
 *   =`                    ,]`    ,/@@@@@OO@@@@@@@@@@@@@@@@@@@@@@@@@@O@@@@o@@OO\.,@@@@@@@@@@@@@@@@@@@@\.          ./O@O.
 *   =.       .O`        ,@@@@\.,@@@@@@@OO@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@o@@OO@O.=@@@@@@@@@@@@@@@@@@@@\.        ,O@@^=.
 * . .\.   =OO@.O.     ,@@@@@@@@@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O/O@OOOOO`\@@@@@@@@@@@@@@@@@@@@O.     ./@@/=.=.
 * `  .,`. .@,\.O ....OOO@@@@@@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O=..[\OOOOO`O@@@^ .,\@@@@@O@@@@@/\\]`./@@`. =^^
 * \`    ,\\/@@@`  ./@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/.=^...\oOOOO\@@@^          ./@@[`=@OO@@/.   .O.
 *  .\].     ./@@]]@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O`.......***OOOO@@@^        .,@@`  =/@@@/.    .OO.
 *      .[[[[`..\@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O`..........**=OO@O@@`        /@@\]]/@@@@^O..  .OOO^
 *              .,O@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\*..........*,/@@@O@^]].    ./@@@@@OO@@@@o@@`  =OOO^
 *               .,O@@@@@@@@@@@@@@@@@@@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@Oo*......,O@@@OO^.\@@@].,@@@@@@@O@@@\/O=`  .=OO`.
 * .......`.   .\@@@@@@@@@@@@@@@@@@@@@@@@@/` =@@@@@@@@@@@@@@@@@@@@@@@@@@@@OO\]/@@@OOOO@@@@@@@@@\@@@@@@@@@@@`.    ,OO`.
 *   ,O`. ,O\].. ,@@@@@@@@@@@@@@@@@@@@@/.    ./@@@@@@@@@@@@@\@@OO@***O@@`[\@@O@@@@@@@O/`*@@@@`/@@@@@@@@@@^=O.  ..=O.
 *     .\OOO@O@@@O]`\@@@@@@@@@@@@@@@O\O\]..  .@@@@@@@@@@@@@@\OOo`OO@@@`..,O@@@@//\..[*....O@@@@@@@@@@@@@^./@.   ...
 *           ..[[O@@@@@@\]`. .....,@@@\/@@@@O/@@@@@@@@@.,@@\O@@\=O@@[.../@@@@[.=^.\.......==@@@@@@@@@^.  ,@O.
 *    .,]OO]`..     ..[O@@@@@@@O].. ./@@@@@@@@@@@@@@@@@^O@@@@OOOOoO`*,O@@@O`...=\..,\...,/./@@@@@@@@/.  .@`
 *  .O@@@/\O`...  ..,`.    .,\@@@@@@@@@@OOO@@@@@@@@@@@@\@O@@@@@OoOO@@@@@O......@@`..O..../@@@@@@@@O^.  ,O.
 *  .     .....,@@@O`            .[@@@@@@OOO@@@@@@@@@@@@\@@@@@@@@@@@@/[*......=@@@`...]@@@@O@@@@@/.  .=/.
 *          ..=@@@\.               .O@@OoooO@@@@@@@@@@@@@@@@OO\@OO**..........=@@@@@@@@@@`./@@@@\.  ./^
 *            .*O@@@\.              ....OOO@@@@@@@@@@@@@@@@@OO@@O@\]]]]]]]/@@@@@@@@@/[..../^,@@O\^ =@`
 *            ... .[\O^.          ./@@@@@@@@@@@@@@@@@@@@@@@@@@@@/,\@@@@@@@@@@@@@@]....../O`......,@O.
 *            ....                      ...\@@@@@@@@@@@@@@@@@@@`......[[`.@@@@@@@@/\OO[`......../@^
 *             ...                           .[\O^,@@@@@@@O@@@@..........=@@@@@@@@@`.........=@@@@].
 *             ...                            ...,O@OO@@@OOOO@@..........=@@@@@@@@@@^......,@@@@@@@^
 *             ..*.                           .,/OOO@@@@@OOOO@@`.........@@@@@@@@@@@@@@@@@@@@@@@@@@@\`..
 *             .....                          .OOOO@@@@OO@@@@@@@\......,@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@O\]..
 *              ......                      ./OOOOOOO@@OOO@@@@@@@@@@@@@@@@@@@^.    ./@@@@@@@@@@@@@@@@@@@@@@@@@@O]`..
 *               ......                  .]OOOOOO[./@OOOOO@@@@@@@@@@@@@@@@@/.  .,/@@/`O@@@@@@@@@@@@@@@@@@@@O@@@@@@@@@@\]
 *               .........               .......*.,OOOOOO@@@@@@@@@@@@@@@@@@^.]@@@/.   .@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 *                ...........                 .../OOOOO//@@@@@@@@@@@@@@@@O@@@/`.       =@O@@@@@@@@@@@@@...[\@@@@@@@@@@@O
 *                 .........................*`.]OOOO/`.=@@@@@@@@@@@@@@@@@/\O.          .O/@@@@@@@@@@@O^         .[\O@@@@
 *                   .....................*.,[[[......=@@@`.\@@@@@@@@@@@@^.^            ,^,@@@@@@@@@@.                ..
 *                    .................*.............=@@@`./@@@@@@@@@@@@^ ..             .. \@@@@@@@@^`.         ...*[`.
 *                     ............................*,@@@^/@@@@@@@@@@@@@@.                    =@@@@@@@@..\...*[..
 *                      ...........................=@@@O@@@@@@@@@@@@@@@^                     .@@@@@@@@^. .\.
 *                        ..........................\/@@@@@@@\@@@@@@@@O.                ....*[,@@@@@@@^    .*.
 *                          ........................O@@@@@@/..@@@@@@@@^         ...**[.       .@@@@@@@@.     .,`.
 *                            ..................../@@@@@@@@\..@@@@@@@@^ ...*,[..               =@@@@@@@.        ,`.
 *                              ................/@@@@@@@`@@@^ =@@@@@@@^                        .\@@@@@@^          ,*.
 *                                  ..........,O@@@@@@` ..[O[`=@@@@@@@^                         ,@@@@@@^            .\.
 *                                          ,OO@@@@@/`.       .@@@@@@@O.                         @@@@@@\.             .\
 *                                      ..,/O@@@@@/.          .O@@@@@@@.                         .@@@@@^
 *                             ....*[`. ./OOOO@@/.             =@@@@@@O.                          =@@@@@.
 *                     ....*[..       .,OOOOOOO`               .@@@@@@@.                          =@@@@@^
 *
 * Breeze is blowing ...
 **********************************************************************************************************************/

import org.gradle.jvm.tasks.Jar

//配置要用到的插件
plugins {
	id("org.gradle.maven-publish")
	id("org.jetbrains.kotlin.jvm") version "1.3.72"
	id("org.jetbrains.dokka") version "0.10.1"
	id("com.jfrog.bintray") version "1.8.5"
}

allprojects {
	group = "com.windea.breezeframework"
	version = "1.2.0"

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
		pkg.desc = "Integrated code framework written by Kotlin."
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
