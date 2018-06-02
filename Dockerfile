FROM sigo-nexus.ccorp.local:18446/openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/online-sla.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
ENV TZ 'America/Sao_Paulo'