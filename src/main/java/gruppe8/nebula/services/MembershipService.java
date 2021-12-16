// Authors Simon Gredal & Mark Friis Larsen
package gruppe8.nebula.services;

import gruppe8.nebula.entities.MembershipEntity;
import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.repositories.MembershipRepository;
import gruppe8.nebula.requests.MembershipUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {
    private final MembershipRepository membershipRepository;

    public MembershipService(MembershipRepository membershipRepository){
        this.membershipRepository = membershipRepository;
    }


    public Boolean addMembership(TeamEntity from, Account to) {
        return membershipRepository.createMembership(from.getId(), to.id(), true);
    }
    public Boolean sendInvitation(TeamEntity from, Account to) {
        return membershipRepository.createMembership(from.getId(), to.id(), false);
    }
    public Boolean sendInvitation(Long teamId, Long accountId) {
        return membershipRepository.createMembership(teamId, accountId, false);
    }


    public List<MembershipEntity> getMembershipsForAccount(Account account, Boolean membershipAccepted) {
        return membershipRepository.getMembershipsForAccount(account.id(), membershipAccepted);
    }
    public List<MembershipEntity> getMembershipsForTeam(Long teamId, Boolean membershipAccepted) {
        return membershipRepository.getMembershipsForTeam(teamId, membershipAccepted);
    }


    public Boolean accountOwnsMembership(Account account, MembershipUpdateRequest request) {
        return membershipRepository.accountOwnsMembership(account.id(), request.membershipId());
    }
    public Boolean accountHasMembershipInTeam(Account account, Long teamId) {
        return membershipRepository.accountHasMembershipInTeam(account.id(), teamId);
    }


    public Boolean acceptMembership(MembershipUpdateRequest request) {
        return membershipRepository.acceptMembership(request.membershipId());
    }
    public Boolean rejectMembership(MembershipUpdateRequest request) {
        return membershipRepository.deleteMembership(request.membershipId());
    }

    public Boolean membershipIsPartOfTeam(Long membershipId, Long teamId) {
        return membershipRepository.membershipIsPartOfTeam(membershipId, teamId);
    }

    public Boolean deleteMembership(Long membershipId) {
        return membershipRepository.deleteMembership(membershipId);
    }
}
