package com.mycompany.entityclasses;

import com.mycompany.jsfclasses.Constants;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author megh
 */
@Entity
@Table(name = "Photo")
@XmlRootElement
@NamedQueries({
    /* The following query is added */
    @NamedQuery(name = "Photo.findPhotosByRoommateID", query = "SELECT p FROM Photo p WHERE p.roommateId.roommateID = :roommateID"),

    @NamedQuery(name = "Photo.findAll", query = "SELECT p FROM Photo p"),
    @NamedQuery(name = "Photo.findById", query = "SELECT p FROM Photo p WHERE p.id = :id"),
    @NamedQuery(name = "Photo.findByExtension", query = "SELECT p FROM Photo p WHERE p.extension = :extension")})
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "extension")
    private String extension;

    @JoinColumn(name = "roommate_id", referencedColumnName = "roommate_ID")
    @ManyToOne
    private Roommate roommateId;

    public Photo() {
    }

    public Photo(Integer id) {
        this.id = id;
    }

    public Photo(Integer id, String extension) {
        this.id = id;
        this.extension = extension;
    }

    // This method is added to the generated code
    public Photo(String extension, Roommate id) {
        this.extension = extension;
        roommateId = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Roommate getRoommateId() {
        return roommateId;
    }

    public void setRoommateId(Roommate roommateId) {
        this.roommateId = roommateId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Photo)) {
            return false;
        }
        Photo other = (Photo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entityclasses.Photo[ id=" + id + " ]";
    }

    /*
    =====================================================
    The following methods are added to the generated code
    =====================================================
     */
    public String getFilePath() {
        return Constants.ROOT_DIRECTORY + getFilename();
    }

    // Roommate's photo image file is named as Roommate's unique id.file extension name.
    // Example: 38.jpeg
    public String getFilename() {
        return getId() + "." + getExtension();
    }

    public String getThumbnailFilePath() {
        return Constants.ROOT_DIRECTORY + getThumbnailName();
    }

    /*
    Roommate's photo thumbnail image file is named as 
    the primary key (id) of the Roommate's photo_thumbnail.file extension name.
    Example: 38_thumbnail.jpeg
     */
    public String getThumbnailName() {
        return getId() + "_thumbnail." + getExtension();
    }

}
