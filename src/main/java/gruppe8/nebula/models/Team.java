package gruppe8.nebula.models;

import gruppe8.nebula.entities.TeamEntity;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private Long id;
    private String name;
    private final List<Project> projects = new ArrayList<>();

    public Team() {}

    public Team(TeamEntity teamEntity) {
        this.id = teamEntity.id();
        this.name = teamEntity.name();
    }

    public List<Project> getProjects() {
        return projects;
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
