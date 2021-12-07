package gruppe8.nebula.entities;

public final class TeamEntity {
    private Long id;
    private String name;

    public TeamEntity() {}

    public Long id() { return id; }
    public void setId(Long id) { this.id = id; }

    public String name() { return name; }
    public void setName(String name) { this.name = name; }
}
