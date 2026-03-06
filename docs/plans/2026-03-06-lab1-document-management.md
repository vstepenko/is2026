# Lab 1: Document Management System Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Build a Spring Boot REST API for managing personnel electronic documents (creation, signing, search) backed by PostgreSQL.

**Architecture:** Layered Spring Boot application (Controller -> Service -> Repository -> Database).

**Tech Stack:** Java 17, Spring Boot 3, Spring Data JPA, PostgreSQL (Docker), JUnit 5, Gradle.

---

### Task 1: Project Scaffolding

**Files:**
- Create: `build.gradle`
- Create: `settings.gradle`
- Create: `src/main/java/com/company/is/Lab1Application.java`
- Create: `src/main/resources/application.properties`

**Step 1: Create Gradle configuration**

Create `settings.gradle` with: `rootProject.name = 'lab1'`

Create `build.gradle` with plugins (`java`, `org.springframework.boot`, `io.spring.dependency-management`) and dependencies:
- `implementation 'org.springframework.boot:spring-boot-starter-web'`
- `implementation 'org.springframework.boot:spring-boot-starter-data-jpa'`
- `runtimeOnly 'org.postgresql:postgresql'`
- `compileOnly 'org.projectlombok:lombok'`
- `annotationProcessor 'org.projectlombok:lombok'`
- `testImplementation 'org.springframework.boot:spring-boot-starter-test'`

**Step 2: Create Main Application Class**

Create `src/main/java/com/company/is/Lab1Application.java` annotated with `@SpringBootApplication`.

**Step 3: Verify Build**

Run: `./gradlew build`
Expected: Build success (tests may vary, currently none).

**Step 4: Commit**

```bash
git add build.gradle settings.gradle src/
git commit -m "chore: init project structure with gradle"
```

### Task 2: Database Infrastructure

**Files:**
- Create: `compose.yaml`
- Modify: `src/main/resources/application.properties`

**Step 1: Create Docker Compose file**

Create `compose.yaml` defining a `postgres` service (port 5432, user/pass/db defined).

**Step 2: Configure Spring Boot**

Update `application.properties`:
- `spring.datasource.url=jdbc:postgresql://localhost:5432/lab1_db`
- `spring.datasource.username=...`
- `spring.datasource.password=...`
- `spring.jpa.hibernate.ddl-auto=update` (for automatic table creation)

**Step 3: Start Database**

Run: `docker compose up -d`
Expected: Container starts healthy.

**Step 4: Commit**

```bash
git add compose.yaml src/main/resources/application.properties
git commit -m "infra: add postgres docker compose and config"
```

### Task 3: Document Entity

**Files:**
- Create: `src/main/java/com/company/is/domain/DocumentType.java`
- Create: `src/main/java/com/company/is/domain/Document.java`
- Create: `src/main/java/com/company/is/repository/DocumentRepository.java`

**Step 1: Define Enum**

Create `DocumentType` enum with values: `VACATION_REQUEST`, `HIRING_REQUEST`, `DISMISSAL_REQUEST`.

**Step 2: Define Entity**

Create `Document` class annotated with `@Entity`:
- `id` (Long, @Id, @GeneratedValue)
- `name` (String)
- `type` (DocumentType, @Enumerated)
- `body` (String, @Lob)
- `creationDate` (LocalDateTime)
- `signatureDate` (LocalDateTime)
- `username` (String)

**Step 3: Define Repository**

Create interface `DocumentRepository` extending `JpaRepository<Document, Long>`.

**Step 4: Commit**

```bash
git add src/main/java/com/company/is/domain/ src/main/java/com/company/is/repository/
git commit -m "feat: add Document entity and repository"
```

### Task 4: Feature - Create Document

**Files:**
- Create: `src/main/java/com/company/is/service/DocumentService.java`
- Create: `src/main/java/com/company/is/controller/DocumentController.java`
- Test: `src/test/java/com/company/is/DocumentControllerTest.java`

**Step 1: Write failing test (POST /api/documents)**

Create `DocumentControllerTest` using `@SpringBootTest` and `MockMvc`.
Test `createDocument` expects 200 OK and JSON return.

**Step 2: Implement Service**

