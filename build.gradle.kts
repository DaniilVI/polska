plugins {
    id("java")
    id("pmd")
}

group = "ru.yarsu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

pmd {
    toolVersion = "7.16.0"
    ruleSetFiles = files("config/pmd/pmd.xml")
    isConsoleOutput = true
}

tasks.test {
    useJUnitPlatform()
}