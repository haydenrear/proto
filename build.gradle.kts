import dev.clojurephant.plugin.clojure.tasks.ClojureCheck
import dev.clojurephant.plugin.clojure.tasks.ClojureCompile
//import gradle.kotlin.dsl.accessors._00f85714044e4519430f1d8fd9fb09ad.clojure
//import gradle.kotlin.dsl.accessors._00f85714044e4519430f1d8fd9fb09ad.main
//import gradle.kotlin.dsl.accessors._00f85714044e4519430f1d8fd9fb09ad.sourceSets
//import gradle.kotlin.dsl.accessors._00f85714044e4519430f1d8fd9fb09ad.test

plugins {
    id("com.hayden.no-main-class")
    id("com.hayden.ai")
    id("com.hayden.clojure")
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

tasks.named("compileClojure", ClojureCompile::class.java) {
    namespaces.add("com.hayden.proto.prototypes")
}

tasks.named("compileTestClojure", ClojureCompile::class.java) {
    namespaces.add("com.hayden.proto.prototypes-test")
    namespaces.add("com.hayden.proto.prototypes")
}

