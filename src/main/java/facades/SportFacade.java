package facades;

import dto.SportDTO;
import entities.Sport;
import errorhandling.MissingInput;
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
    
}
