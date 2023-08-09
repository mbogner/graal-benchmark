#!/usr/bin/env bash
# shellcheck disable=SC2154
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
cd "${DIR}" || exit 1
OUT_DIR=./benchmark-results/compile/

if [[ "$1" == "" || "$2" == "" ]]; then
  echo "usage: $0 <warmups> <iterations>"
  exit 1
fi

rm -rf profile-out*
gradle-profiler --benchmark --warmups "$1" --iterations "$2" \
  --gradle-version "8.2.1" \
  --scenario-file benchmark-compilation.scenarios

echo "moving result to $OUT_DIR"
rm -rf "${OUT_DIR}"
mkdir -p "${OUT_DIR}"
cp -r profile-out/* "${OUT_DIR}"