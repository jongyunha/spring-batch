import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    runtimeOnly("mysql:mysql-connector-java")
    implementation("org.springframework.boot:spring-boot-starter-batch")
}