package com.mycompany.sessionbeans;

import com.mycompany.entityclasses.Roommate;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;


/**
 *
 * @author Andres
 */
@Stateless
public class RoommateFacade extends AbstractFacade<Roommate> {

    /*
    ----------------------------------------------------------------------------------------------------
    Annotating 'private EntityManager em;' with '@PersistenceContext(unitName = "PizzaHut-BalciPU")' 
    implies that the EntityManager instance pointed to by 'em' is associated with the 'PizzaHut-BalciPU'
    persistence context. The persistence context is a set of entity (Roommate) instances in which for
    any persistent entity (Roommate) identity, there is a unique entity (Roommate) instance.
    Within the persistence context, the entity (Roommate) instances and their life cycle are managed.
    The EntityManager API is used to create and remove persistent entity (Roommate) instances,
    to find entities by their primary key, and to query over entities (Roommates).
    ----------------------------------------------------------------------------------------------------
     */
    @PersistenceContext(unitName = "ApartMates-Group2")
    private EntityManager em;

    // @Override annotation indicates that the super class AbstractFacade's getEntityManager() method is overridden.
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /* 
    This constructor method invokes the parent abstract class AbstractFacade.java's
    constructor method AbstractFacade, which in turn initializes its entityClass instance
    variable with the Roommate class object reference returned by the Roommate.class.
     */
    public RoommateFacade() {
        super(Roommate.class);
    }

    /*
    -----------------------------------------------------
    The following methods are added to the generated code
    -----------------------------------------------------
     */
    /**
     * @param rommateID is the Primary Key of the Roommate entity in a table row in the PizzaHutDB database.
     * @return object reference of the Roommate entity whose primary key is id
     */
    public Roommate getRoommateFacade(int rommateID) {
        
        // The find method is inherited from the parent AbstractFacade class
        return em.find(Roommate.class, rommateID);
    }

    /**
     * @param id is the id attribute (column) value of the roommate
     * @return object reference of the roommate entity whose id is id
     */
    public Roommate findById(int id) {
        if (em.createQuery("SELECT c FROM Roommate c WHERE c.roommateID = :id")
                .setParameter("id", id)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Roommate) (em.createQuery("SELECT c FROM Roommate c WHERE c.roommateID = :id")
                    .setParameter("id", id)
                    .getSingleResult());
        }
    }
    
    /**
     * @param email is the username attribute (column) value of the roommate
     * @return object reference of the Roommate entity whose username is username
     */
    public Roommate findByEmail(String email) {
        if (em.createQuery("SELECT c FROM Roommate c WHERE c.email = :email")
                .setParameter("email", email)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Roommate) (em.createQuery("SELECT c FROM Roommate c WHERE c.email = :email")
                    .setParameter("email", email)
                    .getSingleResult());
        }
    }

    /**
     * Deletes the Roommate entity whose primary key is roommateID
     * @param rommateID is the Primary Key of the Roommate entity in a table row in the PizzaHutDB database.
     */
    public void deleteRoommate(int rommateID) {
        
        // The find method is inherited from the parent AbstractFacade class
        Roommate roommate = em.find(Roommate.class, rommateID);
        
        // The remove method is inherited from the parent AbstractFacade class
        em.remove(roommate); 
    }
    
    /**
     * Deletes the Roommate entity whose email is roommateEmail
     * @param roommateEmail is the email of the user currently logged in
     * @param apartmentID is the Primary Key of the Roommate entity in a table row in the ApartMatesDB database.
     * @return boolean that indicates whether roommate left the apartment successfully
     */
    public boolean leaveApartment(String roommateEmail, Integer apartmentID) {
        
        List<Roommate> roommates = new ArrayList<Roommate>(); 
        roommates = em.createQuery("SELECT c FROM Roommate c WHERE c.apartmentID = :apartmentID")
                    .setParameter("apartmentID", apartmentID)
                    .getResultList();
        
        if (roommates.size() > 1) {
            for(Roommate roommate : roommates) {
                if(Objects.equals(roommate.getEmail(), roommateEmail)) {
                    roommate.setApartmentID(null);
                    edit(roommate);
                    return true;
                }
            }
        }
        
        return false;
    }
        
    /**
     * Adds the Roommate entity whose email key is email
     * @param email is the Primary Key of the Roommate entity in a table row in the PizzaHutDB database.
     * @param apartmentID primary key to which the roommate has to be added
     * @return String that contains message indicating if the add was successful
     */
    public String inviteToApartment(String email, Integer apartmentID) {
        
        
        if (em.createQuery("SELECT c FROM Roommate c WHERE c.email = :email")
                .setParameter("email", email)
                .getResultList().isEmpty()) 
            return "Email is not registered";
        
        else {
            Roommate roommate;
            roommate = (Roommate) em.createQuery("SELECT c FROM Roommate c WHERE c.email = :email")
                        .setParameter("email", email)
                        .getSingleResult();

            if (roommate.getApartmentID() == null) {
                roommate.setApartmentID(apartmentID);
                edit(roommate);
                return "Roommate invited successfully";
            }
            
            else if(roommate.getApartmentID() == apartmentID) {
                return "Roommate already belongs to this apartment";                
            }
            
            else
                return "Roommate already belongs to an apartment";
        }
    }
    
    public void addApartmentIDs(Integer apartmentID, List<String> emails) {
        
        Roommate roommate;
        
        for(String email : emails) {
            roommate = (Roommate) em.createQuery("SELECT c FROM Roommate c WHERE c.email = :email")
                        .setParameter("email", email)
                        .getSingleResult();

            roommate.setApartmentID(apartmentID);
            edit(roommate);
        }
    }
    
    /**
     * Deletes the Apartment entity whose primary key is apartmentID
     * @param apartmentID is the Primary Key of the Apartment entity in a table row in the PizzaHutDB database.
     */
    public void removeApartmentIDs(Integer apartmentID) {
        
        List<Roommate> roommates = new ArrayList<Roommate>(); 
        roommates = em.createQuery("SELECT c FROM Roommate c WHERE c.apartmentID = :apartmentID")
                    .setParameter("apartmentID", apartmentID)
                    .getResultList();
        
        for(Roommate roommate : roommates) {
            roommate.setApartmentID(null);
            edit(roommate);
        }
    }
    
    public List<Roommate> findApartmentRoommates(Integer apartmentID) {
        
        List<Roommate> roommates = new ArrayList<Roommate>(); 
        roommates = em.createQuery("SELECT c FROM Roommate c WHERE c.apartmentID = :apartmentID")
                    .setParameter("apartmentID", apartmentID)
                    .getResultList();
        
        return roommates;
    }
    
}
