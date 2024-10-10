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
//    dependsOn("copyClojure")
    namespaces.add("com.hayden.proto.prototypes")

//    doLast {
//        delete {
//            delete(projectDir.resolve("src/main/clj"))
//        }
//    }
}

tasks.named("compileTestClojure", ClojureCompile::class.java) {
    dependsOn("copyTestClojure")
    namespaces.add("com.hayden.proto.prototypes-test")
    namespaces.add("com.hayden.proto.prototypes")

//    doLast {
//        delete {
//            delete(projectDir.resolve("src/test/clj"))
//        }
//    }
}

tasks.register<Copy>("copyClojure") {
    from(projectDir.resolve("src/main/cljSrc"))
    into(projectDir.resolve("src/main/clj"))
}

tasks.register<Copy>("copyTestClojure") {
    from(projectDir.resolve("src/test/cljSrc"))
    into(projectDir.resolve("src/test/clj"))
}

sourceSets {
    main {
        clojure {
            srcDir("src/main/clj")
        }
    }
    test {
        clojure {
            srcDirs("src/test/clj")
        }
    }
}
