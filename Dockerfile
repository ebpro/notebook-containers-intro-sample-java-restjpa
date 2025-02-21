# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21-jammy AS builder
WORKDIR /build

# Copy only pom.xml first to leverage Docker cache
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Add metadata
LABEL maintainer="Emmanuel BRUNO <emmanuel.bruno@univ-tln.fr>"
LABEL version="0.1.0"
LABEL description="REST JPA Demo Application"
LABEL org.opencontainers.image.source="https://github.com/ebpro/notebook-containers-intro-sample-java-restjpa/"
LABEL org.opencontainers.image.licenses="MIT"

# Create non-root user
RUN useradd -r -u 1001 -g root appuser

# Set environment variables
ENV JAVA_OPTS="-XX:+UseZGC -XX:+ZGenerational -Xmx512m -Xms512m"

# Copy JAR from builder stage
COPY --from=builder /build/target/*-jar-with-dependencies.jar app.jar
RUN chown appuser:root app.jar

# Configure container
EXPOSE 8080
USER appuser

# Health check
HEALTHCHECK --interval=30s --timeout=3s \
    CMD wget --quiet --tries=1 --spider http://localhost:8080/restjpa/persons || exit 1

# Run application
ENTRYPOINT ["java","-jar","app.jar"]