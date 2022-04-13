import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    application
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "me.dragon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks {
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "MainKt"))
        }
    }
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}
