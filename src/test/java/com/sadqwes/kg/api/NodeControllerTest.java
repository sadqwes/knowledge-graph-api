package com.sadqwes.kg.api;

import com.sadqwes.kg.model.NodeEntity;
import com.sadqwes.kg.repo.NodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NodeControllerTest {
    @Autowired MockMvc mvc;
    @Autowired NodeRepository repo;

    @Test
    void createAndSearchNode() throws Exception {
        mvc.perform(post("/api/nodes").contentType(APPLICATION_JSON)
                        .content("{\"name\":\"velero\",\"description\":\"backup tool\",\"tags\":\"dr,backup\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("velero"));

        mvc.perform(get("/api/nodes").param("term", "vel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("velero"));
    }

    @Test
    void deleteRequiresAdminToken() throws Exception {
        NodeEntity n = repo.save(new NodeEntity("temp", null, null));

        mvc.perform(delete("/api/nodes/" + n.getId()))
                .andExpect(status().isForbidden());

        mvc.perform(delete("/api/nodes/" + n.getId()).header("X-Admin-Token", "kg-admin-2026"))
                .andExpect(status().isNoContent());
    }
}
