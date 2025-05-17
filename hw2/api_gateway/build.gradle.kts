plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common_lib"))
    
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.springdoc.openapi.starter.webmvc.ui)
    
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
    mainClass.set("api_gateway.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}