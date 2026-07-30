plugins {
    java
    `maven-publish`
    id("com.gradleup.shadow") version "9.4.2"
    id("xyz.jpenilla.run-paper") version "3.0.2"
}

group = "net.jeqo"
version = "2.2.7"
description = "A unique balloons plugin."

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(26))
    }

    withJavadocJar()
    withSourcesJar()
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.2.build.75-beta")
    compileOnly("com.ticxo.modelengine:ModelEngine:R4.0.9")

    compileOnly("org.projectlombok:lombok:1.18.46")
    annotationProcessor("org.projectlombok:lombok:1.18.46")

    implementation("gg.moonrise.scheduler:folia-scheduler:1.0.1")
    implementation("org.jetbrains:annotations:26.1.0")
    implementation("org.apache.maven:maven-model:3.9.16")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(25)
    }

    javadoc {
        options.encoding = "UTF-8"
        (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
    }

    processResources {
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand("project" to mapOf("version" to project.version))
        }
    }

    jar {
        archiveClassifier.set("plain")
    }

    shadowJar {
        archiveClassifier.set("")
        relocate("gg.moonrise.scheduler", "net.jeqo.bloons.libs.foliascheduler")
    }

    assemble {
        dependsOn(shadowJar)
    }
}

tasks.runServer {
    minecraftVersion("1.21.4")
    runDirectory.set(layout.projectDirectory.dir("../server"))
    systemProperty("com.sun.management.jmxremote", "true")
    jvmArgs("-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:25566")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks.shadowJar)
            artifact(tasks.named("sourcesJar"))
            artifact(tasks.named("javadocJar"))

            pom {
                name.set("Bloons")
                description.set(project.description)
                url.set("https://jeqo.net/bloons")
            }
        }
    }

    repositories {
        maven {
            name = "github"
            url = uri("https://maven.pkg.github.com/Jeqo-Studios/Bloons")
            credentials {
                username = findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
