FROM openjdk:17
EXPOSE 8080
ADD build/libs/*.jar /app/
RUN mv $(ls app/opening_account-*.jar | grep -v "plain") /app/opening_account.jar
ENTRYPOINT ["java","-jar","/app/opening_account.jar"]