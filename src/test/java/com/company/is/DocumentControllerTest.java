package com.company.is;

import com.company.is.domain.Document;
import com.company.is.domain.DocumentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private com.company.is.repository.DocumentRepository documentRepository;

    @Test
    public void createDocument_ShouldReturnCreatedDocument() throws Exception {
        com.company.is.dto.CreateDocumentRequest request = new com.company.is.dto.CreateDocumentRequest(
            "Test Doc",
            DocumentType.VACATION_REQUEST,
            "Please approve vacation",
            "user1"
        );

        mockMvc.perform(post("/api/documents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.creationDate").exists());
    }

    @Test
    public void updateDocument_ShouldReturnUpdatedDocument() throws Exception {
        Document doc = new Document();
        doc.setName("Initial Name");
        doc.setType(DocumentType.HIRING_REQUEST);
        doc.setUsername("user1");
        doc.setCreationDate(java.time.LocalDateTime.now());
        doc = documentRepository.save(doc);

        com.company.is.dto.UpdateDocumentRequest updateRequest = new com.company.is.dto.UpdateDocumentRequest(
            "Updated Name",
            DocumentType.DISMISSAL_REQUEST,
            "Updated Body"
        );

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/documents/" + doc.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.type").value("DISMISSAL_REQUEST"));
    }

    @Test
    public void deleteDocument_ShouldReturnOk() throws Exception {
        Document doc = new Document();
        doc.setName("To Delete");
        doc.setType(DocumentType.VACATION_REQUEST);
        doc = documentRepository.save(doc);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/api/documents/" + doc.getId()))
                .andExpect(status().isOk());
    }

//    @Test
//    public void getDocumentsByUser_ShouldReturnList() throws Exception {
//        Document doc1 = new Document();
//        doc1.setName("Doc 1");
//        doc1.setUsername("user1");
//        documentRepository.save(doc1);
//
//        Document doc2 = new Document();
//        doc2.setName("Doc 2");
//        doc2.setUsername("user2");
//        documentRepository.save(doc2);
//
//        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/documents/user/user1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$.length()").value(1))
//                .andExpect(jsonPath("$[0].username").value("user1"));
//    }
}
