package gruppe8.nebula.requests;

import java.util.List;

public record TeamCreationRequest(Long id, String name, List<String> invitations) {
}
