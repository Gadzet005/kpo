plugins {
    java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.google.protobuf") version "0.9.4"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common_lib"))
    
	implementation("org.springframework.boot:spring-boot-starter")

	implementation("org.springframework.boot:spring-boot-starter-web")
    
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation("org.springframework.boot:spring-boot-starter-aop")

	implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")

	implementation("net.devh:grpc-server-spring-boot-starter:2.15.0.RELEASE")
	implementation("io.grpc:grpc-protobuf:1.54.0")
	implementation("io.grpc:grpc-stub:1.54.0")
	compileOnly("org.apache.tomcat:annotations-api:6.0.53")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

springBoot {
    mainClass.set("api_gateway.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}