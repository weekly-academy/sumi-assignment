plugins {
	java
	id("org.springframework.boot") version "3.1.8"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.test.gradle"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation ("org.apache.commons:commons-lang3:3.12.0")
	implementation ("org.slf4j:slf4j-api:2.0.6")
	implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
	testImplementation ("ch.qos.logback:logback-classic:1.4.5")
	testImplementation ("com.h2database", "h2", "1.4.193")
	implementation ("org.projectlombok:lombok-mapstruct-binding:0.2.0")
	annotationProcessor ("org.projectlombok:lombok-mapstruct-binding:0.2.0")
	annotationProcessor ("org.projectlombok:lombok")
	implementation ("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor ("org.mapstruct:mapstruct-processor:1.5.5.Final")


}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}


