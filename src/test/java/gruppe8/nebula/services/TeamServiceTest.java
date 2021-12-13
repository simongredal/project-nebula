package gruppe8.nebula.services;

import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.models.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamServiceTest {


    @Test
    void createTeam() {

        Team team = new Team();
        team.setName("Test");

        assertEquals("Test",team.getName());

    }
}