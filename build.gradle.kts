import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.7.20"))
    }
}

group = "com.jongyun"
version = "0.0.1-SNAPSHOT"

plugins {
    val kotlinVersion = "1.7.20"
    idea
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
    id("org.springframework.boot") version "2.7.7" apply false
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0" apply false
    id("org.jlleitschuh.gradle.ktlint-idea") version "10.1.0"
    id("org.flywaydb.flyway") version "7.13.0" apply false
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    repositories {
        mavenCentral()
    }

    ktlint {
        filter {
            exclude("*.kts")
            exclude("**/generated/**")
        }
    }
}

tasks {
    register<Exec>("lint") {
        commandLine = "./gradlew ktlintCheck".split(" ")
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencyManagement {
        imports {
            mavenBom(SpringBootPlugin.BOM_COORDINATES)
        }
    }

    java.sourceCompatibility = JavaVersion.VERSION_11

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
        implementation("com.h2database:h2")
        implementation("org.springframework.boot:spring-boot-starter-jdbc")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}