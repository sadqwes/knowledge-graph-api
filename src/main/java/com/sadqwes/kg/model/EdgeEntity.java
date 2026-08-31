package com.sadqwes.kg.model;

import jakarta.persistence.*;

@Entity
@Table(name = "edges")
public class EdgeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "from_id")
    private NodeEntity from;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "to_id")
    private NodeEntity to;

    private String label;

    public EdgeEntity() {}

    public EdgeEntity(NodeEntity from, NodeEntity to, String label) {
        this.from = from;
        this.to = to;
        this.label = label;
    }

    public Long getId() { return id; }
    public NodeEntity getFrom() { return from; }
    public NodeEntity getTo() { return to; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}
