plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "hw2"

include("common_lib")
include("api_gateway")
include("file_analysis_service")
include("file_storing_service")
