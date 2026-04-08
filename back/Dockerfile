# ─────────────────────────────────────────────
#  STAGE 1: Build con Maven
# ─────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copiar POM primero para cachear dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B --no-transfer-progress

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests --no-transfer-progress

# ─────────────────────────────────────────────
#  STAGE 2: Runtime liviano con JRE
# ─────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Usuario no-root por seguridad
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=builder /app/target/seguridadallimite-1.0.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-Dspring.profiles.active=prod", \
  "-jar", "app.jar"]
