package com.sadqwes.kg.service;

import com.sadqwes.kg.model.EdgeEntity;
import com.sadqwes.kg.model.NodeEntity;
import com.sadqwes.kg.repo.EdgeRepository;
import com.sadqwes.kg.repo.NodeRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphService {
    private final NodeRepository nodes;
    private final EdgeRepository edges;
    private final EntityManager em;

    public GraphService(NodeRepository nodes, EdgeRepository edges, EntityManager em) {
        this.nodes = nodes;
        this.edges = edges;
        this.em = em;
    }

    // НАМЕРЕННАЯ УЯЗВИМОСТЬ (SAST/DAST-тренировка): SQL-инъекция через конкатенацию
    @SuppressWarnings("unchecked")
    public List<NodeEntity> searchUnsafe(String term) {
        return em.createNativeQuery(
                        "SELECT * FROM nodes WHERE name ILIKE '%" + term + "%'",
                        NodeEntity.class)
                .getResultList();
    }

    public List<NodeEntity> shortestPath(Long fromId, Long toId) {
        Map<Long, List<Long>> adj = new HashMap<>();
        for (EdgeEntity e : edges.findAll()) {
            adj.computeIfAbsent(e.getFrom().getId(), k -> new ArrayList<>()).add(e.getTo().getId());
            adj.computeIfAbsent(e.getTo().getId(), k -> new ArrayList<>()).add(e.getFrom().getId());
        }

        Map<Long, Long> prev = new HashMap<>();
        Deque<Long> queue = new ArrayDeque<>();
        queue.add(fromId);
        prev.put(fromId, null);

        while (!queue.isEmpty()) {
            Long cur = queue.poll();
            if (cur.equals(toId)) break;
            for (Long next : adj.getOrDefault(cur, List.of())) {
                if (!prev.containsKey(next)) {
                    prev.put(next, cur);
                    queue.add(next);
                }
            }
        }

        if (!prev.containsKey(toId)) return List.of();

        LinkedList<NodeEntity> path = new LinkedList<>();
        Long cur = toId;
        while (cur != null) {
            path.addFirst(nodes.findById(cur).orElseThrow());
            cur = prev.get(cur);
        }
        return path;
    }
}
