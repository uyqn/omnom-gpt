import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    base
    kotlin("jvm") version "2.0.21" apply false // Kotlin plugin for submodules
    id("org.springframework.boot") version "3.3.5" apply false // Spring Boot plugin
    id("io.spring.dependency-management") version "1.1.6" apply false // Spring dependency management
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    // Common configuration for all submodules
    group = "no.uyqn"
    version = "0.0.1-SNAPSHOT"

    plugins.withType<org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper> {
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            compilerOptions {
                freeCompilerArgs.add("-Xjsr305=strict")
                apiVersion.set(KotlinVersion.KOTLIN_2_0)
            }
        }
    }
}
