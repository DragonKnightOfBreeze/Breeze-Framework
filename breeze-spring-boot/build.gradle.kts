plugins {
	kotlin("plugin.spring") version "1.3.70"
	kotlin("plugin.jpa") version "1.3.70"
}

dependencies {
	api(project(":breeze-core"))

	implementation(platform("org.springframework.boot:spring-boot-dependencies:2.2.4.RELEASE"))
	implementation("org.springframework.boot:spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	compileOnly("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.springframework.boot:spring-boot-starter-aop")
	compileOnly("org.springframework.boot:spring-boot-starter-data-jpa")
	compileOnly("org.springframework.boot:spring-boot-starter-cache")
	compileOnly("org.springframework.boot:spring-boot-starter-mail")
	compileOnly("org.springframework.boot:spring-boot-starter-security")

	compileOnly("com.querydsl:querydsl-apt")
	compileOnly("com.querydsl:querydsl-jpa")
}
