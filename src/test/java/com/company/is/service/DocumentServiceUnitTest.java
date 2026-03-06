package com.company.is.service;

import com.company.is.domain.Document;
import com.company.is.domain.DocumentType;
import com.company.is.dto.CreateDocumentRequest;
import com.company.is.dto.DocumentResponse;
import com.company.is.mapper.DocumentMapper;
import com.company.is.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentServiceUnitTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private DocumentMapper documentMapper;

    @InjectMocks
    private DocumentService documentService;

    @Test
    void testCreateDocument_ShouldReturnResponse() {
        // Arrange
        CreateDocumentRequest request = new CreateDocumentRequest(
                "Test Doc",
                DocumentType.HIRING_REQUEST,
                "Content",
                "user1"
        );

        Document document = Document.builder()
                .name("Test Doc")
                .type(DocumentType.HIRING_REQUEST)
                .body("Content")
                .username("user1")
                .build();

        Document savedDocument = Document.builder()
                .id(1L)
                .name("Test Doc")
                .type(DocumentType.HIRING_REQUEST)
                .body("Content")
                .username("user1")
                .creationDate(LocalDateTime.now())
                .build();

        DocumentResponse response = new DocumentResponse(
                1L,
                "Test Doc",
                DocumentType.HIRING_REQUEST,
                "Content",
                LocalDateTime.now(),
                null,
                "user1"
        );

        when(documentMapper.toEntity(request)).thenReturn(document);
        when(documentRepository.save(any(Document.class))).thenReturn(savedDocument);
        when(documentMapper.toResponse(savedDocument)).thenReturn(response);

        // Act
        DocumentResponse result = documentService.createDocument(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Test Doc", result.name());
        verify(documentRepository).save(any(Document.class));
    }
}
