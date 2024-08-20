FROM openjdk:21
ADD target/demo-0.0.1-SNAPSHOT.jar app1.jar

#ENV TELEGRAM_BOT_TOKEN=$TELEGRAM_BOT_TOKEN
#ENV TELEGRAM_BOT_NAME=$TELEGRAM_BOT_NAME

ENTRYPOINT [ "java", "-jar","app1.jar" ]