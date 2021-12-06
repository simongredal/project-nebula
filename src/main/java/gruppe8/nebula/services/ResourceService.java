package gruppe8.nebula.services;

import gruppe8.nebula.entities.ResourceEntity;
import gruppe8.nebula.entities.TaskEntity;
import gruppe8.nebula.repositories.ResourceRepository;
import gruppe8.nebula.requests.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {
    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public List<ResourceEntity> getResourcesFromProject(Long projectId) {
        return resourceRepository.getResourcesForProject(projectId);
    }

    public Boolean createResource(ResourceCreationRequest request) {
        ResourceEntity resource = new ResourceEntity(
                request.id(),
                request.project_id(),
                request.name(),
                request.color());
        return resourceRepository.createResource(resource);
    }

    public Boolean updateResource(ResourceUpdateRequest request) {
        ResourceEntity resource = new ResourceEntity(
                request.id(),
                request.team_id(),
                request.name(),
                request.color());
        return resourceRepository.editResource(resource);
    }

    public Boolean deleteResource(ResourceDeletionRequest request) {
        return resourceRepository.deleteResource(request.resourceId());
    }
}
