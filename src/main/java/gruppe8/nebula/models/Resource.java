package gruppe8.nebula.models;

import gruppe8.nebula.entities.ResourceEntity;

public class Resource {
    private Long id;
    private String color;

    public Resource(ResourceEntity resource) {
        this.id = resource.id();
        this.color = resource.color();
    }
}
