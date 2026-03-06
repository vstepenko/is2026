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
        Document doc = new Document();
        doc.setName("Test Doc");
        doc.setType(DocumentType.VACATION_REQUEST);
        doc.setBody("Please approve vacation");
        doc.setUsername("user1");

        mockMvc.perform(post("/api/documents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doc)))
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

        Document updateDoc = new Document();
        updateDoc.setName("Updated Name");
        updateDoc.setType(DocumentType.DISMISSAL_REQUEST);
        updateDoc.setBody("Updated Body");

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/documents/" + doc.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDoc)))
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
}
