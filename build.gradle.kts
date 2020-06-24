import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    // kotlin
    val kotlinVersion = "1.3.72"
    kotlin("jvm") version kotlinVersion
    // spring
    id("org.springframework.boot") version "2.2.7.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

group = "com.monkeydp.biz"
version = "1.0.0-SNAPSHOT"
java.sourceCompatibility = VERSION_1_8

val springVersion = "5.2.6.RELEASE"

dependencies {
    // kotlin
    api(kotlin("reflect"))
    api(kotlin("stdlib-jdk8"))

    api("com.monkeydp:tools:1.1.+")

    api("org.springframework:spring-messaging:$springVersion")
    api("org.springframework:spring-webmvc:$springVersion")

    api("jakarta.persistence:jakarta.persistence-api")
    api("org.hibernate:hibernate-core")
    api("au.com.console:kotlin-jpa-specification-dsl:2.0.0-rc.1")

    api("com.ibm.icu:icu4j:67.1")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = VERSION_1_8.toString()
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = VERSION_1_8.toString()
    }
}