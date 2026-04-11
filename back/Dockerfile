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

RUN apk add --no-cache su-exec curl \
  && addgroup -S appgroup \
  && adduser -S appuser -G appgroup

WORKDIR /app

COPY --from=builder /app/target/seguridadallimite-1.0.jar app.jar
COPY docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.sh \
  && chown appuser:appgroup /app/app.jar

EXPOSE 8090

ENTRYPOINT ["/docker-entrypoint.sh"]
