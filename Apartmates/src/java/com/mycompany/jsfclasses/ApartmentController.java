package com.mycompany.jsfclasses;

import com.mycompany.entityclasses.Apartment;
import com.mycompany.entityclasses.Roommate;
import com.mycompany.sessionbeans.ApartmentFacade;
import com.mycompany.sessionbeans.RoommateFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Andres
 */
/*
---------------------------------------------------------------------------
JSF Managed Beans annotated with @ManagedBean from javax.faces.bean
is in the process of being deprecated in favor of CDI Beans. Therefore, 
you should use @Named from javax.inject package to designate a managed
bean as a JSF controller class. Contexts and Dependency Injection (CDI) 
beans annotated with @Named is the preferred approach, because CDI 
enables Java EE-wide dependency injection. CDI bean is a bean managed
by the CDI container. 

Within JSF XHTML pages, this bean will be referenced by using the name
accountManager. Actually, the default name is the class name starting
with a lower case letter and value = "accountManager" is optional;
However, we spell it out to make our code more readable and understandable.
---------------------------------------------------------------------------
 */
@Named(value = "ApartmentController")
@SessionScoped

/**
 *
 * @author Group 2
 */

/*
--------------------------------------------------------------------------
Marking the AccountManager class as "implements Serializable" implies that
instances of the class can be automatically serialized and deserialized. 

Serialization is the process of converting a class instance (object)
from memory into a suitable format for storage in a file or memory buffer, 
or for transmission across a network connection link.

Deserialization is the process of recreating a class instance (object)
in memory from the format under which it was stored.
--------------------------------------------------------------------------
 */
