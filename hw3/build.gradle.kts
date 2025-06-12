plugins {
    jacoco
}

jacoco {
    toolVersion = "0.8.7"
}

subprojects {
    apply(plugin = "jacoco")

    tasks.withType(Test::class.java) {
        useJUnitPlatform()

        jacoco {
            reportsDirectory.set(file("$buildDir/reports/jacoco"))
        }
    }
}