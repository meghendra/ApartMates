package com.mycompany.jsfclasses;

import com.mycompany.entityclasses.Photo;
import com.mycompany.entityclasses.Roommate;
import com.mycompany.sessionbeans.PhotoFacade;
import com.mycompany.sessionbeans.RoommateFacade;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
@Named(value = "RoommateController")

/*
The @SessionScoped annotation preserves the values of the AccountManager
object's instance variables across multiple HTTP request-response cycles
as long as the user's established HTTP session is alive.
 */
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
public class RoommateController implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    private String firstName;
    private String lastName;
    private String email;
    private String loginEmail;
    private String invitedRoommateEmail;
    private String password;
    private String newPassword;
    private String loginPassword;
    private int securityQuestion;
    private String securityAnswer;
    private Integer apartmentID;
    private Integer points;

    private Map<String, Object> security_questions;
    private String statusMessage;

    private Roommate selectedRoommate;
    private List<Roommate> apartmentRoommates;
    private List<Roommate> apartmentRoommatesButMe;

    /*
    The @EJB annotation implies that the EJB container will perform an injection of the object
    reference of the RoommateFacade object into roommateFacade when it is created at runtime.
     */
    @EJB
    private RoommateFacade roommateFacade;

    /*
    The @EJB annotation implies that the EJB container will perform an injection of the object
    reference of the PhotoFacade object into photoFacade when it is created at runtime.
     */
    @EJB
    private PhotoFacade photoFacade;

    // Constructor method instantiating an instance of ApartMatesController
    public RoommateController() {
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInvitedRoommateEmail() {
        return invitedRoommateEmail;
    }

    public void setInvitedRoommateEmail(String invitedRoommateEmail) {
        this.invitedRoommateEmail = invitedRoommateEmail;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public int getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(int securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Integer getApartmentID() {
        return apartmentID;
    }

    public void setApartmentID(Integer apartmentID) {
        this.apartmentID = apartmentID;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public List<Roommate> getApartmentRoommatesButMe() {
        return apartmentRoommatesButMe;
    }

    public void setApartmentRoommatesButMe(List<Roommate> apartmentRoommatesButMe) {
        this.apartmentRoommatesButMe = apartmentRoommatesButMe;
    }


    /*
    private Map<String, Object> security_questions;
        String      int
        ---------   ---
        question1,  0
        question2,  1
        question3,  2
            :
    When the user selects a security question, its number (int) is stored; not its String.
    Later, given the number (int), the security question String is retrieved.
     */
    public Map<String, Object> getSecurity_questions() {

        if (security_questions == null) {
            /*
            Difference between HashMap and LinkedHashMap:
            HashMap stores key-value pairings in no particular order. Values are retrieved based on their corresponding Keys.
            LinkedHashMap stores and retrieves key-value pairings in the order they were put into the map.
             */
            security_questions = new LinkedHashMap<>();

            for (int i = 0; i < Constants.QUESTIONS.length; i++) {
                security_questions.put(Constants.QUESTIONS[i], i);
            }
        }
        return security_questions;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Roommate getSelectedRoommate() {
        return selectedRoommate;
    }

    public void setSelectedRoommate(Roommate selectedRoommate) {
        this.selectedRoommate = selectedRoommate;
    }

    public List<Roommate> getApartmentRoommates() {
        return apartmentRoommates;
    }

    public void setApartmentRoommates(List<Roommate> apartmentRoommates) {
        this.apartmentRoommates = apartmentRoommates;
    }

    // EL in Profile.xhtml invokes this method to obtain the constant value
    public String photoStorageDirectoryName() {
        return Constants.STORAGE_DIRECTORY;
    }

    public String roommatePhoto() {

        // Obtain the username of the logged-in roommate
        String userEmail = (String) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("roommateEmail");

        // Obtain the object reference of the logged-in Roommate object
        Roommate r = roommateFacade.findByEmail(userEmail);

        /*
        Roommate photo files are not stored in the database. Only the primary key (id) of the
        Roommate's photo is stored in the database.
        
        When Roommate uploads a photo, a thumbnail (small) version of the file is created
        in the saveThumbnail() method of FileManager by using the Scalr.resize method provided
        in the imgscalr (Java Image Scaling Library) imported as imgscalr-lib-4.2.jar

        Both uploaded and thumbnail photo files are named after the primary key (id) of the
        Roommate's photo and are stored in the PizzaHutStorageLocation. For example,
        for the primary key (id) = 25 and file extension = jpeg, the files are named as:
            e.g., 25.jpeg
            e.g., 25_thumbnail.jpeg
         */
        // Obtain a list of photo files (e.g., 25.jpeg and 25_thumbnail.jpeg) associated
        // with the logged-in Roommate whose database primary key is roommate.getId()
        List<Photo> photoList = photoFacade.findPhotosByRoommateID(r.getRoommateID());

        if (photoList.isEmpty()) {
            // No Roommate photo exists. Return the default Roommate photo image.
            return "defaultPhoto.png";
        }

        /*
        photoList.get(0) returns the object reference of the first Photo object in the list.
        getThumbnailName() message is sent to that Photo object to retrieve its
        thumbnail image file name, e.g., 25_thumbnail.jpeg
         */
        return photoList.get(0).getThumbnailName();
    }

    public String roommatePhoto(Roommate r) {

        /*
        Roommate photo files are not stored in the database. Only the primary key (id) of the
        Roommate's photo is stored in the database.
        
        When Roommate uploads a photo, a thumbnail (small) version of the file is created
        in the saveThumbnail() method of FileManager by using the Scalr.resize method provided
        in the imgscalr (Java Image Scaling Library) imported as imgscalr-lib-4.2.jar

        Both uploaded and thumbnail photo files are named after the primary key (id) of the
        Roommate's photo and are stored in the PizzaHutStorageLocation. For example,
        for the primary key (id) = 25 and file extension = jpeg, the files are named as:
            e.g., 25.jpeg
            e.g., 25_thumbnail.jpeg
         */
        // Obtain a list of photo files (e.g., 25.jpeg and 25_thumbnail.jpeg) associated
        // with the logged-in Roommate whose database primary key is roommate.getId()
        List<Photo> photoList = photoFacade.findPhotosByRoommateID(r.getRoommateID());

        if (photoList.isEmpty()) {
            // No Roommate photo exists. Return the default Roommate photo image.
            return "defaultPhoto.png";
        }

        /*
        photoList.get(0) returns the object reference of the first Photo object in the list.
        getThumbnailName() message is sent to that Photo object to retrieve its
        thumbnail image file name, e.g., 25_thumbnail.jpeg
         */
        return photoList.get(0).getThumbnailName();
    }

    //Roommate methods

    /*
    Create a new Roommate account. Return "" if an error occurs; otherwise,
    upon successful account creation, redirect to show the SignIn page.
     */
    public String createRoommate() {

        // Obtain the object reference of the Roommate object with username
        Roommate aRoommate = roommateFacade.findByEmail(email);
        statusMessage = "";

        if (aRoommate != null) {
            // A Roommate already exists with the username given
            email = "";
            statusMessage = "Email already registered!";
            return "";
        }

        try {
            // Instantiate a new Roommate object
            Roommate roommate = new Roommate();

            // Dress up the newly created Roommate object with the values given
            roommate.setFirstName(firstName);
            roommate.setLastName(lastName);
            roommate.setEmail(email);
            roommate.setSecurityQuestion(securityQuestion);
            roommate.setSecurityAnswer(securityAnswer);
            roommate.setPassword(password);
            roommate.setPoints(0);
            roommateFacade.create(roommate);

            selectedRoommate = roommate;
            initializeRoommateSessionMap(roommate);

        } catch (EJBException e) {
            email = "";
            statusMessage = "Something went wrong while creating your account!";
            return "";
        }
        // Initialize the session map for the newly created Roommate object
        initializeRoommateSessionMap();

        /*
        The Profile page cannot be shown since the Roommate has not signed in yet.
        Therefore, we show the Sign In page for the Roommate to sign in first.
         */
        return showDashboard();
    }

    /*
    Sign in the Roommate if the entered username and password are valid
    @return "" if an error occurs; otherwise, redirect to show the Profile page
     */
    public String loginRoommate() {

        // Obtain the object reference of the Roommate object from the entered username
        Roommate roommate = roommateFacade.findByEmail(loginEmail);
        statusMessage = "";
        if (roommate == null) {
            statusMessage = "Entered email " + loginEmail + " does not exist!";
            return "";

        } else {

            String actualEmail = roommate.getEmail();

            String actualPassword = roommate.getPassword();

            if (!actualEmail.equals(loginEmail)) {
                statusMessage = "Invalid Email!";
                return "";
            }

            if (!actualPassword.equals(loginPassword)) {
                statusMessage = "Invalid Password!";
                return "";
            }

            statusMessage = "";

            // Retrieve values from Roommate
            firstName = roommate.getFirstName();
            lastName = roommate.getLastName();
            email = roommate.getEmail();
            password = roommate.getPassword();
            securityQuestion = roommate.getSecurityQuestion();
            securityAnswer = roommate.getSecurityAnswer();
            apartmentID = roommate.getApartmentID();
            points = roommate.getPoints();
            selectedRoommate = roommate;

            if (apartmentID != null) {
                apartmentRoommates = roommateFacade.findApartmentRoommates(apartmentID);

                if (apartmentRoommates.size() > 1) {
                    apartmentRoommatesButMe = apartmentRoommates;
                    apartmentRoommatesButMe.remove(roommate);
                }
            }
            // Initialize the session map with Roommate properties of interest
            initializeRoommateSessionMap(roommate);

            // Redirect to show the Profile page
            return showDashboard();
        }
    }

    public String resetPassword() {
        // Redirect to show the EnterUsername page
        return "EnterUsername.xhtml?faces-redirect=true";
    }

    /*
    Update the logged-in Roommate's account profile. Return "" if an error occurs;
    otherwise, upon successful account update, redirect to show the Profile page.
     */
    public String updateRoommate() {

        if (statusMessage == null || statusMessage.isEmpty()) {

            // Obtain the username of the logged-in Roommate
            String roommateEmail = (String) FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().get("roommateEmail");

            // Obtain the object reference of the logged-in Roommate object
            Roommate editRoommate = roommateFacade.findByEmail(roommateEmail);

            try {
                // Set the logged-in Roommate's properties to the given values
                editRoommate.setFirstName(this.selectedRoommate.getFirstName());
                editRoommate.setLastName(this.selectedRoommate.getLastName());
                editRoommate.setEmail(this.selectedRoommate.getEmail());

                // It is optional for the Roommate to change his/her password
                String new_Password = getNewPassword();

                if (new_Password == null || new_Password.isEmpty()) {
                    // Do nothing. The user does not want to change the password.
                } else {
                    editRoommate.setPassword(new_Password);
                    // Password changed successfully!
                    // Password was first validated by invoking the validatePasswordChange method below.
                }

                // The changes are stored in the database
                roommateFacade.edit(editRoommate);
                // Initialize the session map with roommate properties of interest
                initializeRoommateSessionMap(editRoommate);

            } catch (EJBException e) {
                email = "";
                statusMessage = "Something went wrong while editing your profile!";
                return "";
            }
            // Account update is completed, redirect to show the Profile page.
            return showProfile();
        }
        return "";
    }

    /*
    Delete the logged-in roommate's account. Return "" if an error occurs; otherwise,
    upon successful account deletion, redirect to show the index (home) page.
     */
    public String deleteProfile() {

        if (statusMessage == null || statusMessage.isEmpty()) {

            // Obtain the database primary key of the logged-in roommate object
            int roommateID = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roommateID");

            try {
                deletePhoto(roommateID);
                // Delete the roommate entity, whose primary key is roommate_id, from the database
                roommateFacade.deleteRoommate(roommateID);

            } catch (EJBException e) {
                roommateID = -1;
                statusMessage = "Something went wrong while deleting your account!";
                return "";
            }

            logout();
            return showIndexPage();
        }
        return "";
    }

    /*
    Delete both uploaded and thumbnail photo files that belong to the roommate
    object whose database primary key is roommateId
     */
    public void deletePhoto(int roommateId) {

        /*
        Obtain the list of Photo objects that belong to the roommate whose
        database primary key is roommateId.
         */
        List<Photo> photoList = photoFacade.findPhotosByRoommateID(roommateId);

        if (!photoList.isEmpty()) {

            // Obtain the object reference of the first Photo object in the list.
            Photo photo = photoList.get(0);
            try {
                // Delete the uploaded photo file if it exists
                Files.deleteIfExists(Paths.get(photo.getFilePath()));

                // Delete the thumbnail image file if it exists
                Files.deleteIfExists(Paths.get(photo.getThumbnailFilePath()));

                // Delete the temporary file if it exists
                Files.deleteIfExists(Paths.get(Constants.ROOT_DIRECTORY + "tmp_file"));

                // Remove the roommate photo's record from the database
                photoFacade.remove(photo);

            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Return True if a roommate is logged in; otherwise, return False
    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roommateEmail") != null;
    }

    // Return True if a roommate is logged in; otherwise, return False
    public boolean hasAnApartment() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("apartmentID") != null;
    }

    // Validate if the entered password matches the entered confirm password
    public void validateInformation(ComponentSystemEvent event) {

        /*
        FacesContext contains all of the per-request state information related to the processing of
        a single JavaServer Faces request, and the rendering of the corresponding response.
        It is passed to, and potentially modified by, each phase of the request processing lifecycle.
         */
        FacesContext fc = FacesContext.getCurrentInstance();

        /*
        UIComponent is the base class for all user interface components in JavaServer Faces. 
        The set of UIComponent instances associated with a particular request and response are organized into
        a component tree under a UIViewRoot that represents the entire content of the request or response.
         */
        // Obtain the UIComponent instances associated with the event
        UIComponent components = event.getComponent();

        /*
        UIInput is a kind of UIComponent for the user to enter a value in.
         */
        // Obtain the object reference of the UIInput field with id="password" on the UI
        UIInput uiInputPassword = (UIInput) components.findComponent("password");

        // Obtain the password entered in the UIInput field with id="password" on the UI
        String entered_password = uiInputPassword.getLocalValue()
                == null ? "" : uiInputPassword.getLocalValue().toString();

        // Obtain the object reference of the UIInput field with id="confirmPassword" on the UI
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");

        // Obtain the confirm password entered in the UIInput field with id="confirmPassword" on the UI
        String entered_confirm_password = uiInputConfirmPassword.getLocalValue()
                == null ? "" : uiInputConfirmPassword.getLocalValue().toString();

        if (entered_password.isEmpty() || entered_confirm_password.isEmpty()) {
            // Do not take any action. 
            // The required="true" in the XHTML file will catch this and produce an error message.
            return;
        }

        if (!entered_password.equals(entered_confirm_password)) {
            statusMessage = "Password and Confirm Password must match!";
        } else {
            statusMessage = "";
        }
    }

    // Validate the new password and new confirm password
    public void validatePasswordChange(ComponentSystemEvent event) {
        /*
        FacesContext contains all of the per-request state information related to the processing of
        a single JavaServer Faces request, and the rendering of the corresponding response.
        It is passed to, and potentially modified by, each phase of the request processing lifecycle.
         */
        FacesContext fc = FacesContext.getCurrentInstance();

        /*
        UIComponent is the base class for all user interface components in JavaServer Faces. 
        The set of UIComponent instances associated with a particular request and response are organized into
        a component tree under a UIViewRoot that represents the entire content of the request or response.
         */
        // Obtain the UIComponent instances associated with the event
        UIComponent components = event.getComponent();

        /*
        UIInput is a kind of UIComponent for the user to enter a value in.
         */
        // Obtain the object reference of the UIInput field with id="newPassword" on the UI
        UIInput uiInputPassword = (UIInput) components.findComponent("newPassword");

        // Obtain the new password entered in the UIInput field with id="newPassword" on the UI
        String new_Password = uiInputPassword.getLocalValue()
                == null ? "" : uiInputPassword.getLocalValue().toString();

        // Obtain the object reference of the UIInput field with id="newConfirmPassword" on the UI
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("newConfirmPassword");

        // Obtain the new confirm password entered in the UIInput field with id="newConfirmPassword" on the UI
        String new_ConfirmPassword = uiInputConfirmPassword.getLocalValue()
                == null ? "" : uiInputConfirmPassword.getLocalValue().toString();

        // It is optional for the roommate to change his/her password
        if (new_Password.isEmpty() || new_ConfirmPassword.isEmpty()) {
            // Do nothing. The user does not want to change the password.
            return;
        }

        if (!new_Password.equals(new_ConfirmPassword)) {
            statusMessage = "New Password and New Confirm Password must match!";
        } else {
            /*
            REGular EXpression (regex) for validating password strength:
            (?=.{8,31})      ==> Validate the password to be minimum 8 and maximum 31 characters long. 
            (?=.*[!@#$%^&*]) ==> Validate the password to contain at least one special character. 
            (?=.*[A-Z])      ==> Validate the password to contain at least one uppercase letter. 
            (?=.*[a-z])      ==> Validate the password to contain at least one lowercase letter. 
            (?=.*[0-9])      ==> Validate the password to contain at least one number from 0 to 9.
             */
            String regex = "^(?=.{8,31})(?=.*[!@#$%^&*])(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$";

            if (!new_Password.matches(regex)) {
                statusMessage = "The password must be minimum 8 "
                        + "characters long, contain at least one special character, "
                        + "contain at least one uppercase letter, "
                        + "contain at least one lowercase letter, "
                        + "and contain at least one number 0 to 9.";
            } else {
                statusMessage = "";
            }
        }
    }

    // Validate if the entered password and confirm password are correct
    public void validateRoommatePassword(ComponentSystemEvent event) {
        /*
        FacesContext contains all of the per-request state information related to the processing of
        a single JavaServer Faces request, and the rendering of the corresponding response.
        It is passed to, and potentially modified by, each phase of the request processing lifecycle.
         */
        FacesContext fc = FacesContext.getCurrentInstance();

        /*
        UIComponent is the base class for all user interface components in JavaServer Faces. 
        The set of UIComponent instances associated with a particular request and response are organized into
        a component tree under a UIViewRoot that represents the entire content of the request or response.
         */
        // Obtain the UIComponent instances associated with the event
        UIComponent components = event.getComponent();

        /*
        UIInput is a kind of UIComponent for the user to enter a value in.
         */
        // Obtain the object reference of the UIInput field with id="password" on the UI
        UIInput uiInputPassword = (UIInput) components.findComponent("password");

        // Obtain the password entered in the UIInput field with id="password" on the UI
        String entered_password = uiInputPassword.getLocalValue()
                == null ? "" : uiInputPassword.getLocalValue().toString();

        // Obtain the object reference of the UIInput field with id="confirmPassword" on the UI
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");

        // Obtain the confirm password entered in the UIInput field with id="confirmPassword" on the UI
        String entered_confirm_password = uiInputConfirmPassword.getLocalValue()
                == null ? "" : uiInputConfirmPassword.getLocalValue().toString();

        if (entered_password.isEmpty() || entered_confirm_password.isEmpty()) {
            // Do not take any action. 
            // The required="true" in the XHTML file will catch this and produce an error message.
            return;
        }

        if (!entered_password.equals(entered_confirm_password)) {
            statusMessage = "Password and Confirm Password must match!";
        } else {
            // Obtain the logged-in roommate's username
            String email = (String) FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().get("roommateEmail");

            // Obtain the object reference of the logged-in roommate object
            Roommate roommate = roommateFacade.findByEmail(email);

            if (entered_password.equals(roommate.getPassword())) {
                // entered password = logged-in roommate's password
                statusMessage = "";
            } else {
                statusMessage = "Incorrect Password!";
            }
        }
    }

    // Initialize the session map for the Roommate object
    public void initializeRoommateSessionMap() {

        // Obtain the object reference of the roommate object
        Roommate roommate = roommateFacade.findByEmail(getEmail());

        // Put the roommate's object reference into session map variable roommate
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("roommate", roommate);

        // Put the roommate's database primary key into session map variable roommateID
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("roommateID", roommate.getRoommateID());

        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("roommateEmail", roommate.getEmail());
    }

    // Initialize the session map for the roommate object
    public void initializeRoommateSessionMap(Roommate roommate) {

        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("roommate", roommate);

        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("roommateID", roommate.getRoommateID());

        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("roommateFirstName", roommate.getFirstName());

        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("roommateLastName", roommate.getLastName());

        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("roommateEmail", roommate.getEmail());

        if (roommate.getApartmentID() != null) {
            FacesContext.getCurrentInstance().getExternalContext().
                    getSessionMap().put("apartmentID", roommate.getApartmentID());
        }

        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("points", roommate.getPoints());

        if (apartmentRoommatesButMe != null) {
            int totalPoints = 0;
            for (Roommate r : apartmentRoommatesButMe) {
                totalPoints = totalPoints + r.getPoints();
            }
            FacesContext.getCurrentInstance().getExternalContext().
                    getSessionMap().put("totalPoints", totalPoints);
        } else {
            FacesContext.getCurrentInstance().getExternalContext().
                    getSessionMap().put("totalPoints", 0);
        }
    }

    /*
    UIComponent is the base class for all user interface components in JavaServer Faces. 
    The set of UIComponent instances associated with a particular request and response are organized into
    a component tree under a UIViewRoot that represents the entire content of the request or response.

    @param components: UIComponent instances associated with the current request and response
    @return True if entered password is correct; otherwise, return False
     */
    private boolean correctPasswordEntered(UIComponent components) {

        // Obtain the object reference of the UIInput field with id="verifyPassword" on the UI
        UIInput uiInputVerifyPassword = (UIInput) components.findComponent("verifyPassword");

        // Obtain the verify password entered in the UIInput field with id="verifyPassword" on the UI
        String verifyPassword = uiInputVerifyPassword.getLocalValue()
                == null ? "" : uiInputVerifyPassword.getLocalValue().toString();

        if (verifyPassword.isEmpty()) {
            statusMessage = "Please enter a password!";
            return false;

        } else if (verifyPassword.equals(password)) {
            // Correct password is entered
            return true;

        } else {
            statusMessage = "Invalid password entered!";
            return false;
        }
    }

    /*
    Delete the logged-in roommate's account. Return "" if an error occurs; otherwise,
    upon successful account deletion, redirect to show the index (home) page.
     */
    public String inviteRoommate() {

        statusMessage = "";

        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roommateEmail") == invitedRoommateEmail) {
            statusMessage = "Cannot invite yourself!";
            return "";
        }

        // Obtain the database primary key of the logged-in roommate object
        String email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roommateEmail");
        apartmentID = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("apartmentID");

        try {

            statusMessage = roommateFacade.inviteToApartment(invitedRoommateEmail, apartmentID);
            invitedRoommateEmail = null;

            if (statusMessage.equals("Roommate invited successfully")) {
                apartmentRoommates = roommateFacade.findApartmentRoommates(apartmentID);
                return "ViewApartment.xhtml?faces-redirect=true";
            }
            return "";

        } catch (EJBException e) {
            statusMessage = "Something went wrong while inviting the roommate!";
            return "";
        }
    }

    public String logout() {

        // Clear the logged-in roommate's session map
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();

        // Reset the logged-in roommate's properties
        email = password = "";
        firstName = lastName = "";
        securityQuestion = 0;
        securityAnswer = "";
        statusMessage = "";

        // Invalidate the logged-in roommate's session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        // Redirect to show the index (Home) page
        return showIndexPage();
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
