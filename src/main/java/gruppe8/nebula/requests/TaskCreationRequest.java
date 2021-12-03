package gruppe8.nebula.requests;
import gruppe8.nebula.models.Project;

public record TaskCreationRequest(Long id, Long projectId, Long parentId, String name, String startDate, String endDate,Long duration, Long resourceId) {

}
