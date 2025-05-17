plugins {
    java
    id("java-library")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.utils)
    implementation(libs.bundles.jackson)
    annotationProcessor(libs.lombok)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}