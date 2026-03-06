package com.company.is.dto;

import com.company.is.domain.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateDocumentRequest(
    @NotBlank(message = "Name is required")
    String name,
    
    @NotNull(message = "Type is required")
    DocumentType type,
    
    String body,
    
    String username
) {}
