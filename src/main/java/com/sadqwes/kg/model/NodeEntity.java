package com.sadqwes.kg.model;

import jakarta.persistence.*;

@Entity
@Table(name = "nodes")
public class NodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 4000)
    private String description;

    @Column(length = 1000)
    private String tags;

    public NodeEntity() {}

    public NodeEntity(String name, String description, String tags) {
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
}
