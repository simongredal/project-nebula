package gruppe8.nebula.entities;

public record MembershipEntity(
        Long accountId,
        String accountName,
        String accountEmail,
        Long teamId,
        String teamName,
        Long membershipId,
        Boolean membershipAccepted,
        Integer membershipCount,
        Integer invitationCount) {
}
