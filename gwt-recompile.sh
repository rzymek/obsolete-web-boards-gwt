#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $DIR
mvn -f engine/pom.xml $* generate-sources war:exploded gwt:compile -Pdev -DskipTests 