public class ApartmentController implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    private Integer apartmentID;
    private String apartmentName;
    private String apartmentAddress;
    private Apartment selectedApartment;
    private String statusMessage;
    private String email;

    @EJB
    private ApartmentFacade apartmentFacade;

    @EJB
    private RoommateFacade roommateFacade;

    // Constructor method instantiating an instance of ApartMatesController
    public ApartmentController() {
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public Integer getApartmentID() {
        return apartmentID;
    }

    public void setApartmentID(Integer apartmentID) {
        this.apartmentID = apartmentID;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getApartmentAddress() {
        return apartmentAddress;
    }

    public void setApartmentAddress(String apartmentAddress) {
        this.apartmentAddress = apartmentAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Apartment getSelectedApartment() {
        return selectedApartment;
    }

    public void setSelectedApartment(Apartment selectedApartment) {
        this.selectedApartment = selectedApartment;
    }

    //Apartment methods
    public String createApartment() {

        statusMessage = "";

        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("apartmentID") != null) {
            statusMessage = "Apartment already exists!";
            return "";
        }

        try {
            // Instantiate a new Apartment object
            Apartment apartment = new Apartment();

            // Dress up the newly created Apartment object with the values given
            apartment.setApartmentName(apartmentName);
            apartment.setApartmentAddress(apartmentAddress);
            apartmentID = apartmentFacade.ownCreate(apartment);
            selectedApartment = apartment;

            email = (String) FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().get("roommateEmail");

            Roommate roommate = roommateFacade.findByEmail(email);
            roommate.setApartmentID(apartmentID);
            FacesContext.getCurrentInstance().getExternalContext().
                    getSessionMap().put("apartmentID", roommate.getApartmentID());
            roommateFacade.edit(roommate);

        } catch (EJBException e) {
            statusMessage = "Something went wrong while creating your account!";
            return "";
        }
        // Initialize the session map for the newly created Apartment object
        initializeApartmentSessionMap();

        return "Dashboard.xhtml?faces-redirect=true";
    }

    public String updateApartment() {

        statusMessage = "";

        apartmentID = (int) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("apartmentID");

        Apartment apartment = apartmentFacade.find(apartmentID);

        try {

            apartment.setApartmentName(this.selectedApartment.getApartmentName());
            apartment.setApartmentAddress(this.selectedApartment.getApartmentAddress());

            // The changes are stored in the database
            apartmentFacade.edit(apartment);

            apartmentName = selectedApartment.getApartmentName();
            apartmentAddress = selectedApartment.getApartmentAddress();
            selectedApartment = apartment;

        } catch (EJBException e) {
            statusMessage = "Something went wrong while editing your apartment!";
            return "";
        }
        // Account update is completed, redirect to show the Profile page.
        return showViewApartment();
    }

    public String loadApartment() {

        statusMessage = "";

        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("apartmentID") == null) {
            statusMessage = "No Apartment Exists!";
            return "";
        }

        apartmentID = (int) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("apartmentID");

        Apartment apartment = apartmentFacade.find(apartmentID);

        try {

            apartmentName = apartment.getApartmentName();
            apartmentAddress = apartment.getApartmentAddress();
            selectedApartment = apartment;

        } catch (EJBException e) {
            statusMessage = "Something went wrong while loading your apartment!";
            return "";
        }

        // Account update is completed, redirect to show the Profile page.
        return "ViewApartment.xhtml?faces-redirect=true";
    }

    public void leaveApartment() {

        statusMessage = "";

        String email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roommateEmail");
        apartmentID = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("apartmentID");

        try {
            boolean leftApartment = roommateFacade.leaveApartment(email, apartmentID);

            if (leftApartment) {
                apartmentID = null;
                selectedApartment.setApartmentID(null);
                FacesContext.getCurrentInstance().getExternalContext().
                        getSessionMap().put("apartmentID", null);

                return;
            } else {
                statusMessage = "Apartment cannot be left alone!";
                return;
            }

        } catch (EJBException e) {
            statusMessage = "Something went wrong while leaving the apartment!";
            return;
        }
    }

    
    public String deleteApartment() {

        statusMessage = "";

        apartmentID = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("apartmentID");

        try {

            roommateFacade.removeApartmentIDs(apartmentID);
            apartmentFacade.deleteApartment(apartmentID);

        } catch (EJBException e) {
            statusMessage = "Something went wrong while deleting your apartment!";
            return "";
        }

        apartmentID = null;
        apartmentName = null;
        apartmentAddress = null;
        selectedApartment = null;
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("apartmentID", null);

        return "Dashboard.xhtml?faces-redirect=true";
    }

    // Initialize the session map for the Apartment object
    public void initializeApartmentSessionMap() {

        // Obtain the object reference of the Apartment object
        Apartment apartment = apartmentFacade.getApartmentFacade(apartmentID);

        // Put the Apartment's object reference into session map variable Apartment
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("apartment", apartment);

        // Put the Apartment's database primary key into session map variable apartmentID
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("apartmentID", apartment.getApartmentID());

    }

    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roommateEmail") != null;
    }

    //Navigation methods
    public String homePageDestination() {

        statusMessage = null;
        if (isLoggedIn()) {
            return showDashboard();
        } else {
            return showIndexPage();
        }
    }

    // Show the Index page
    public String showIndexPage() {

        statusMessage = null;
        return "index?faces-redirect=true";
    }

    // Show the Dashboard page
    public String showDashboard() {

        statusMessage = null;
        if (isLoggedIn()) {
            return "Dashboard?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }

    // Show the Create Apartment page
    public String showProfile() {

        statusMessage = null;
        if (isLoggedIn()) {
            return "Profile?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }

// Show the Create Apartment page
    public String showEditProfile() {

        statusMessage = null;
        if (isLoggedIn()) {
            return "EditProfile?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }

    // Show the Create Apartment page
    public String showCreateApartment() {

        statusMessage = null;
        if (isLoggedIn()) {
            return "CreateApartment?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }

    // Show the Create Apartment page
    public String showViewApartment() {

        statusMessage = null;
        if (isLoggedIn()) {
            return "ViewApartment?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }

    // Show the Create Apartment page
    public String showEditApartment() {

        statusMessage = null;
        if (isLoggedIn()) {
            return "EditApartment?faces-redirect=true";
        } else {
            return showIndexPage();
        }
    }

}
