package rest;

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
import utils.EMF_Creator;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

import io.restassured.parsing.Parser;

import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//@Disabled

public class SportResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static User user, admin;
    private static Sport sport;
    private static SportTeam sportTeam;
    private static Player player;
    private static Coach coach;


    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
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

    private static String securityToken;

    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    @Test
    public void testServerIsUp() {
        given().when().get("users/count").then().statusCode(200);
    }
    
    @Test
    public void testAddSport() {
        SportDTO sDTO = new SportDTO(sport);
        sDTO.setName("Test sport");
        login("admin", "test2");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(sDTO)
                .when()
                .post("/sport")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("name", equalTo("Test sport"));
    }
    
    @Test
    public void testGetAllSports() {
        List<SportDTO> sportDTOs;
        
        sportDTOs = given()
                .contentType("application/json")
                .get("/sport")
                .then()
                .extract().body().jsonPath().getList("", SportDTO.class);
        
        assertThat(sportDTOs.size(), equalTo(1));
    }
    
    @Test
    public void testAddSportTeam() {
        SportTeamDTO sDTO = new SportTeamDTO(sportTeam);
        sDTO.setTeamName("Testers");
        login("admin", "test2");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(sDTO)
                .when()
                .post("/sport/team")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("teamName", equalTo("Testers"));
    }
    
    @Test
    public void testGetAllSportTeams() {
        List<SportTeamDTO> teamDTOs;
        
        teamDTOs = given()
                .contentType("application/json")
                .get("/sport/team")
                .then()
                .extract().body().jsonPath().getList("", SportTeamDTO.class);
        
        assertThat(teamDTOs.size(), equalTo(1));
    }
    
    @Test
    public void testDeleteSportTeam() {
        login("admin", "test2");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .delete("/sport/team/{id}", sportTeam.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("teamName", equalTo(sportTeam.getTeamName()));
    }
    
    @Test
    public void testEditSportTeam() {
        sportTeam.setDescription("this team has been edited");
        SportTeamDTO editDTO = new SportTeamDTO(sportTeam);
        
        login("admin", "test2");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(editDTO)
                .put("/sport/team/{id}", sportTeam.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("description", equalTo("this team has been edited"));
                
    }
}