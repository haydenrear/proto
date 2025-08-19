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

dependencies {
//    api(project(":shared"))
    api(project(":utilitymodule"))
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
