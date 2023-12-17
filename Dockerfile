FROM eclipse-temurin:17-jre

ENV TZ="Asia/Almaty" JAVA_APP_MEMORY="-Xmx500m"

WORKDIR /app

EXPOSE 8080

COPY build/libs/*.jar category-bot.jar

ENTRYPOINT ["sh", "-c", "java -jar category-bot.jar $JAVA_APP_MEMORY"]