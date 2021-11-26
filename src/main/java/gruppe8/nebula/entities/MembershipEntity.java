package gruppe8.nebula.entities;

public record MembershipEntity(
        Long accountId,
        String accountName,
        String accountEmail,
        Long teamId,
        String teamName,
        Boolean membershipAccepted,
        Integer membershipCount) {
}
