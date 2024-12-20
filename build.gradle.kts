plugins {
    kotlin("jvm") version "1.9.24"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:2.3.13")
    implementation("io.ktor:ktor-server-netty:2.3.13")
    implementation("io.ktor:ktor-server-routing:2.3.13")
    implementation("io.ktor:ktor-server-status-pages:2.3.13")

    implementation("io.ktor:ktor-client-core:2.3.13")
    implementation("io.ktor:ktor-client-cio:2.3.13")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.13")
    implementation("io.ktor:ktor-serialization-jackson:2.3.13")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")

    testImplementation("io.ktor:ktor-server-tests:2.3.13")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
}

application {
    mainClass.set("ApplicationKt")
    println("Main class configured as: ${mainClass.get()}")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.test {
    useJUnitPlatform()
}
