package com.sadqwes.kg.repo;

import com.sadqwes.kg.model.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NodeRepository extends JpaRepository<NodeEntity, Long> {
    List<NodeEntity> findByNameContainingIgnoreCase(String term);
}
