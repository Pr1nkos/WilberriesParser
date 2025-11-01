plugins {
    kotlin("jvm") version "2.2.20"
    application
}

group = "ru.pr1nkos"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
val seleniumVer = "4.38.0"
val jacksonVer = "2.20.1"
val opencsvVer = "5.12.0"
val jsoupVer = "1.21.2"
val slf4jVer = "2.0.17"
val kotLoggingVer = "7.0.3"
val logbackVer = "1.5.20"

dependencies {
    testImplementation(kotlin("test"))
    // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
    implementation("org.seleniumhq.selenium:selenium-java:${seleniumVer}")
    // https://mvnrepository.com/artifact/tools.jackson.dataformat/jackson-dataformat-yaml
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${jacksonVer}")
    // https://mvnrepository.com/artifact/tools.jackson.module/jackson-module-kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${jacksonVer}")
    // https://mvnrepository.com/artifact/com.opencsv/opencsv
    implementation("com.opencsv:opencsv:${opencsvVer}")
    // https://mvnrepository.com/artifact/org.jsoup/jsoup
    implementation("org.jsoup:jsoup:${jsoupVer}")
// https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:${slf4jVer}")
    implementation("io.github.oshai:kotlin-logging-jvm:${kotLoggingVer}")
    // https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
    implementation("ch.qos.logback:logback-classic:${logbackVer}")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}