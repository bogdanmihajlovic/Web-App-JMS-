/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bogdan
 */
@Entity
@Table(name = "transakcija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transakcija.findAll", query = "SELECT t FROM Transakcija t"),
    @NamedQuery(name = "Transakcija.findByIdTranskacija", query = "SELECT t FROM Transakcija t WHERE t.idTranskacija = :idTranskacija"),
    @NamedQuery(name = "Transakcija.findByPlaceno", query = "SELECT t FROM Transakcija t WHERE t.placeno = :placeno"),
    @NamedQuery(name = "Transakcija.findByVreme", query = "SELECT t FROM Transakcija t WHERE t.vreme = :vreme")})
public class Transakcija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTranskacija")
    private Integer idTranskacija;
    @Basic(optional = false)
    @NotNull
    @Column(name = "placeno")
    private double placeno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vreme;

    public Transakcija() {
    }

    public Transakcija(Integer idTranskacija) {
        this.idTranskacija = idTranskacija;
    }

    public Transakcija(Integer idTranskacija, double placeno, Date vreme) {
        this.idTranskacija = idTranskacija;
        this.placeno = placeno;
        this.vreme = vreme;
    }

    public Integer getIdTranskacija() {
        return idTranskacija;
    }

    public void setIdTranskacija(Integer idTranskacija) {
        this.idTranskacija = idTranskacija;
    }

    public double getPlaceno() {
        return placeno;
    }

    public void setPlaceno(double placeno) {
        this.placeno = placeno;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTranskacija != null ? idTranskacija.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transakcija)) {
            return false;
        }
        Transakcija other = (Transakcija) object;
        if ((this.idTranskacija == null && other.idTranskacija != null) || (this.idTranskacija != null && !this.idTranskacija.equals(other.idTranskacija))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Transakcija[ idTranskacija=" + idTranskacija + " ]";
    }
    
}
