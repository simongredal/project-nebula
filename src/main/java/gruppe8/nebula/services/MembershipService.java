package gruppe8.nebula.services;

import gruppe8.nebula.models.Team;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.repositories.MembershipRepository;
import gruppe8.nebula.requests.MembershipRequest;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final Argon2PasswordEncoder passwordEncoder;

    public MembershipService(MembershipRepository membershipRepository, Argon2PasswordEncoder passwordEncoder){
        this.membershipRepository = membershipRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Boolean addMembership(MembershipRequest request) {
        Team team = new Team(
                request.team_id(),
                null
        );
        Account account = new Account(
                request.account_id(),
                "",
                "",
                ""
        );

        return membershipRepository.createMembership(team,account);
    }
}
