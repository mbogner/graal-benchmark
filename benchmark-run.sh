#!/bin/bash
OUT_DIR=./benchmark-results/run
OUTPUT=$OUT_DIR/run
LOG="${OUT_DIR}/run.log"

rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

if [[ "$1" == "" || "$2" == "" ]]; then
  echo "usage: $0 <warmup> <runs>"
  exit 1
fi

echo "output is redirected to ${LOG}"
hyperfine --warmup "$1" --runs "$2" --shell=none \
  --export-json "${OUTPUT}.json" \
  --export-markdown "${OUTPUT}.md" \
  --export-csv "${OUTPUT}.csv" \
  --setup './gradlew :sample-java:shadowJar :sample-java:nativeCompile :sample-kotlin:shadowJar :sample-kotlin:nativeCompile :sample-spring-java:bootJar :sample-spring-java:nativeCompile :sample-spring-kotlin:bootJar :sample-spring-kotlin:nativeCompile' \
  'java -jar sample-java/build/libs/sample-java-all.jar' \
  'sample-java/build/native/nativeCompile/sample-java' \
  'java -jar sample-kotlin/build/libs/sample-kotlin-all.jar' \
  'sample-kotlin/build/native/nativeCompile/sample-kotlin' \
  'java -jar sample-spring-java/build/libs/sample-spring-java-all.jar' \
  'sample-spring-java/build/native/nativeCompile/sample-spring-java' \
  'java -jar sample-spring-kotlin/build/libs/sample-spring-kotlin-all.jar' \
  'sample-spring-kotlin/build/native/nativeCompile/sample-spring-kotlin' \
  >> "${LOG}" 2>&1

echo "create statistics"
pip3 install numpy matplotlib scipy >/dev/null 2>&1 || exit 1
python3 scripts/advanced_statistics.py "${OUTPUT}.json" > "${OUT_DIR}/advanced_statistics.txt"

echo "done"