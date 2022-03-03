plugins {
    java
    id("org.springframework.boot") version "2.6.4"
}

apply(plugin = "io.spring.dependency-management")

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
//    implementation(platform("io.r2dbc:r2dbc-bom:Arabba-SR12"))
    implementation("io.r2dbc:r2dbc-h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

springBoot {
    mainClass.set("example.Application")
}

