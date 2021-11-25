package gruppe8.nebula.models;

public class Team {
    private Long id;
    private String name;

    public Team(Long id,String name) {
        this.id = id;
        this.name = name;
    }
    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
