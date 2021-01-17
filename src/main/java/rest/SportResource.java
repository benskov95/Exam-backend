package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CoachDTO;
import dto.PlayerDTO;
import dto.SportDTO;
import dto.SportTeamDTO;
import errorhandling.AlreadyExists;
import errorhandling.MissingInput;
import facades.SportFacade;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("sport")
public class SportResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final SportFacade SPORT_FACADE = SportFacade.getSportFacade(EMF);
    
    @POST
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addSport(String sport) throws  AuthenticationException, MissingInput, AlreadyExists {
        SportDTO SportDTO = GSON.fromJson(sport, SportDTO.class);
        SportDTO newSport = SPORT_FACADE.addSport(SportDTO);
        return GSON.toJson(newSport);
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllSports() {
        List<SportDTO> sportDTOs = SPORT_FACADE.getAllSports();
        return GSON.toJson(sportDTOs);
    }
    
    @POST
    @RolesAllowed("admin")
    @Path("team")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addSportTeam(String sportTeam) throws errorhandling.NotFoundException, MissingInput, AlreadyExists {
        SportTeamDTO teamDTO = GSON.fromJson(sportTeam, SportTeamDTO.class);
        SportTeamDTO addedDTO = SPORT_FACADE.addSportTeam(teamDTO);
        return GSON.toJson(addedDTO);
    }
    
    @GET
    @Path("team")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllSportTeams() {
        List<SportTeamDTO> sportTeamDTOs = SPORT_FACADE.getAllSportTeams();
        return GSON.toJson(sportTeamDTOs);
    }
    
    @GET
    @RolesAllowed("admin")
    @Path("team/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getSportTeamById(@PathParam("id") int id) {
        SportTeamDTO teamDTO = SPORT_FACADE.getSportTeamById(id);
        return GSON.toJson(teamDTO);
    }
    
    @PUT
    @RolesAllowed("admin")
    @Path("team/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String editSportTeam(String sportTeam) throws MissingInput, AlreadyExists {
        SportTeamDTO teamDTO = GSON.fromJson(sportTeam, SportTeamDTO.class);
        SportTeamDTO editedDTO = SPORT_FACADE.editSportTeam(teamDTO);
        return GSON.toJson(editedDTO);
    }
    
    @DELETE
    @RolesAllowed("admin")
    @Path("team/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteSportTeam(@PathParam("id") int id) {
        SportTeamDTO deletedDTO = SPORT_FACADE.deleteSportTeam(id);
        return GSON.toJson(deletedDTO);
    }
    
    @POST
    @Path("player/{username}")
    @RolesAllowed("user")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addPlayer(String player, @PathParam("username") String username) {
        PlayerDTO pDTO = GSON.fromJson(player, PlayerDTO.class);
        PlayerDTO addedDTO = SPORT_FACADE.addPlayer(pDTO, username);
        return GSON.toJson(addedDTO);
    }
    
    @POST
    @Path("team/{teamId}/player")
    @RolesAllowed("player")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addPlayerToTeam(String player, @PathParam("teamId") int teamId) throws AlreadyExists {
        PlayerDTO pDTO = GSON.fromJson(player, PlayerDTO.class);
        PlayerDTO addedDTO = SPORT_FACADE.addPlayerToTeam(pDTO, teamId);
        return GSON.toJson(addedDTO);
    }
    
    @GET
    @Path("player/{username}")
    @RolesAllowed("player")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPlayerByUsername(@PathParam("username") String username) {
        PlayerDTO pDTO = SPORT_FACADE.getPlayerByUsername(username);
        return GSON.toJson(pDTO);
    }
    
    @POST
    @Path("coach/{username}")
    @RolesAllowed("user")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addCoach(String coach, @PathParam("username") String username) {
        CoachDTO cDTO = GSON.fromJson(coach, CoachDTO.class);
        CoachDTO addedDTO = SPORT_FACADE.addCoach(cDTO, username);
        return GSON.toJson(addedDTO);
    }
    
    @POST
    @Path("team/{teamId}/coach")
    @RolesAllowed("coach")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addCoachToTeam(String coach, @PathParam("teamId") int teamId) throws AlreadyExists {
        CoachDTO cDTO = GSON.fromJson(coach, CoachDTO.class);
        CoachDTO addedDTO = SPORT_FACADE.addCoachToTeam(cDTO, teamId);
        return GSON.toJson(addedDTO);
    }
    
    @GET
    @Path("coach/{username}")
    @RolesAllowed("coach")
    @Produces({MediaType.APPLICATION_JSON})
    public String getCoachByUsername(@PathParam("username") String username) {
        CoachDTO cDTO = SPORT_FACADE.getCoachByUsername(username);
        return GSON.toJson(cDTO);
    }
    
}
