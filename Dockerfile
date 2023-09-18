FROM openjdk:18-alpine
EXPOSE 8088
COPY target/Ecommerce-1.jar Ecommerce.jar
ENTRYPOINT ["java","-jar","/Ecommerce.jar"]