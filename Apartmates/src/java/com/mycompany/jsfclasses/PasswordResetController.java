/*
 * Created by Andres Pico on 2016.12.10  * 
 * * 
 */
package com.mycompany.jsfclasses;

import com.mycompany.entityclasses.Roommate;
import com.mycompany.sessionbeans.RoommateFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

@Named(value = "PasswordResetController")
@SessionScoped
/**
 *
 * @author Group 2
 */
public class PasswordResetController implements Serializable {

    // Instance Variables (Properties)
    private String email;
    private String message = "";
    private String answer;
    private String password;


    @EJB
    private RoommateFacade roommateFacade;

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
    ================
    Instance Methods
    ================
     */
    // Process the submitted username
    public String usernameSubmit() {

        // Obtain the object reference of the Roommate object with username
        Roommate roommate = roommateFacade.findByEmail(email);

        if (roommate == null) {
            message = "Entered username does not exist!";

            // Redirect to show the EnterUsername page
            return "EnterUsername?faces-redirect=true";
        } else {
            // Entered username exists
            message = "";

            // Redirect to show the SecurityQuestion page
            return "SecurityQuestion?faces-redirect=true";
        }
    }

    // Process the submitted answer to the security question
    public String securityAnswerSubmit() {

        // Obtain the object reference of the Roommate object with username
        Roommate roommate = roommateFacade.findByEmail(email);

        String actualSecurityAnswer = roommate.getSecurityAnswer();
        String enteredSecurityAnswer = getAnswer();

        if (actualSecurityAnswer.equals(enteredSecurityAnswer)) {
            // Answer to the security question is correct
            message = "";

            // Redirect to show the ResetPassword page
            return "ResetPassword?faces-redirect=true";
        } else {
            // Answer to the security question is wrong
            message = "Security question answer is incorrect!";

            // Redirect to show the SecurityQuestion page
            return "SecurityQuestion?faces-redirect=true";
        }
    }

    // Return the security question selected by the Roommate object with username
    public String getSecurityQuestion() {

        // Obtain the object reference of the Roommate object with username
        Roommate roommate = roommateFacade.findByEmail(email);

        // Obtain the number of the security question selected by the Roommate
        int questionNumber = roommate.getSecurityQuestion();

        // Return the security question corresponding to the question number
        return Constants.QUESTIONS[questionNumber];
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
            message = "Password and Confirm Password must match!";
        } else {
            message = "";
        }
    }

    public String resetPassword() {

        if (message == null || message.isEmpty()) {

            // Obtain the object reference of the Roommate object with email
            Roommate roommate = roommateFacade.findByEmail(email);

            try {
                // Reset Roommate object's password
                roommate.setPassword(password);

                // Update the database
                roommateFacade.edit(roommate);

                // Initialize the instance variables
                email = message = answer = password = "";

            } catch (EJBException e) {
                message = "Something went wrong while resetting your password, please try again!";
                
                // Redirect to show the ResetPassword page
                return "ResetPassword?faces-redirect=true";
            }

            // Redirect to show the index (Home) page
            return "index?faces-redirect=true";
            
        } else {
            // Redirect to show the ResetPassword page
            return "ResetPassword?faces-redirect=true";
        }
    }

}