package com.company.is.service;

import com.company.is.domain.Document;
import com.company.is.dto.CreateDocumentRequest;
import com.company.is.dto.DocumentResponse;
import com.company.is.dto.UpdateDocumentRequest;
import com.company.is.exception.DocumentNotFoundException;
import com.company.is.mapper.DocumentMapper;
import com.company.is.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public DocumentResponse createDocument(CreateDocumentRequest request) {
        Document document = documentMapper.toEntity(request);
        if (document.getCreationDate() == null) {
            document.setCreationDate(LocalDateTime.now());
        }
        Document savedDocument = documentRepository.save(document);
        return documentMapper.toResponse(savedDocument);
    }

    public DocumentResponse updateDocument(Long id, UpdateDocumentRequest request) {
        Document existing = documentRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found with id: " + id));
        
        documentMapper.updateEntityFromDto(request, existing);
        
        Document savedDocument = documentRepository.save(existing);
        return documentMapper.toResponse(savedDocument);
    }

    public void deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new DocumentNotFoundException("Document not found with id: " + id);
        }
        documentRepository.deleteById(id);
    }

    public DocumentResponse getDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found with id: " + id));
        return documentMapper.toResponse(document);
    }
}
