import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    // kotlin
    val kotlinVersion = "1.4.10"
    kotlin("jvm") version kotlinVersion
    // ml
    id("com.moonlight.gradle.java-maven-publish") version "1.0.1.RELEASE"
}

group = "com.monkeydp.biz"
version = "1.0.7-SNAPSHOT"
java.sourceCompatibility = VERSION_1_8
java.targetCompatibility = VERSION_1_8

val springVersion = "5.2.6.RELEASE"
val springBootVersion = "2.2.7.RELEASE"

dependencies {
    api("com.monkeydp:tools:1.1.7-SNAPSHOT")

    implementation("org.springframework:spring-aspects:$springVersion")
    implementation("org.springframework:spring-messaging:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:$springBootVersion")
    api("org.springframework:spring-webmvc:$springVersion")

    implementation("jakarta.persistence:jakarta.persistence-api:2.2.3")
    implementation("org.hibernate:hibernate-core:5.4.15.Final")
    implementation("au.com.console:kotlin-jpa-specification-dsl:2.0.0-rc.1")
    api("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5:2.10.4")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.4")

    implementation("com.ibm.icu:icu4j:67.1")

    implementation("commons-codec:commons-codec:1.13")

    implementation("me.nimavat:shortid:1.0.1.RC1")

    implementation("io.springfox:springfox-core:2.9.2")

    implementation("com.aliyun:aliyun-java-sdk-core:4.5.1")
    implementation("com.aliyun:aliyun-java-sdk-dysmsapi:1.0.0")

    testImplementation(kotlin("test-junit5"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.0.0")
}

tasks {
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xopt-in=kotlin.time.ExperimentalTime")
            jvmTarget = VERSION_1_8.toString()
        }
    }
    compileTestKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xopt-in=kotlin.time.ExperimentalTime")
            jvmTarget = VERSION_1_8.toString()
        }
    }
    test {
        useJUnitPlatform()
    }
}
