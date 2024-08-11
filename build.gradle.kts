plugins {
    id("com.hayden.no-main-class")
    id("com.hayden.ai")
}

group = "com.hayden"
version = "0.0.1-SNAPSHOT"

java {
    version = JavaVersion.VERSION_21
}

tasks.register("prepareKotlinBuildScriptModel") {}

dependencies {
    api(project(":shared"))
    api(project(":utilitymodule"))
}
