# Technology Stack & Dependencies

<cite>
**Referenced Files in This Document**   
- [build.gradle](file://build.gradle) - *Updated with PostgreSQL and JPA dependencies*
- [DemoApplication.kt](file://src/main/kotlin/com/example/demo/DemoApplication.kt)
- [settings.gradle](file://settings.gradle)
- [application.properties](file://src/main/resources/application.properties) - *Added PostgreSQL configuration*
- [VideoGame.kt](file://src/main/kotlin/com/example/demo/Lab2/entities/VideoGame.kt) - *JPA entity with relationships*
- [VideoGameRepository.kt](file://src/main/kotlin/com/example/demo/Lab2/repositories/VideoGameRepository.kt) - *Spring Data JPA repository*
- [VideoGameService.kt](file://src/main/kotlin/com/example/demo/Lab2/services/VideoGameService.kt) - *Service with transaction management*
</cite>

## Update Summary
**Changes Made**   
- Added PostgreSQL and JPA dependency information
- Updated key dependencies section with new database components
- Added database configuration details
- Enhanced dependency management strategy with persistence layer
- Added new section on data persistence and JPA configuration
- Updated version compatibility guidance for database components

## Table of Contents
1. [Introduction](#introduction)
2. [Core Framework: Spring Boot 3.5.6](#core-framework-spring-boot-356)
3. [Programming Language: Kotlin 1.9.25](#programming-language-kotlin-1925)
4. [Gradle Build Configuration](#gradle-build-configuration)
5. [Key Dependencies Analysis](#key-dependencies-analysis)
6. [Java Toolchain and Runtime](#java-toolchain-and-runtime)
7. [Dependency Management Strategy](#dependency-management-strategy)
8. [Data Persistence and JPA Configuration](#data-persistence-and-jpa-configuration)
9. [Adding and Modifying Dependencies](#adding-and-modifying-dependencies)
10. [Version Compatibility and Upgrade Guidance](#version-compatibility-and-upgrade-guidance)
11. [Conclusion](#conclusion)

## Introduction
This document provides a comprehensive analysis of the technology stack and dependency management system in the Spring Boot application. It details the foundational components including Spring Boot 3.5.6, Kotlin 1.9.25, and Gradle build configuration. The document explains the purpose of each dependency, the build setup, and provides guidance on managing dependencies effectively.

**Section sources**
- [build.gradle](file://build.gradle#L1-L50)
- [DemoApplication.kt](file://src/main/kotlin/com/example/demo/DemoApplication.kt#L1-L10)

## Core Framework: Spring Boot 3.5.6

Spring Boot 3.5.6 serves as the foundational framework for this application, providing essential capabilities that streamline development and deployment. The framework enables auto-configuration through the `@SpringBootApplication` annotation, which automatically configures the Spring application based on the dependencies present in the classpath. This eliminates the need for extensive XML or Java configuration, allowing developers to focus on business logic rather than infrastructure setup.

The framework also includes an embedded server capability, which allows the application to run as a standalone executable JAR file without requiring external deployment to a servlet container. This is particularly beneficial for microservices architectures and cloud-native applications where simplicity and portability are crucial.

Spring Boot 3.5.6 brings several advantages including production-ready features out of the box (such as health checks, metrics, and externalized configuration), opinionated defaults that follow best practices, and seamless integration with the broader Spring ecosystem.

```mermaid
classDiagram
class DemoApplication {
+main(args String[])
}
class SpringBootApplication {
+autoConfiguration()
+embeddedServer()
+configurationProperties()
}
DemoApplication --> SpringBootApplication : "annotated with"
```

**Diagram sources**
- [DemoApplication.kt](file://src/main/kotlin/com/example/demo/DemoApplication.kt#L5-L6)
- [build.gradle](file://build.gradle#L3-L3)

**Section sources**
- [DemoApplication.kt](file://src/main/kotlin/com/example/demo/DemoApplication.kt#L5-L6)
- [build.gradle](file://build.gradle#L3-L3)

## Programming Language: Kotlin 1.9.25

The application leverages Kotlin 1.9.25 as the primary programming language, taking advantage of its modern syntax, null safety, and interoperability with Java. The Kotlin JVM plugin (`org.jetbrains.kotlin.jvm`) is configured in the build file to compile Kotlin code for the Java Virtual Machine, while the Kotlin Spring plugin (`org.jetbrains.kotlin.plugin.spring`) provides additional support for Spring Framework features.

Kotlin's concise syntax reduces boilerplate code significantly compared to Java, leading to more maintainable and readable code. The language's null safety features help prevent null pointer exceptions at compile time, improving application reliability. Additionally, Kotlin's extension functions, data classes, and coroutines provide powerful abstractions that enhance developer productivity.

The Kotlin reflection support (`kotlin-reflect`) enables runtime inspection of Kotlin classes, functions, and properties, which is essential for frameworks like Spring that rely on reflection for dependency injection, aspect-oriented programming, and JSON serialization.

**Section sources**
- [build.gradle](file://build.gradle#L1-L2)
- [build.gradle](file://build.gradle#L15-L15)

## Gradle Build Configuration

The Gradle build system is configured with several key components that ensure consistent and reliable builds. The build script defines plugins, repositories, dependencies, and custom configurations that govern how the application is compiled, tested, and packaged.

The configuration includes a dedicated `developmentOnly` configuration that isolates dependencies used exclusively during development, preventing them from being included in production builds. The `runtimeClasspath` configuration extends from `developmentOnly`, ensuring that development dependencies are available at runtime during development but excluded from final artifacts.

The build also configures test tasks to use JUnit Jupiter (the testing framework for JUnit 5), enabling modern testing features such as parameterized tests, dynamic tests, and improved assertion APIs.

```mermaid
flowchart TD
A["Gradle Build Process"] --> B["Apply Plugins"]
B --> C["Configure Java Toolchain"]
C --> D["Resolve Dependencies"]
D --> E["Compile Kotlin Code"]
E --> F["Run Tests with JUnit Jupiter"]
F --> G["Package Application"]
G --> H["Deploy/Run"]
style A fill:#f9f,stroke:#333
style H fill:#bbf,stroke:#333
```

**Diagram sources**
- [build.gradle](file://build.gradle#L1-L50)

**Section sources**
- [build.gradle](file://build.gradle#L1-L50)

## Key Dependencies Analysis

The application's dependency management is structured around several critical components that provide essential functionality:

- **spring-boot-starter-web**: Provides the foundation for building web applications with Spring MVC, including embedded Tomcat server, Spring Web, and related dependencies. This starter enables REST functionality and web endpoint creation.

- **spring-boot-starter-data-jpa**: Integrates Spring Data JPA with Hibernate as the default JPA provider, enabling database persistence with minimal configuration. This dependency provides repository support, transaction management, and automatic CRUD operations.

- **spring-boot-starter-validation**: Provides Bean Validation (JSR-380) support with Hibernate Validator, enabling declarative validation of request data and business objects.

- **postgresql**: PostgreSQL JDBC driver for connecting to PostgreSQL databases in production environments.

- **jackson-module-kotlin**: Extends Jackson JSON processor to support Kotlin-specific features such as data classes, default parameter values, and immutable properties. This module ensures proper serialization and deserialization of Kotlin objects in REST endpoints.

- **kotlin-reflect**: Supplies reflection capabilities for Kotlin code, enabling frameworks to inspect and manipulate Kotlin classes, functions, and properties at runtime. This is crucial for Spring's dependency injection and AOP features when working with Kotlin code.

- **spring-boot-devtools**: Offers development-time features including automatic application restart when code changes are detected, live reload of static resources, and enhanced debugging capabilities. This significantly improves developer productivity during the development phase.

- **spring-boot-starter-test**: Includes comprehensive testing support with Spring Test, JUnit Jupiter, Mockito, and other testing utilities, enabling robust unit and integration testing.

```mermaid
graph TB
subgraph "Core Dependencies"
A[spring-boot-starter-web]
B[spring-boot-starter-data-jpa]
C[spring-boot-starter-validation]
D[postgresql]
E[jackson-module-kotlin]
F[kotlin-reflect]
G[spring-boot-devtools]
H[spring-boot-starter-test]
end
subgraph "Functionality"
I[REST Endpoints]
J[Database Persistence]
K[Data Validation]
L[PostgreSQL Integration]
M[JSON Processing]
N[Reflection Support]
O[Dev Reloading]
P[Unit Testing]
end
A --> I
B --> J
C --> K
D --> L
E --> M
F --> N
G --> O
H --> P
```

**Diagram sources**
- [build.gradle](file://build.gradle#L13-L19)

**Section sources**
- [build.gradle](file://build.gradle#L13-L19)

## Java Toolchain and Runtime

The application is configured to use Java 21 as the target runtime through Gradle's toolchain feature. This ensures that the application is compiled with Java 21 language features and APIs, while also guaranteeing that the build will fail if Java 21 is not available in the development environment.

The toolchain configuration provides several benefits:
- Ensures consistent compilation across different development environments
- Automatically detects and uses the appropriate JDK version
- Eliminates configuration issues related to JAVA_HOME settings
- Enables use of modern Java features while maintaining compatibility

This configuration is particularly valuable in team environments where developers may have different JDK versions installed, as it enforces a standardized build environment.

```mermaid
flowchart LR
A["Development Environment"] --> B["Gradle Toolchain"]
B --> C{"Java 21 Available?"}
C --> |Yes| D["Compile with Java 21"]
C --> |No| E["Build Failure"]
D --> F["Generate Bytecode"]
F --> G["Run on Java 21+ JVM"]
```

**Diagram sources**
- [build.gradle](file://build.gradle#L10-L12)

**Section sources**
- [build.gradle](file://build.gradle#L10-L12)

## Dependency Management Strategy

The project employs a sophisticated dependency management strategy using the `io.spring.dependency-management` plugin. This plugin automatically manages the versions of all Spring Boot dependencies, ensuring compatibility and eliminating version conflicts.

The strategy includes:
- Centralized version management for all Spring-related dependencies
- Consistent dependency versions across the entire Spring ecosystem
- Automatic resolution of compatible versions for transitive dependencies
- Simplified dependency declarations (version numbers can be omitted)

The dependency configurations are organized into logical groups:
- `implementation`: Dependencies required for compilation and runtime
- `testImplementation`: Dependencies needed only for testing
- `testRuntimeOnly`: Runtime dependencies specific to test execution
- `developmentOnly`: Dependencies used exclusively during development

This approach promotes clean separation of concerns and prevents unnecessary dependencies from being included in production builds.

**Section sources**
- [build.gradle](file://build.gradle#L4-L4)
- [build.gradle](file://build.gradle#L13-L19)

## Data Persistence and JPA Configuration

The application implements a robust data persistence layer using Spring Data JPA with PostgreSQL as the primary database. The configuration supports both production and testing environments with different database systems.

### Database Configuration
The application uses different databases for production and testing:
- **Production**: PostgreSQL (configured in `src/main/resources/application.properties`)
- **Testing**: H2 in-memory database (configured in `src/test/resources/application.properties`)

### JPA Entity Design
The application implements four main entities with proper relationships:
- **VideoGame**: Main entity with relationships to Developer, Publisher, and Genre
- **Developer**: Parent entity with one-to-many relationship to VideoGame
- **Publisher**: Parent entity with one-to-many relationship to VideoGame  
- **Genre**: Parent entity with one-to-many relationship to VideoGame

### Key JPA Features Implemented
- **Entity Relationships**: Properly configured ManyToOne and OneToMany relationships with appropriate fetch strategies
- **Automatic Timestamps**: CreatedAt and UpdatedAt fields with automatic management
- **Validation Constraints**: Database-level constraints (NOT NULL, UNIQUE, etc.)
- **Cascade Management**: Controlled cascade operations to prevent accidental deletions

### Configuration Properties
```properties
# Production configuration (application.properties)
spring.datasource.url=jdbc:postgresql://localhost:5432/lab2_videogames
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Test configuration (test/resources/application.properties)
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop
```

```mermaid
classDiagram
class VideoGame {
+Long id
+String title
+Integer releaseYear
+BigDecimal price
+LocalDateTime createdAt
+LocalDateTime updatedAt
}
class Developer {
+Long id
+String name
+String country
+Integer foundedYear
+VideoGame[] games
+LocalDateTime createdAt
+LocalDateTime updatedAt
}
class Publisher {
+Long id
+String name
+String country
+Integer foundedYear
+VideoGame[] games
+LocalDateTime createdAt
+LocalDateTime updatedAt
}
class Genre {
+Long id
+String name
+String description
+VideoGame[] games
+LocalDateTime createdAt
+LocalDateTime updatedAt
}
VideoGame --> Developer : ManyToOne
VideoGame --> Publisher : ManyToOne
VideoGame --> Genre : ManyToOne
Developer --> VideoGame : OneToMany
Publisher --> VideoGame : OneToMany
Genre --> VideoGame : OneToMany
```

**Diagram sources**
- [VideoGame.kt](file://src/main/kotlin/com/example/demo/Lab2/entities/VideoGame.kt#L9-L61)
- [Developer.kt](file://src/main/kotlin/com/example/demo/Lab2/entities/Developer.kt#L8-L51)
- [Publisher.kt](file://src/main/kotlin/com/example/demo/Lab2/entities/Publisher.kt#L8-L51)
- [Genre.kt](file://src/main/kotlin/com/example/demo/Lab2/entities/Genre.kt#L8-L48)

**Section sources**
- [build.gradle](file://build.gradle#L14)
- [application.properties](file://src/main/resources/application.properties#L1-L14)
- [VideoGame.kt](file://src/main/kotlin/com/example/demo/Lab2/entities/VideoGame.kt#L9-L61)

## Adding and Modifying Dependencies

To add new dependencies to the project, developers should follow these guidelines:

1. **For Spring Boot starters**: Use the appropriate starter dependency (e.g., `spring-boot-starter-data-jpa` for database access) without specifying a version, as the version is managed by the dependency management plugin.

2. **For third-party libraries**: Include the full group, artifact, and version in the implementation or appropriate configuration block.

3. **For development tools**: Place dependencies in the `developmentOnly` configuration to prevent inclusion in production builds.

Example of adding a new dependency:
```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'com.h2database:h2:2.2.224'
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2'
}
```

To modify existing dependencies, update the version number directly in the build file. However, for Spring Boot managed dependencies, it's recommended to upgrade the Spring Boot version itself rather than overriding individual dependency versions.

**Section sources**
- [build.gradle](file://build.gradle#L13-L19)

## Version Compatibility and Upgrade Guidance

Maintaining version compatibility is critical for application stability. The following guidelines ensure safe dependency upgrades:

1. **Spring Boot upgrades**: When upgrading Spring Boot, update the plugin version in the build file. This automatically updates all managed dependencies to compatible versions.

2. **Kotlin upgrades**: Ensure Kotlin version compatibility with Spring Boot. Spring Boot 3.5.6 is compatible with Kotlin 1.9.x series.

3. **Java version compatibility**: Spring Boot 3.5.6 requires Java 17 or higher, with Java 21 recommended for optimal performance and feature availability.

4. **Database driver compatibility**: Ensure PostgreSQL JDBC driver version compatibility with the database server version.

5. **Third-party library upgrades**: Always check compatibility matrices and release notes before upgrading non-managed dependencies.

Recommended upgrade procedure:
1. Check the Spring Boot release notes for breaking changes
2. Update the Spring Boot version in the build file
3. Run all tests to verify functionality
4. Address any deprecation warnings
5. Perform integration testing

The use of the dependency management plugin significantly reduces version conflict risks during upgrades.

**Section sources**
- [build.gradle](file://build.gradle#L1-L50)

## Conclusion
The technology stack and dependency management system in this Spring Boot application provides a robust foundation for modern Kotlin development. The combination of Spring Boot 3.5.6, Kotlin 1.9.25, and Gradle with Java 21 offers a powerful, type-safe, and productive development environment. The well-structured dependency management approach ensures version compatibility, simplifies maintenance, and supports safe upgrades. This configuration is well-suited for building scalable, maintainable, and production-ready applications.

[No sources needed since this section summarizes without analyzing specific files]