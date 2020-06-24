import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    // kotlin
    val kotlinVersion = "1.3.72"
    kotlin("jvm") version kotlinVersion
}

group = "com.monkeydp.biz"
version = "1.0.0-SNAPSHOT"
java.sourceCompatibility = VERSION_1_8

dependencies {
    // kotlin
    api(kotlin("reflect"))
    api(kotlin("stdlib-jdk8"))

    api("com.monkeydp:tools:1.1.+")

    api("jakarta.persistence:jakarta.persistence-api:2.2.3")
    api("org.hibernate:hibernate-core:5.4.15.Final")
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