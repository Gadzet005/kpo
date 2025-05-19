plugins {
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.google.protobuf") version "0.9.4"
    java
}

repositories {
    mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")

	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("org.postgresql:postgresql")
    
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

tasks.named<Test>("test") {
    useJUnitPlatform()
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.2"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.62.2"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
            }
        }
    }
}

sourceSets {
    main {
        java {
            srcDirs(
                "build/generated/source/proto/main/java",
                "build/generated/source/proto/main/grpc"
            )
        }
    }
}