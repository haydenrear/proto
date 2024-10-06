import dev.clojurephant.plugin.clojure.tasks.ClojureCheck
import dev.clojurephant.plugin.clojure.tasks.ClojureCompile

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
