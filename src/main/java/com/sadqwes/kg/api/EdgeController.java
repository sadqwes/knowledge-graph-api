package com.sadqwes.kg.api;

import com.sadqwes.kg.config.AdminAuth;
import com.sadqwes.kg.model.EdgeEntity;
import com.sadqwes.kg.repo.EdgeRepository;
import com.sadqwes.kg.repo.NodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/edges")
public class EdgeController {
    private final EdgeRepository edges;
    private final NodeRepository nodes;
    private final AdminAuth auth;

    public EdgeController(EdgeRepository edges, NodeRepository nodes, AdminAuth auth) {
        this.edges = edges;
        this.nodes = nodes;
        this.auth = auth;
    }

    @PostMapping
    public EdgeEntity create(@RequestBody Map<String, Object> body) {
        Long fromId = Long.valueOf(body.get("fromId").toString());
        Long toId = Long.valueOf(body.get("toId").toString());
        EdgeEntity edge = new EdgeEntity(
                nodes.findById(fromId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                nodes.findById(toId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                body.getOrDefault("label", "related").toString());
        return edges.save(edge);
    }

    @GetMapping
    public List<EdgeEntity> list() {
        return edges.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id,
                       @RequestHeader(value = "X-Admin-Token", required = false) String token) {
        if (!auth.isAdmin(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        edges.deleteById(id);
    }
}
