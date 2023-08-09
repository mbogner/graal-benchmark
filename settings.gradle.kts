rootProject.name = "graal"

val apps = setOf(
    "sample-java",
    "sample-kotlin",
    "sample-spring-java",
    "sample-spring-kotlin",
)
apps.forEach { appName ->
    include(appName)
    project(":$appName").projectDir = file("./$appName")
}