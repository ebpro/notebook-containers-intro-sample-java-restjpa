# ------------------------------
# Stage 1: Build the application
# ------------------------------
FROM maven:3.9.6-eclipse-temurin-21-jammy AS builder
WORKDIR /build

# Copy only pom.xml first to leverage Docker cache
# Copy pom, mvnw and wrapper to use the project wrapper (keeps reproducible builds)
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN chmod +x ./mvnw

# Pre-fetch dependencies (use build cache for Maven local repository)
RUN --mount=type=cache,target=/root/.m2 ./mvnw -P prod dependency:go-offline

# Copy source and build (keeps layers small and cacheable)
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 ./mvnw -P prod clean package -DskipTests

# -----------------
# Stage 2: Runtime
# -----------------
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Add metadata
LABEL maintainer="Emmanuel BRUNO <emmanuel.bruno@univ-tln.fr>"
LABEL version="0.1.0"
LABEL description="REST JPA Demo Application"
LABEL org.opencontainers.image.source="https://github.com/ebpro/notebook-containers-intro-sample-java-restjpa/"
LABEL org.opencontainers.image.licenses="MIT"

# Create non-root user
RUN useradd --system --uid 1001 appuser

# Set environment variables
ENV JAVA_TOOL_OPTIONS="-XX:+UseZGC -XX:+ZGenerational -Xmx512m -Xms512m"

# Copy main JAR and libs from builder stage
COPY --from=builder /build/target/*-SNAPSHOT.jar /app/app.jar
COPY --from=builder /build/target/libs /app/libs

# Set permissions for non-root user
RUN chown -R appuser:appuser /app/

# Configure container
EXPOSE 8080
USER appuser

# Health check uses curl to verify application is running (calls health endpoint)
HEALTHCHECK --interval=30s --timeout=3s \
    CMD curl --fail http://localhost:8080/restjpa/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
