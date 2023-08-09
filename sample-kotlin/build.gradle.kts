/*
 * Copyright (c) 2023.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    kotlin("jvm") version "1.9.0"

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

kotlin {
    jvmToolchain(17)
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.apache.commons/commons-text
    implementation("org.apache.commons:commons-text:1.10.0")

    testImplementation(kotlin("test"))
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("sample-kotlin")
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
        archiveBaseName.set("sample-kotlin")
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