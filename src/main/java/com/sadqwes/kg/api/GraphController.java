package com.sadqwes.kg.api;

import com.sadqwes.kg.model.NodeEntity;
import com.sadqwes.kg.repo.EdgeRepository;
import com.sadqwes.kg.repo.NodeRepository;
import com.sadqwes.kg.service.GraphService;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GraphController {
    private final GraphService graphService;
    private final NodeRepository nodes;
    private final EdgeRepository edges;

    public GraphController(GraphService graphService, NodeRepository nodes, EdgeRepository edges) {
        this.graphService = graphService;
        this.nodes = nodes;
        this.edges = edges;
    }

    @GetMapping("/api/search")
    public List<NodeEntity> search(@RequestParam String term) {
        return graphService.searchUnsafe(term);
    }

    @GetMapping("/api/path")
    public List<NodeEntity> path(@RequestParam Long from, @RequestParam Long to) {
        return graphService.shortestPath(from, to);
    }

    @GetMapping("/api/graph")
    public Map<String, Object> graph() {
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("nodes", nodes.findAll());
        out.put("edges", edges.findAll());
        return out;
    }
}
