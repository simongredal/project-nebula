package gruppe8.nebula.requests;

import gruppe8.nebula.models.Project;

import java.sql.Date;
import java.time.LocalDateTime;


public record TaskCreationRequest(Long id, Long projectId, Long parentId, String name, LocalDateTime startDate, LocalDateTime endDate) {

}
