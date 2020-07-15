import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    // kotlin
    val kotlinVersion = "1.3.72"
    kotlin("jvm") version kotlinVersion
}

group = "com.monkeydp.biz"
version = "1.0.1-SNAPSHOT"
java.sourceCompatibility = VERSION_1_8

val springVersion = "5.2.6.RELEASE"

dependencies {
    // kotlin
    api(kotlin("reflect"))
    api(kotlin("stdlib-jdk8"))

    api("com.monkeydp:tools:1.1.0.RELEASE")

    api("org.springframework:spring-messaging:$springVersion")
    api("org.springframework:spring-webmvc:$springVersion")

    api("jakarta.persistence:jakarta.persistence-api:2.2.3")
    api("org.hibernate:hibernate-core:5.4.15.Final")
    api("au.com.console:kotlin-jpa-specification-dsl:2.0.0-rc.1")
    api("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5:2.10.4")
    api("com.fasterxml.jackson.module:jackson-module-kotlin")

    api("com.ibm.icu:icu4j:67.1")

    api("commons-codec:commons-codec:1.13")

    api("com.github.javafaker:javafaker:1.0.2")

    api("io.springfox:springfox-core:2.9.2")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = VERSION_1_8.toString()
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = VERSION_1_8.toString()
    }
}