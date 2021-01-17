package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    public String addSport(String sport) throws  AuthenticationException, MissingInput {
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
    
    @PUT
    @RolesAllowed("admin")
    @Path("team/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String editSportTeam(String sportTeam, @PathParam("id") int id) throws MissingInput, AlreadyExists {
        SportTeamDTO teamDTO = GSON.fromJson(sportTeam, SportTeamDTO.class);
        teamDTO.setId(id);
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
}
