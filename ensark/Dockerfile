# ==============================================================================
# Build Stage
# ==============================================================================
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copy Maven wrapper files and pom.xml for dependency caching
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Ensure wrapper script is executable and resolve dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy application source code and compile jar package
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# ==============================================================================
# Runtime Stage
# ==============================================================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Create non-root system user and uploads folder for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup \
    && mkdir -p /app/uploads && chown -R appuser:appgroup /app

USER appuser:appgroup

# Copy compiled JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Default Environment Variables
ENV PORT=8085 \
    UPLOAD_DIR=/app/uploads

EXPOSE 8085

# Fast startup using non-blocking random seed generator
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
