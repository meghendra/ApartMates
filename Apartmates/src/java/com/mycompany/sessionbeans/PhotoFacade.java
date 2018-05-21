package com.mycompany.sessionbeans;

import com.mycompany.entityclasses.Photo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author megh
 */
@Stateless
public class PhotoFacade extends AbstractFacade<Photo> {

    @PersistenceContext(unitName = "ApartMates-Group2")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PhotoFacade() {
        super(Photo.class);
    }
    
    public List<Photo> findPhotosByRoommateID(Integer roommateID) {

        return (List<Photo>) em.createNamedQuery("Photo.findPhotosByRoommateID")
                .setParameter("roommateID", roommateID)
                .getResultList();
    }
    
}
