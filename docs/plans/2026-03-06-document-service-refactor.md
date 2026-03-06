# Refactoring Plan: Document Service Modernization

## Overview
Refactor the existing Document management system (`DocumentController`, `DocumentService`, etc.) to follow modern Spring Boot best practices, introduce DTOs, and improve error handling.

## Goals
1.  **Separation of Concerns**: Decouple API contract (DTOs) from Database Entities.
2.  **Robustness**: Add validation and proper exception handling.
3.  **Modernization**: Use Java 17 features (Records) and MapStruct for mapping.

## Architecture

### 1. DTOs (`com.company.is.dto`)
Using Java 17 Records for immutability and conciseness.
-   `CreateDocumentRequest`: `String name`, `DocumentType type`, `String body`, `String username`.
-   `UpdateDocumentRequest`: `String name`, `DocumentType type`, `String body`.
-   `DocumentResponse`: All fields + ID + timestamps.

### 2. Mapper (`com.company.is.mapper`)
-   `DocumentMapper`: MapStruct interface (`@Mapper(componentModel = "spring")`).
    -   `toEntity(CreateDocumentRequest)`
    -   `updateEntity(UpdateDocumentRequest, @MappingTarget Document)`
    -   `toResponse(Document)`

### 3. Service (`com.company.is.service.DocumentService`)
-   Inject `DocumentRepository` and `DocumentMapper`.
-   Transactional methods.
-   Returns `DocumentResponse` objects.
-   Throws `DocumentNotFoundException`.

### 4. Controller (`com.company.is.controller.DocumentController`)
-   Accepts `@Valid` request DTOs.
-   Returns `ResponseEntity<DocumentResponse>`.
-   Delegates all logic to Service.

### 5. Error Handling (`com.company.is.exception`)
-   `DocumentNotFoundException` (Runtime).
-   `GlobalExceptionHandler` (`@ControllerAdvice`):
    -   Handle `DocumentNotFoundException` -> 404.
    -   Handle `MethodArgumentNotValidException` -> 400.

## Implementation Steps
1.  **Dependencies**: Add `org.mapstruct:mapstruct` and processor to `build.gradle`.
2.  **DTOs**: Create `dto` package and record classes.
3.  **Exceptions**: Create `exception` package and handler classes.
4.  **Mapper**: Create `DocumentMapper` interface.
5.  **Service**: Refactor `DocumentService` to use Mapper and DTOs.
6.  **Controller**: Refactor `DocumentController` to use DTOs.
7.  **Entity**: Clean up `Document` entity (Lombok usage).
8.  **Verification**: Run application and verify endpoints.
