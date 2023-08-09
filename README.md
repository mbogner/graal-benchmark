# Graal Tests

This project defines some small projects with Java, Kotlin and Spring boot to compare executable jar files to native
images.

## Modules

The project uses different modules that showcase simple hello world applications:

* [sample-java](sample-java/README.md)
* [sample-kotlin](sample-kotlin/README.md)
* [sample-spring-java](sample-spring-java/README.md)
* [sample-spring-kotlin](sample-spring-kotlin/README.md)

## Requirements

* gradle-profiler 0.20.0 (installed via homebrew)
* hyperfine 1.17.0 (installed via homebrew)
* GraalVM CE 17.0.8 (installed via sdkman)
* python3 3.11.4 (installed via pyenv)
* pip3 23.2.1 (comes with python3)

## Benchmarks

### Compilation

Benchmark of compilation times.

```shell
./benchmark-compilation.sh 3 10
```

### Run

Starts every application x times and benchmarks how long it takes to finish.

```shell
./benchmark-run.sh 3 30
```