Create `DocumentService` with method `createDocument(Document doc)`. Should set `creationDate` to `now()` if null.

**Step 3: Implement Controller**

Create `DocumentController` with `@PostMapping("/api/documents")`.

**Step 4: Verify Pass**

Run: `./gradlew test`

**Step 5: Commit**

```bash
git add src/
git commit -m "feat: implement create document api"
```

### Task 5: Feature - Update Document

**Files:**
- Modify: `src/main/java/com/company/is/service/DocumentService.java`
- Modify: `src/main/java/com/company/is/controller/DocumentController.java`
- Modify: `src/test/java/com/company/is/DocumentControllerTest.java`

**Step 1: Write failing test (PUT /api/documents/{id})**

Test updating a document's body or type.

**Step 2: Implement Service Update**

Add `updateDocument(Long id, Document incoming)` to Service. Fetch existing, update fields, save.

**Step 3: Implement Controller Update**

Add `@PutMapping("/{id}")` to Controller.

**Step 4: Verify Pass**

Run: `./gradlew test`

**Step 5: Commit**

```bash
git add src/
git commit -m "feat: implement update document api"
```

### Task 6: Feature - Delete Document

**Files:**
- Modify: `src/main/java/com/company/is/service/DocumentService.java`
- Modify: `src/main/java/com/company/is/controller/DocumentController.java`
- Modify: `src/test/java/com/company/is/DocumentControllerTest.java`

**Step 1: Write failing test (DELETE /api/documents/{id})**

Test deleting an existing ID.

**Step 2: Implement Service Delete**

Add `deleteDocument(Long id)` to Service.

**Step 3: Implement Controller Delete**

Add `@DeleteMapping("/{id}")` to Controller.

**Step 4: Verify Pass**

Run: `./gradlew test`

**Step 5: Commit**

```bash
git add src/
git commit -m "feat: implement delete document api"
```

### Task 7: Feature - Get Documents by User

**Files:**
- Modify: `src/main/java/com/company/is/repository/DocumentRepository.java`
- Modify: `src/main/java/com/company/is/service/DocumentService.java`
- Modify: `src/main/java/com/company/is/controller/DocumentController.java`

**Step 1: Write failing test (GET /api/documents/user/{username})**

**Step 2: Add Repository Method**

Add `List<Document> findByUsername(String username);`

**Step 3: Implement Service & Controller**

Expose the find method.

**Step 4: Verify Pass**

Run: `./gradlew test`

**Step 5: Commit**

```bash
git add src/
git commit -m "feat: implement get documents by user"
```

### Task 8: Feature - Get Documents by Status (Signed/Unsigned)

**Files:**
- Modify: `src/main/java/com/company/is/repository/DocumentRepository.java`
- Modify: `src/main/java/com/company/is/service/DocumentService.java`
- Modify: `src/main/java/com/company/is/controller/DocumentController.java`

**Step 1: Write failing test**

Test fetching signed (`signatureDate` is NOT NULL) and unsigned (`signatureDate` IS NULL) for a user.

**Step 2: Add Repository Methods**

- `findByUsernameAndSignatureDateIsNotNull(String username)`
- `findByUsernameAndSignatureDateIsNull(String username)`

**Step 3: Implement Controller Endpoint**

`GET /api/documents/user/{username}/signed?signed=true/false`

**Step 4: Verify Pass**

Run: `./gradlew test`

**Step 5: Commit**

```bash
git add src/
git commit -m "feat: implement get documents by signed status"
```

### Task 9: Feature - Get Documents by Date Range

**Files:**
- Modify: `src/main/java/com/company/is/repository/DocumentRepository.java`
- Modify: `src/main/java/com/company/is/service/DocumentService.java`
- Modify: `src/main/java/com/company/is/controller/DocumentController.java`

**Step 1: Write failing test**

Test fetching documents within a date range.

**Step 2: Add Repository Method**

`findByCreationDateBetween(LocalDateTime start, LocalDateTime end)`

**Step 3: Implement Controller Endpoint**

`GET /api/documents/search?from=...&to=...`

**Step 4: Verify Pass**

Run: `./gradlew test`

**Step 5: Commit**

```bash
git add src/
git commit -m "feat: implement get documents by date range"
```
