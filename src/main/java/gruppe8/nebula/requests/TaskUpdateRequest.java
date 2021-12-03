package gruppe8.nebula.requests;

public record TaskUpdateRequest(Long id, Long projectId, Long parentId, String name, String startDate, String endDate,Long duration, Long resourceId) {
}
