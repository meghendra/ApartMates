package com.mycompany.jsfclasses;

import com.mycompany.entityclasses.Roommate;
import com.mycompany.entityclasses.Task;
import com.mycompany.jsfclasses.util.JsfUtil;
import com.mycompany.sessionbeans.RoommateFacade;
import com.mycompany.sessionbeans.TaskFacade;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

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
TaskController. Actually, the default name is the class name starting
with a lower case letter and value = "TaskController" is optional;
However, we spell it out to make our code more readable and understandable.
---------------------------------------------------------------------------
 */
@Named(value = "taskController")

/*
The @SessionScoped annotation preserves the values of the TaskController
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
public class TaskController implements Serializable {

    private String statusMessage;
    private List<Task> tasks = null;
    private Task selected;

    private String taskID;
    private String taskName;
    private String taskDetails;
    private String taskLocation;
    private Date todayDate = new Date();
    private int lastHour = new Date(System.currentTimeMillis() - (60 * 60 * 1000)).getHours();
    private Date taskDeadline;
    private Date taskReminder1;
    private Date taskReminder2;
    private Date taskReminder3;
    private String taskPriority = "Medium";
    private String taskIsComplete = "No";
    private String apartmentID;
    private SimpleDateFormat standardDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /*
    The @EJB annotation implies that the EJB container will perform an injection of the object
    reference of the TaskFacade object into taskFacade when it is created at runtime.
     */
    @EJB
    private TaskFacade taskFacade;
    @EJB
    private RoommateFacade roommateFacade;

    public TaskController() {
    }

    //Task methods
    /*
    If 'items' list is empty, obtain the object references of all of the tasks
    in the database attached to the roommates apartment id, store them in the 'items" array (list), and return them.
     */
    public List<Task> getTasks() {
        if (tasks == null) {

            tasks = getTasksByApartmentID();
        }
        return tasks;
    }

    public List<Task> getTasksByApartmentID() {
        if (tasks == null) {
            // TaskFacade inherits the findAll() method from the AbstractFacade class
            if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("apartmentID") != null) {
                tasks = taskFacade.findByApartmentID((int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("apartmentID"));
            }
        }
        return tasks;
    }

    public Task prepareCreate() {

        // Instantiate a new Company object and store its object reference into instance variable 'selected'
        selected = new Task();

        initializeEmbeddableKey();  // Initialize embeddable primary key for the new object
        return selected;            // Return the object reference of the newly created Company object
    }

    /*
    Create a new task account. Return "" if an error occurs; otherwise,
    upon successful account creation, redirect to show the SignIn page.
     */
    public String createTask() {

        if (statusMessage == null || statusMessage.isEmpty()) {
            try {
                // Instantiate a new Task object
                Task task = new Task();

                // Dress up the newly created Task object with the values given
                task.setTaskName(taskName);
                task.setTaskDetails(taskDetails);
                task.setTaskLocation(taskLocation);
                if (taskDeadline != null) {
                    task.setTaskDeadline(taskDeadline);
                }
                if (taskReminder1 != null) {
                    task.setTaskReminder1(taskReminder1);
                }
                if (taskReminder2 != null) {
                    task.setTaskReminder2(taskReminder2);
                }
                if (taskReminder3 != null) {
                    task.setTaskReminder3(taskReminder3);
                }

                if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("apartmentID") != null) {
                    task.setApartmentID((int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("apartmentID"));
                }

                task.setTaskIsComplete("No");

                if (!taskPriority.isEmpty()) {
                    task.setTaskPriority(taskPriority);
                } else {
                    task.setTaskPriority("Medium");
                }

                taskFacade.create(task);
                if (!JsfUtil.isValidationFailed()) {
                    // The CREATE operation is successfully performed
                    tasks = null;    // Empty the items list
                }

            } catch (Exception e) {
                statusMessage = "Something went wrong while creating your task!";
                return "";
            }
        }
        return "";
    }

    /*
    Create a new Task account. Return "" if an error occurs; otherwise,
    upon successful account creation, redirect to show the SignIn page.
     */
    public String updateTask() {

        if (statusMessage == null || statusMessage.isEmpty()) {
            selected.setTaskIsComplete("No");
            taskFacade.edit(selected);
            if (!JsfUtil.isValidationFailed()) {
                // The CREATE operation is successfully performed
                tasks = null;    // Empty the items list
            }
        }
        return "";
    }

    /*
    Mark a Task as complete
     */
    public String markTask(Task t) {

        if (statusMessage == null || statusMessage.isEmpty()) {
            t.setTaskIsComplete("Yes");
            taskFacade.edit(t);
            Roommate r = roommateFacade.findById((int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roommateID"));
            Date today = new Date();
            int bonus = 0;
            int base = 10;
            if (t.getTaskDeadline().compareTo(today) > 0) {
                bonus = 10;
                base = 15;
            } else if (t.getTaskDeadline().compareTo(today) < 0) {
                bonus = 0;
                base = 5;
            } else if (t.getTaskDeadline().compareTo(today) == 0) {
                bonus = 5;
                base = 10;
            }
            switch (t.getTaskPriority()) {
                case "Low":
                    r.setPoints(r.getPoints() + (base + (int) (Math.random() * (bonus))));
                    break;
                case "Medium":
                    r.setPoints(r.getPoints() + ((base * 2) + (int) (Math.random() * (bonus))));
                    break;
                case "High":
                    r.setPoints(r.getPoints() + (((int) Math.round(base * 2.5)) + (int) (Math.random() * (bonus))));
                    break;
            }
            roommateFacade.edit(r);
            FacesContext.getCurrentInstance().getExternalContext().
                    getSessionMap().put("points", r.getPoints());
            if (!JsfUtil.isValidationFailed()) {
                // The CREATE operation is successfully performed
                tasks = null;    // Empty the items list
            }
        }
        return "";
    }

    public void deleteTask() {
        if (statusMessage == null || statusMessage.isEmpty()) {
            taskFacade.remove(selected);
            if (!JsfUtil.isValidationFailed()) {
                // The DELETE operation is successfully performed
                selected = null;    // Set the instance variable 'selected' point to no object
                tasks = null;       // Empty the items list
            }
        }
    }

    /*
    -----------------------
    Embeddable Primary Keys
    -----------------------
     */
    protected void setEmbeddableKeys() {
        // Nothing to do if entity does not have any embeddable key.
    }

    protected void initializeEmbeddableKey() {
        // Nothing to do if entity does not have any embeddable key.
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public String getTaskLocation() {
        return taskLocation;
    }

    public void setTaskLocation(String taskLocation) {
        this.taskLocation = taskLocation;
    }

    public Date getTodayDate() {
        return todayDate;
    }

    public int getLastHour() {
        return lastHour;
    }

    public Date getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(Date taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public Date getTaskReminder1() {
        return taskReminder1;
    }

    public void setTaskReminder1(Date taskReminder1) {
        this.taskReminder1 = taskReminder1;
    }

    public Date getTaskReminder2() {
        return taskReminder2;
    }

    public void setTaskReminder2(Date taskReminder2) {
        this.taskReminder2 = taskReminder2;
    }

    public Date getTaskReminder3() {
        return taskReminder3;
    }

    public void setTaskReminder3(Date taskReminder3) {
        this.taskReminder3 = taskReminder3;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskIsComplete() {
        return taskIsComplete;
    }

    public void setTaskIsComplete(String taskIsComplete) {
        this.taskIsComplete = taskIsComplete;
    }

    public String getApartmentID() {
        return apartmentID;
    }

    public void setApartmentID(String apartmentID) {
        this.apartmentID = apartmentID;
    }

    public Task getSelected() {
        return selected;
    }

    public void setSelected(Task selected) {
        this.selected = selected;
    }

}
