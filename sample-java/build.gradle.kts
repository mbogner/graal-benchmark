plugins {
    java
    // https://github.com/graalvm/native-build-tools
    id("org.graalvm.buildtools.native") version "0.9.23"
    // https://github.com/johnrengelman/shadow
    id("com.github.johnrengelman.shadow") version "8.1.1"
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
    // https://mvnrepository.com/artifact/org.apache.commons/commons-text
    implementation("org.apache.commons:commons-text:1.10.0")

    // https://mvnrepository.com/artifact/org.junit/junit-bom
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("sample-java")
            useFatJar.set(true)
            mainClass.set("dev.mbo.graal.sample.Application")
        }
    }
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<Copy> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    jar {
        manifest {
            attributes["Main-Class"] = "dev.mbo.graal.sample.Application"
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }

    shadowJar {
        archiveBaseName.set("sample-java")
        archiveClassifier.set("all")
        archiveVersion.set("")
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