import dev.clojurephant.plugin.clojure.tasks.ClojureCompile

plugins {
    id("com.hayden.no-main-class")
    id("com.hayden.ai")
    id("com.hayden.clojure")
    id("com.hayden.kotlin")
}

group = "com.hayden"
version = "0.0.1-SNAPSHOT"



tasks.register("prepareKotlinBuildScriptModel") {}

dependencies {
    api(project(":shared"))
    api(project(":utilitymodule"))
    implementation("io.modelcontextprotocol:kotlin-sdk:0.2.0")
}

tasks.named("compileClojure", ClojureCompile::class.java) {
    namespaces.add("com.hayden.proto.prototypes")
}

tasks.named("compileTestClojure", ClojureCompile::class.java) {
    namespaces.add("com.hayden.proto.prototypes-test")
    namespaces.add("com.hayden.proto.prototypes")
}

