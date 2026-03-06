# Document Service Refactor Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Refactor Document Service to use DTOs, MapStruct, and robust error handling.

**Architecture:** Spring Boot Service Layer with MapStruct for Entity-DTO mapping, Record-based DTOs, and Global Exception Handling.

**Tech Stack:** Java 17, Spring Boot 3, MapStruct, Lombok, JUnit 5, Mockito.

---

### Task 1: Add Dependencies & Cleanup

**Files:**
- Modify: `build.gradle`
- Modify: `src/main/java/com/company/is/domain/Document.java`

**Step 1: Add MapStruct dependencies**
Add to `build.gradle`:
```groovy
implementation 'org.mapstruct:mapstruct:1.5.5.Final'
annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'
```

**Step 2: Clean up Document Entity**
Modify `Document.java`:
- Remove `@Data`.
- Add `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`.
- Add `@Builder` (optional but good).
- Add validation annotations `@NotBlank` to fields.

**Step 3: Verify build**
Run: `./gradlew clean build -x test`
Expected: BUILD SUCCESS

**Step 4: Commit**
```bash
git add build.gradle src/main/java/com/company/is/domain/Document.java
git commit -m "chore: Add MapStruct and clean up Entity"
```

---

### Task 2: Create DTOs

**Files:**
- Create: `src/main/java/com/company/is/dto/CreateDocumentRequest.java`
- Create: `src/main/java/com/company/is/dto/UpdateDocumentRequest.java`
- Create: `src/main/java/com/company/is/dto/DocumentResponse.java`

**Step 1: Create Records**
Implement records as per design:
- `CreateDocumentRequest`: `name`, `type`, `body`, `username`.
- `UpdateDocumentRequest`: `name`, `type`, `body`.
- `DocumentResponse`: all fields + timestamps.

**Step 2: Commit**
```bash
git add src/main/java/com/company/is/dto/
git commit -m "feat: Add DTO records"
```

---

### Task 3: Exception Handling

**Files:**
- Create: `src/main/java/com/company/is/exception/DocumentNotFoundException.java`
- Create: `src/main/java/com/company/is/exception/GlobalExceptionHandler.java`

**Step 1: Create Exception Class**
`DocumentNotFoundException` extending `RuntimeException`.

**Step 2: Create Handler**
`GlobalExceptionHandler` with `@ControllerAdvice`.
- Handle `DocumentNotFoundException` -> 404.
- Handle `MethodArgumentNotValidException` -> 400.

**Step 3: Commit**
```bash
git add src/main/java/com/company/is/exception/
git commit -m "feat: Add Global Exception Handling"
```

---

### Task 4: Create Mapper

**Files:**
- Create: `src/main/java/com/company/is/mapper/DocumentMapper.java`

**Step 1: Create Mapper Interface**
Annotated with `@Mapper(componentModel = "spring")`.
- `Document toEntity(CreateDocumentRequest request)`
- `DocumentResponse toResponse(Document document)`
- `void updateEntityFromDto(UpdateDocumentRequest request, @MappingTarget Document document)`

**Step 2: Verify Build (Processor)**
Run: `./gradlew compileJava`
Expected: BUILD SUCCESS (Generates implementation)

**Step 3: Commit**
```bash
git add src/main/java/com/company/is/mapper/
git commit -m "feat: Add MapStruct Mapper"
```

---

### Task 5: Refactor Service (Unit Tests First)

**Files:**
- Test: `src/test/java/com/company/is/service/DocumentServiceUnitTest.java`
- Modify: `src/main/java/com/company/is/service/DocumentService.java`

**Step 1: Write Unit Test for Create**
Create `DocumentServiceUnitTest.java` using Mockito (mock Repository and Mapper).
- `testCreateDocument_ShouldReturnResponse()`

**Step 2: Refactor Service - Dependencies**
Inject `DocumentRepository` and `DocumentMapper` via constructor.

**Step 3: Refactor Service - Create Method**
Change `createDocument(Document)` to `createDocument(CreateDocumentRequest)`.
Use mapper to convert.

**Step 4: Run Unit Test**
Run: `./gradlew test --tests DocumentServiceUnitTest`
Expected: PASS

**Step 5: Refactor Update/Delete/Get**
- `updateDocument(Long id, UpdateDocumentRequest)`
- `deleteDocument(Long id)` (check existence first)
- `getDocument(Long id)` (return DTO)

**Step 6: Commit**
```bash
git add src/main/java/com/company/is/service/ src/test/java/com/company/is/service/
git commit -m "refactor: DocumentService to use DTOs and Mapper"
```

---

### Task 6: Refactor Controller

**Files:**
- Modify: `src/main/java/com/company/is/controller/DocumentController.java`

**Step 1: Update Controller Methods**
- `createDocument(@RequestBody @Valid CreateDocumentRequest)`
- `updateDocument(@PathVariable Long id, @RequestBody @Valid UpdateDocumentRequest)`
- Return `DocumentResponse`.

**Step 2: Verify Compilation**
Run: `./gradlew compileJava`

**Step 3: Commit**
```bash
git add src/main/java/com/company/is/controller/
git commit -m "refactor: DocumentController to use DTOs"
```
