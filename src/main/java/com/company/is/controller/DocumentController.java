package com.company.is.controller;

import com.company.is.dto.CreateDocumentRequest;
import com.company.is.dto.DocumentResponse;
import com.company.is.dto.UpdateDocumentRequest;
import com.company.is.service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public DocumentResponse createDocument(@RequestBody @Valid CreateDocumentRequest request) {
        return documentService.createDocument(request);
    }

    @PutMapping("/{id}")
    public DocumentResponse updateDocument(@PathVariable Long id, @RequestBody @Valid UpdateDocumentRequest request) {
        return documentService.updateDocument(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
    }

    @GetMapping("/{id}")
    public DocumentResponse getDocument(@PathVariable Long id) {
        return documentService.getDocument(id);
    }
}
