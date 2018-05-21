package com.mycompany.sessionbeans;

import com.mycompany.entityclasses.Task;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author megh
 */
@Stateless
public class TaskFacade extends AbstractFacade<Task> {

    @PersistenceContext(unitName = "ApartMates-Group2")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TaskFacade() {
        super(Task.class);
    }
    
    /**
     * @param apartmentID is the apartmentID attribute (column) value of the Task
     * @return object reference of the Task entity whose apartmentID is apartmentID
     */
    public List<Task> findByApartmentID(int apartmentID) {
        if (em.createQuery("SELECT c FROM Task c WHERE c.apartmentID = :apartmentID")
                .setParameter("apartmentID", apartmentID)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (List<Task>) (em.createQuery("SELECT c FROM Task c WHERE c.apartmentID = :apartmentID")
                    .setParameter("apartmentID", apartmentID).getResultList());
        }
    }
    
}
