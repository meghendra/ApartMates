package com.mycompany.sessionbeans;

import com.mycompany.entityclasses.Apartment;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Andres
 */
@Stateless
public class ApartmentFacade extends AbstractFacade<Apartment> {

    @PersistenceContext(unitName = "ApartMates-Group2")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ApartmentFacade() {
        super(Apartment.class);
    }
    
    /*
    -----------------------------------------------------
    The following methods are added to the generated code
    -----------------------------------------------------
     */
    /**
     * @param apartmentID is the Primary Key of the Apartment entity in a table row in the PizzaHutDB database.
     * @return object reference of the Apartment entity whose primary key is id
     */
    public Apartment getApartmentFacade(int apartmentID) {
        
        // The find method is inherited from the parent AbstractFacade class
        return em.find(Apartment.class, apartmentID);
    }

    /**
     * Stores the newly CREATED Apartment (entity) object into the database
     *
     * @param apartment contains object reference of the Apartment
     * @return int with aptID
     */
    public int ownCreate(Apartment apartment) {
        em.persist(apartment);
        em.flush();
        return apartment.getApartmentID();
    }
    
    /**
     * Deletes the Apartment entity whose primary key is id
     * @param apartmentID is the Primary Key of the Apartment entity in a table row in the PizzaHutDB database.
     */
    public void deleteApartment(int apartmentID) {
        
        // The find method is inherited from the parent AbstractFacade class
        Apartment apartment = em.find(Apartment.class, apartmentID);
        
        // The remove method is inherited from the parent AbstractFacade class
        em.remove(apartment); 
    }
}
