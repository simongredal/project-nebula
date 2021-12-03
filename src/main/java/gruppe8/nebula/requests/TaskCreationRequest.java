package gruppe8.nebula.requests;


public record TaskCreationRequest(Long id, Long projectId, Long parentId, String name, String startDate, String endDate) {

}
