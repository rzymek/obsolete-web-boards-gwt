#!/bin/bash
mvn install -Pprod -DskipTests -Dnogwt \
	&& mvn eclipse:clean eclipse:eclipse -Pprod -DskipTests -Dnogwt
