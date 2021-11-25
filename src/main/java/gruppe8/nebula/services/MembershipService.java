package gruppe8.nebula.services;

import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.models.Membership;
import gruppe8.nebula.models.Team;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.repositories.MembershipRepository;
import gruppe8.nebula.requests.MembershipRequest;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
