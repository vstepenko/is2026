package com.company.is.service;

import com.company.is.domain.Document;
import com.company.is.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public Document createDocument(Document document) {
        if (document.getCreationDate() == null) {
            document.setCreationDate(LocalDateTime.now());
        }
        return documentRepository.save(document);
    }

    public Document updateDocument(Long id, Document incoming) {
        Document existing = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        existing.setName(incoming.getName());
        existing.setType(incoming.getType());
        existing.setBody(incoming.getBody());
        return documentRepository.save(existing);
    }

    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }
}
