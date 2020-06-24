import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    // kotlin
    val kotlinVersion = "1.3.72"
    kotlin("jvm") version kotlinVersion
}

group = "com.monkeydp.biz"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = VERSION_1_8

dependencies {
    // kotlin
    api(kotlin("reflect"))
    api(kotlin("stdlib-jdk8"))

    api("au.com.console:kotlin-jpa-specification-dsl:2.0.0-rc.1")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = VERSION_1_8.toString()
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = VERSION_1_8.toString()
    }
}