package facades;

import dto.PlayerDTO;
import dto.SportDTO;
import dto.SportTeamDTO;
import dto.UserDTO;
import entities.Sport;
import entities.SportTeam;
import errorhandling.AlreadyExists;
import errorhandling.MissingInput;
import errorhandling.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class SportFacade {
    
    private static EntityManagerFactory emf;
    private static SportFacade instance;

    public static SportFacade getSportFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SportFacade();
        }
        return instance;
    }
    
    public SportDTO addSport(SportDTO sportDTO) throws MissingInput {
        EntityManager em = emf.createEntityManager();
        Sport sport = new Sport(sportDTO.getName(), sportDTO.getDescription());
        
        if (sport.getName().length() < 3 || sport.getDescription().length() < 3) {
            throw new MissingInput("Name and description must be minimum 3 characters long.");
        }
        
        try {
            em.getTransaction().begin();
            em.persist(sport);
            em.getTransaction().commit();
            return new SportDTO(sport);
        } finally {
            em.close();
        }
    }
    
    public List<SportDTO> getAllSports() {
        EntityManager em = emf.createEntityManager();
        
        try {
            TypedQuery q = em.createQuery("SELECT s FROM Sport s", Sport.class);
            List<Sport> sports = q.getResultList();
            List<SportDTO> sportDTOs = new ArrayList<>();
            
            for (Sport sport : sports) {
                sportDTOs.add(new SportDTO(sport));
            }
            return sportDTOs;
            
        } finally {
            em.close();
        }
    }
    
    public SportTeamDTO addSportTeam(SportTeamDTO teamDTO) throws MissingInput, NotFoundException {
        EntityManager em = emf.createEntityManager();
        checkInput(teamDTO);
        
        if (teamDTO.getMaxAge() < teamDTO.getMinAge()) {
            throw new MissingInput("Max age must be higher than minimum age");
        }
        
        SportTeam sportTeam = prepareSportTeam(teamDTO);
        try {
            em.getTransaction().begin();
            em.persist(sportTeam);
            em.getTransaction().commit();
            return new SportTeamDTO(sportTeam);
        } finally {
            em.close();
        }
        
    }
    
    private void checkInput(SportTeamDTO teamDTO, EntityManager em) throws MissingInput, AlreadyExists {
        Query q = em.createQuery("SELECT  s FROM SportTeam s WHERE s.teamName = :name");
        q.setParameter("name", teamDTO.getTeamName());
        
        if (q.getSingleResult() != null) {
            throw new AlreadyExists("A team with this name already exists");
        }
        if (teamDTO.getTeamName().length() < 3 ||
            teamDTO.getSport().length() < 3 ||
            teamDTO.getPricePerYear() < 1 ||
            teamDTO.getMinAge() < 1 ||
            teamDTO.getMaxAge() < 1 ||
            teamDTO.getDescription().length() < 3) 
        {
            throw new MissingInput("All fields must be filled out.");
        } 
    }
    
    private Sport getSport(String sportName, EntityManager em) throws NotFoundException {
        try {
            Sport sport = em.find(Sport.class, sportName);
            if (sport == null) {
                throw new NotFoundException("This sport does not exist in the database.");
            } else {
                return sport;
            }
        } finally {
            em.close();
        }
    }
    
    private SportTeam prepareSportTeam(SportTeamDTO teamDTO, EntityManager em) throws NotFoundException {
        Sport sport = getSport(teamDTO.getSport(), em);
        SportTeam sportTeam =  new SportTeam(
                teamDTO.getTeamName(), 
                teamDTO.getDescription(),
                teamDTO.getPricePerYear(),
                teamDTO.getMinAge(), 
                teamDTO.getMaxAge(),
                sport
                );
        return sportTeam;
    }
    
}
