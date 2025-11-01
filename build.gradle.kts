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
val jacksonVer = "3.0.1"
val opencsvVer = "5.12.0"

dependencies {
    testImplementation(kotlin("test"))
    // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
    implementation("org.seleniumhq.selenium:selenium-java:${seleniumVer}")
    // https://mvnrepository.com/artifact/tools.jackson.dataformat/jackson-dataformat-yaml
    implementation("tools.jackson.dataformat:jackson-dataformat-yaml:${jacksonVer}")
    // https://mvnrepository.com/artifact/tools.jackson.module/jackson-module-kotlin
    implementation("tools.jackson.module:jackson-module-kotlin:${jacksonVer}")
    // https://mvnrepository.com/artifact/com.opencsv/opencsv
    implementation("com.opencsv:opencsv:${opencsvVer}")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}