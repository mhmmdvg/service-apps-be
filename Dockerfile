# -------- Build Stage --------
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app

COPY . .

# Give execution permission to the Gradle wrapper
RUN chmod +x ./gradlew

# Build the app using the wrapper
RUN ./gradlew installDist

# -------- Runtime Stage --------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Replace `service-apps` if needed (should match rootProject.name in settings.gradle.kts)
COPY --from=builder /app/build/install/service-apps/ .

EXPOSE 8080

# Run the app
CMD ["bin/service-apps"]
