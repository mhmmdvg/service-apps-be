FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle installDist

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/build/install/service-apps/ .
EXPOSE 8080
CMD ["bin/service-apps"]
