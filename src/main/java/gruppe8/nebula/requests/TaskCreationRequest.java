package gruppe8.nebula.requests;
import gruppe8.nebula.models.Project;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record TaskCreationRequest(
        Long id,
        Long projectId,
        Long parentId,
        String name,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
        Long duration,
        Long resourceId) {
}
