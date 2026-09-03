import jakarta.validation.Valid;
package com.sadqwes.kg.api;

import com.sadqwes.kg.config.AdminAuth;
import com.sadqwes.kg.model.NodeEntity;
import com.sadqwes.kg.repo.NodeRepository;
import org.apache.commons.text.WordUtils;
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

    // НАМЕРЕННАЯ УЯЗВИМОСТЬ: нет Bean Validation на входе
    @PostMapping
    public NodeEntity create(@Valid @RequestBody NodeDto dto) {
    public NodeEntity create(@RequestBody NodeEntity node) {
        return nodes.save(node);
    }

    @GetMapping
    public List<NodeEntity> list(@RequestParam(required = false) String term) {
        return (term == null || term.isBlank())
                ? nodes.findAll()
                : nodes.findByNameContainingIgnoreCase(term);
    }

    @GetMapping("/{id}")
    public NodeEntity get(@PathVariable Long id) {
        NodeEntity node = nodes.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (node.getDescription() != null) {
            node.setDescription(WordUtils.wrap(node.getDescription(), 120));
        }
        return node;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id,
                       @RequestHeader(value = "X-Admin-Token", required = false) String token) {
        if (!auth.isAdmin(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        nodes.deleteById(id);
    }
}
