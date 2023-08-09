#!/bin/bash
OUT_DIR=./benchmark-results/container
OUTPUT=$OUT_DIR/container
LOG="${OUT_DIR}/container.log"

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
  --setup './Dockerfile-graalbuilder.sh && ./sample-java/Dockerfile-jar.sh && ./sample-java/Dockerfile-native.sh && ./sample-kotlin/Dockerfile-jar.sh && ./sample-kotlin/Dockerfile-native.sh && ./sample-spring-java/Dockerfile-jar.sh && ./sample-spring-java/Dockerfile-native.sh && ./sample-spring-kotlin/Dockerfile-jar.sh && ./sample-spring-kotlin/Dockerfile-native.sh' \
  'docker run --rm sample-java-jar:latest' \
  'docker run --rm sample-java-native:latest' \
  'docker run --rm sample-kotlin-jar:latest' \
  'docker run --rm sample-kotlin-native:latest' \
  'docker run --rm sample-spring-java-jar:latest' \
  'docker run --rm sample-spring-java-native:latest' \
  'docker run --rm sample-spring-kotlin-jar:latest' \
  'docker run --rm sample-spring-kotlin-native:latest' \
  >> "${LOG}" 2>&1

echo "create statistics"
pip3 install numpy matplotlib scipy >/dev/null 2>&1 || exit 1
python3 scripts/advanced_statistics.py "${OUTPUT}.json" > "${OUT_DIR}/advanced_statistics.txt"

echo "done"