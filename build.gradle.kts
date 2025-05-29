plugins {
    id("io.quarkus") version "3.22.3"
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven { url = uri("https://maven.quarkus.io/repository/") }
}

dependencies {
    //quarkus BOM (gestion automatique des versions)
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:3.22.3"))

    // Sécurité / OIDC
    implementation("io.quarkus:quarkus-oidc")
    implementation("io.quarkus:quarkus-security")

    // Client REST
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("io.quarkus:quarkus-arc")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")

    // MyBatis
    implementation("io.quarkiverse.mybatis:quarkus-mybatis:2.4.0")

    // JDBC (PostgreSQL ou autre)
    implementation("io.quarkus:quarkus-jdbc-postgresql") // adapte selon ton SGBD

    // https://mvnrepository.com/artifact/org.jsoup/jsoup
    implementation("org.jsoup:jsoup:1.20.1")

    // utilisation avec swagger
    implementation("io.quarkus:quarkus-smallrye-openapi")

}

tasks.test {
    useJUnitPlatform()
}