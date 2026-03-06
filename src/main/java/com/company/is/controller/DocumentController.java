package com.company.is.controller;

import com.company.is.domain.Document;
import com.company.is.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping
    public Document createDocument(@RequestBody Document document) {
        return documentService.createDocument(document);
    }

    @org.springframework.web.bind.annotation.PutMapping("/{id}")
    public Document updateDocument(@org.springframework.web.bind.annotation.PathVariable Long id, @RequestBody Document document) {
        return documentService.updateDocument(id, document);
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
    }
}
