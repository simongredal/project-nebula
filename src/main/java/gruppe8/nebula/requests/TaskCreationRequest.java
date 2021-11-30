package gruppe8.nebula.requests;

import gruppe8.nebula.models.Project;

import java.sql.Date;


public record TaskCreationRequest(Long id, Long projectId, Long parentId, String name, Date startDate, Date endDate) {

}
