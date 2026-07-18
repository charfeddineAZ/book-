#!/usr/bin/env bash
set -euo pipefail

# Guard against Kotlin imports being appended after declarations. Kotlin only
# allows imports between the package declaration and top-level declarations.
violations=0
while IFS= read -r -d '' file; do
  awk '
    BEGIN { seenDeclaration = 0 }
    /^[[:space:]]*$/ || /^[[:space:]]*\/\// { next }
    /^package[[:space:]]+/ { next }
    /^import[[:space:]]+/ {
      if (seenDeclaration) {
        printf "%s:%d: import appears after a declaration\n", FILENAME, FNR
        violations = 1
      }
      next
    }
    { seenDeclaration = 1 }
    END { exit violations }
  ' "$file" || violations=1
done < <(find app/src -type f -name '*.kt' -print0)

exit "$violations"
