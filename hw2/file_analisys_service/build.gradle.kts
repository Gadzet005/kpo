plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.protobuf)
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common_lib"))
    
    implementation(libs.bundles.spring.web)
    implementation(libs.bundles.spring.data)

    implementation(libs.bundles.grpc.all)

    implementation(libs.springdoc.openapi.starter.webmvc.ui)
    implementation(libs.swagger.annotations)
    
    implementation(libs.bundles.utils)
    implementation(libs.bundles.jackson)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    
    implementation(libs.bundles.logging)
    
    testImplementation(libs.bundles.testing)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

springBoot {
    mainClass.set("file_analisys_service.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

val protobufVersion = "3.25.2"
val grpcVersion = "1.62.2"

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
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