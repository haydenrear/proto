import dev.clojurephant.plugin.clojure.tasks.ClojureCompile

plugins {
    id("com.hayden.no-main-class")
    id("com.hayden.ai")
    id("com.hayden.mcp-client")
//    id("com.hayden.clojure")
    id("com.hayden.kotlin")
}

group = "com.hayden"
version = "0.0.1-SNAPSHOT"

tasks.register("prepareKotlinBuildScriptModel") {}

var utilLib = ""

if (project.parent?.name?.contains("multi_agent_ide_parent") ?: false) {
    utilLib = ":multi_agent_ide_java_parent"
} else {
    utilLib = ""
}

dependencies {
//    api(project(":shared"))
    implementation(project("${utilLib}:utilitymodule"))
    implementation("cheshire:cheshire:5.13.0")

}

//tasks.named("compileClojure", ClojureCompile::class) {
//    namespaces.add("com.hayden.proto")
//}
//
//tasks.named("compileTestClojure", ClojureCompile::class) {
//    namespaces.add("com.hayden.proto")
//    namespaces.add("com.hayden.proto")
//}
//
