package com.company.is.dto;

import com.company.is.domain.DocumentType;
import java.time.LocalDateTime;

public record DocumentResponse(
    Long id,
    String name,
    DocumentType type,
    String body,
    LocalDateTime creationDate,
    LocalDateTime signatureDate,
    String username
) {}
