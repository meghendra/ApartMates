package com.mycompany.jsfclasses;

/**
 *
 * @author megh
 * Utility for converting roommate objects into lists of strings
 * and back
 */
import com.mycompany.entityclasses.Roommate;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("roommateConverter")
public class RoommateConverter implements Converter {

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        Roommate r = new Roommate();
        if (value != null && value.trim().length() > 0) {
            value = value.replace("{", "");
            value = value.replace("Roommate", "");
            value = value.replace("}", "");
            String[] components = value.split(",");
            for (String c : components) {
                c = c.replace(" ", "");
                String[] tuple = c.split("=");
                
                switch (tuple[0]) {
                    case "roommateID":
                        r.setRoommateID(Integer.parseInt(tuple[1]));
                        break;
                    case "password":
                        r.setPassword(tuple[1]);
                        break;
                    case "firstName":
                        r.setFirstName(tuple[1]);
                        break;
                    case "lastName":
                        r.setLastName(tuple[1]);
                        break;
                    case "securityQuestion":
                        r.setSecurityQuestion(Integer.parseInt(tuple[1]));
                        break;
                    case "securityAnswer":
                        r.setSecurityAnswer(tuple[1]);
                        break;
                    case "apartmentID":
                        r.setApartmentID(Integer.parseInt(tuple[1]));
                        break;
                    case "points":
                        r.setPoints(Integer.parseInt(tuple[1]));
                        break;
                }
            }

        } else {
            return null;
        }
        return r;
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((Roommate) object).toString());
        } else {
            return null;
        }
    }
}
