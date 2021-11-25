package gruppe8.nebula.entities;

public record MembershipEntity(Long id, Long teamId, Long accountId, boolean accepted) {
}
