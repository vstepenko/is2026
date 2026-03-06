package com.company.is.mapper;

import com.company.is.domain.Document;
import com.company.is.dto.CreateDocumentRequest;
import com.company.is.dto.DocumentResponse;
import com.company.is.dto.UpdateDocumentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    Document toEntity(CreateDocumentRequest request);

    DocumentResponse toResponse(Document document);

    void updateEntityFromDto(UpdateDocumentRequest request, @MappingTarget Document document);
}
