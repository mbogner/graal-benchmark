plugins {
    java
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"

    // https://github.com/graalvm/native-build-tools
    id("org.graalvm.buildtools.native") version "0.9.23"
}

group = "dev.mbo"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-text
    implementation("org.apache.commons:commons-text:1.10.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    withType<Copy> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    bootJar {
        archiveFileName = "${project.name}-all.jar"
    }

    register<Delete>("cleanBenchmark") {
        delete(
            "gradle-user-home",
            "profile-out"
        )
    }

    withType<Wrapper> {
        // https://gradle.org/releases/
        gradleVersion = "8.2.1"
        distributionType = Wrapper.DistributionType.BIN
    }
}
