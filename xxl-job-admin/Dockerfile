FROM openjdk:8-jre-slim
MAINTAINER gongxc

#ENV PARAMS=""
#ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /app.jar"]
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD target/xxl-job-admin-*.jar /app.jar

COPY org.jacoco.agent-runtime.jar /

EXPOSE 8803  8804

RUN bash -c 'touch /app.jar'
CMD java -javaagent:org.jacoco.agent-runtime.jar=output=tcpserver,address=0.0.0.0,port=8804 -jar app.jar
