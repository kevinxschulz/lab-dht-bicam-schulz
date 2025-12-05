# --- Builder stage: Maven + JDK ---
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app

# Nur pom.xml zuerst, um den Maven-Cache besser zu nutzen
COPY pom.xml .
RUN mvn -q -B dependency:go-offline

# Restlicher Code
COPY src ./src

# Build (Tests überspringen – für die Aufgabe ok)
RUN mvn -q -B -DskipTests package

# --- Runtime stage: nur JRE ---
FROM eclipse-temurin:17-jre
WORKDIR /app

# JAR aus dem Builder holen – Namen ggf. anpassen
COPY --from=builder /app/target/biciam-1.0-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
