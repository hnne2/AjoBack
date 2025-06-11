plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.6"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "1.9.25"
}

tasks.bootJar {
	mainClass.set("com.ajo.AjoApplicationKt")
}
group = "com"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
	maven { url = uri("https://oss.sonatype.org/content/repositories/releases/") }
}

dependencies {
	implementation("com.yandex.cloud:java-sdk-services:2.8.0")
	implementation("com.google.code.gson:gson:2.10.1")
	implementation("org.json:json:20231013")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.mysql:mysql-connector-j")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("net.sourceforge.tess4j:tess4j:5.7.0")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("org.apache.poi:poi:5.2.5")
	implementation("org.apache.poi:poi-ooxml:5.2.5")
	implementation("com.google.cloud:google-cloud-vision:3.19.0")
	implementation("com.google.guava:guava:32.1.3-jre")
	implementation("org.apache.commons:commons-text:1.11.0")
	implementation(platform("ai.djl:bom:0.33.0"))
	implementation("ai.djl.tensorflow:tensorflow-engine")
	implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
	implementation("org.glassfish:jakarta.el:4.0.2")
	implementation("org.springframework.boot:spring-boot-starter-mail")








}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
