#!/bin/bash
# script: check-coverage.sh
mvn clean test
mvn jacoco:report
echo "Rapport généré à: target/site/jacoco/index.html"
echo "Ouvrez-le dans votre navigateur pour voir les lignes non couvertes"