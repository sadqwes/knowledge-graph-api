package com.sadqwes.kg.api;

import com.sadqwes.kg.config.AdminAuth;
import com.sadqwes.kg.model.NodeEntity;
import com.sadqwes.kg.repo.NodeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/nodes")
public class NodeController {
    private final NodeRepository nodes;
    private final AdminAuth auth;

    public NodeController(NodeRepository nodes, AdminAuth auth) {
        this.nodes = nodes;
        this.auth = auth;
    }

    @GetMapping
    public List<NodeEntity> search(@RequestParam(required = false) String term) {
        return term == null ? nodes.findAll() : nodes.findByNameContainingIgnoreCase(term);
    }

    @PostMapping
    public NodeEntity create(@Valid @RequestBody NodeDto dto) {
        return nodes.save(new NodeEntity(dto.name(), dto.description(), dto.tags()));
    }

    // Admin-only endpoint: delete requires the admin token in the header.
    // The token is injected from the environment (never hardcoded).
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @RequestHeader(value = "X-Admin-Token", required = false) String token) {
        if (!auth.isAdmin(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "invalid admin token");
        }
        nodes.deleteById(id);
    }
}
