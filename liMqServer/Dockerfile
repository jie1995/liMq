FROM openjdk:8-jre
ADD ./*.jar /app.jar
EXPOSE 9003
#此处年轻代默认8:2就好
ENTRYPOINT exec java -jar -Xms256m -Xmx256m app.jar $PARAMS


