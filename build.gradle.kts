plugins {
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.1.21"
    kotlin("plugin.spring") version "2.1.21"
    kotlin("plugin.jpa") version "2.1.21"
    kotlin("plugin.noarg") version "2.1.21"
}

group = "dk.academy"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val easyRandom = "5.0.0"
val kotlinLogging = "7.0.7"
val mockk = "1.14.2"
val temporal = "1.29.0"
val springdocOpenapi = "2.5.0"

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocOpenapi")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Misc
    implementation("io.github.oshai:kotlin-logging-jvm:$kotlinLogging")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("io.temporal:temporal-kotlin:$temporal")
    implementation("io.temporal:temporal-spring-boot-starter:$temporal")

    // Runtime
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    // Annotation
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Testing
    testImplementation("io.mockk:mockk:$mockk")
    testImplementation("io.temporal:temporal-testing:$temporal")
    testImplementation("org.jeasy:easy-random-core:$easyRandom")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Test Runtime
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs("--enable-native-access=ALL-UNNAMED")
}
