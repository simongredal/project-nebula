package gruppe8.nebula.services;

import gruppe8.nebula.entities.MembershipEntity;
import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.models.Membership;
import gruppe8.nebula.models.Team;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.repositories.MembershipRepository;
import gruppe8.nebula.requests.MembershipRequest;
import gruppe8.nebula.requests.MembershipUpdateRequest;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {
    private final MembershipRepository membershipRepository;

    public MembershipService(MembershipRepository membershipRepository){
        this.membershipRepository = membershipRepository;
    }

    public Boolean addMembership(TeamEntity from, Account to) {
        return membershipRepository.createMembership(from, to, true);
    }

    public Boolean sendInvitation(TeamEntity from, Account to) {
        return membershipRepository.createMembership(from, to, false);
    }

    public List<MembershipEntity> getMembershipsForAccount(Account account, Boolean accepted) {
        return membershipRepository.getMemberships(account.getId(), accepted);
    }

    public Boolean accountOwnsMembership(Account account, MembershipUpdateRequest request) {
        return membershipRepository.accountOwnsMembership(account.getId(), request.membershipId());
    }

    public Boolean rejectInvitation(MembershipUpdateRequest request) {
        return membershipRepository.rejectInvitation(request.membershipId());
    }
}
