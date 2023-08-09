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
    base

    kotlin("jvm") version "1.9.0" apply false
    kotlin("plugin.spring") version "1.9.0" apply false

    // https://github.com/graalvm/native-build-tools
    id("org.graalvm.buildtools.native") version "0.9.23" apply false
    // https://github.com/johnrengelman/shadow
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false

    id("org.springframework.boot") version "3.1.2" apply false
    id("io.spring.dependency-management") version "1.1.2" apply false
}

val projects = arrayOf(
    "sample-java",
    "sample-kotlin",
    "sample-spring",
    "sample-spring-kotlin",
)

tasks {
    withType<Wrapper> {
        // https://gradle.org/releases/
        gradleVersion = "8.2.1"
        distributionType = Wrapper.DistributionType.BIN
    }

    clean {
        val toClean = arrayOf(
            "sample-java",
            "sample-kotlin",
            "sample-spring",
            "sample-spring-kotlin",
        ).flatMap {
            listOf(
                "$it/build",
                "$it/gradle-user-home",
                "$it/profile-out",
            )
        }.toSet().toTypedArray()
        delete(*toClean)
    }
}