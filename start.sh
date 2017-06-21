#!/bin/sh

exec java -Xmx200m -Djava.security.egd=file:/dev/./urandom -jar /app.jar
