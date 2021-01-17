package facades;

import dto.CoachDTO;
import dto.PlayerDTO;
import dto.SportDTO;
import dto.SportTeamDTO;
import dto.UserDTO;
import entities.Coach;
import entities.MemberInfo;
import entities.Player;
import entities.Role;
import entities.Sport;
import entities.SportTeam;
import entities.User;
import errorhandling.AlreadyExists;
import errorhandling.MissingInput;
import errorhandling.NotFoundException;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@Disabled
public class SportFacadeTest {

    private static EntityManagerFactory emf;
    private static SportFacade facade;
    private static User user, admin;
    private static SportTeam sportTeam;
    private static Player player;
    private static Coach coach;
    private static Sport sport;

    public SportFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = SportFacade.getSportFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }


    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        user = new User("user", "test1");
        admin = new User("admin", "test2");
        Role role1 = new Role("user");
        Role role2 = new Role("admin");
        sport = new Sport("football", "kick it");
        sportTeam = new SportTeam("Footballers", "best in the busines", 499999, 18, 40, sport);
        player = new Player("bob", "bob@bobmail.com", "8234924", 27, user);
        MemberInfo memberInfo = new MemberInfo(true, player, sportTeam);
        coach = new Coach("sven", "sven@svenmail.com", "73263421", admin);
        
        user.addRole(role1);
        admin.addRole(role2);
        coach.getSportTeams().add(sportTeam);
        sportTeam.getCoaches().add(coach);
        sportTeam.getMemberInfos().add(memberInfo);
        
        try {
        em.getTransaction().begin();
        em.createNamedQuery("MemberInfo.deleteAllRows").executeUpdate();
        em.createNamedQuery("SportTeam.deleteAllRows").executeUpdate();
        em.createNamedQuery("Sport.deleteAllRows").executeUpdate();
        em.createNamedQuery("Coach.deleteAllRows").executeUpdate();
        em.createNamedQuery("Player.deleteAllRows").executeUpdate();
        em.createNamedQuery("Roles.deleteAllRows").executeUpdate();
        em.createNamedQuery("User.deleteAllRows").executeUpdate();
        em.persist(sport);
        em.persist(sportTeam);
        em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }
    
    @Test
    public void testAddSport() throws MissingInput {
        Sport testSport = new Sport("testball", "for testing");
        SportDTO sportDTO = new SportDTO(testSport);
        SportDTO addedSport = facade.addSport(sportDTO);
        assertTrue(addedSport.getId() != testSport.getId());
    }
    
    @Test
    public void testGetAllSports() {
        List<SportDTO> sportDTOs = facade.getAllSports();
        assertEquals(sportDTOs.get(0).getName(), sport.getName());
    }
    
    @Test
    public void testAddSportTeam() throws MissingInput, NotFoundException, AlreadyExists {
        SportTeamDTO sDTO = new SportTeamDTO(sportTeam);
        sDTO.setTeamName("Testers");
        SportTeamDTO addedTeam = facade.addSportTeam(sDTO); 
        assertTrue(addedTeam.getId() != sDTO.getId());
    }
    
    @Test
    public void testGetAllSportTeams() {
        List<SportTeamDTO> sportTeamDTOs = facade.getAllSportTeams();
        assertEquals(1, sportTeamDTOs.size()); 
    }
    
    @Test
    public void testEditSportTeam() throws MissingInput, AlreadyExists {
        SportTeamDTO sDTO = new SportTeamDTO(sportTeam);
        sDTO.setTeamName("Testers");
        sDTO.setPricePerYear(9999999);
        SportTeamDTO editedDTO = facade.editSportTeam(sDTO);
        assertTrue(!sportTeam.getTeamName().equals(editedDTO.getTeamName()));
    }
    
    @Test
    public void testDeleteSportTeam() {
        facade.deleteSportTeam(sportTeam.getId());
        assertEquals(0, facade.getAllSportTeams().size());
    }
    
    @Test
    public void testAddPlayerToTeam() {
        PlayerDTO pDTO = facade.addPlayerToTeam(new PlayerDTO(player), sportTeam.getId());
        assertTrue(pDTO.getMemberInfoDTOs().get(0).getSportTeamName().equals(sportTeam.getTeamName()));
    }
    
    @Test
    public void testAddCoachToTeam() {
        CoachDTO cDTO = facade.addCoachToTeam(new CoachDTO(coach), sportTeam.getId());
        List<SportTeamDTO> teams = facade.getAllSportTeams();
        assertTrue(teams.get(0).getCoaches().get(0).getName().equals(cDTO.getName()));
    }
    
    @Test
    public void testAlreadyExists() {
        AlreadyExists thrown =
                assertThrows(AlreadyExists.class, () ->  {
                    facade.addSportTeam(new SportTeamDTO(sportTeam));
                });
        assertTrue(thrown.getMessage().equals("A team with this name already exists"));
    }
    
    @Test
    public void testMissingInput() {
        sport.setName("x");
        MissingInput thrown =
                assertThrows(MissingInput.class, () ->  {
                    facade.addSport(new SportDTO(sport));
                });
        assertTrue(thrown.getMessage().equals("Name and description must be minimum 3 characters long."));
    }
    
}