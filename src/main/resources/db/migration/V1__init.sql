CREATE TABLE nodes (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255)  NOT NULL UNIQUE,
    description VARCHAR(4000),
    tags        VARCHAR(1000)
);

CREATE TABLE edges (
    id      BIGSERIAL PRIMARY KEY,
    from_id BIGINT NOT NULL REFERENCES nodes(id) ON DELETE CASCADE,
    to_id   BIGINT NOT NULL REFERENCES nodes(id) ON DELETE CASCADE,
    label   VARCHAR(255)
);

CREATE INDEX idx_edges_from ON edges(from_id);
CREATE INDEX idx_edges_to   ON edges(to_id);
