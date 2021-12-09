package gruppe8.nebula.models;

import gruppe8.nebula.entities.ResourceEntity;

public class Resource {
    private Long id;
    private String name;
    private String color;

    public Resource(ResourceEntity resource) {
        this.id = resource.id();
        this.name = resource.name();
        this.color = resource.color();
    }

    public Long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
