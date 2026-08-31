package com.sadqwes.kg.service;

import com.sadqwes.kg.model.EdgeEntity;
import com.sadqwes.kg.model.NodeEntity;
import com.sadqwes.kg.repo.EdgeRepository;
import com.sadqwes.kg.repo.NodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class GraphServiceTest {
    @Autowired NodeRepository nodes;
    @Autowired EdgeRepository edges;
    @Autowired GraphService graphService;

    @Test
    void findsShortestPath() {
        NodeEntity a = nodes.save(new NodeEntity("restore", null, "dr"));
        NodeEntity b = nodes.save(new NodeEntity("kopia", null, "storage"));
        NodeEntity c = nodes.save(new NodeEntity("mc-mirror", null, "offsite"));

        edges.save(new EdgeEntity(a, b, "uses"));
        edges.save(new EdgeEntity(b, c, "synced-by"));

        List<NodeEntity> path = graphService.shortestPath(a.getId(), c.getId());

        assertEquals(3, path.size());
        assertEquals("restore", path.get(0).getName());
        assertEquals("mc-mirror", path.get(2).getName());
    }
}
