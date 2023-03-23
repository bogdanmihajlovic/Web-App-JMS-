/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bogdan
 */
@Entity
@Table(name = "recenzija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recenzija.findAll", query = "SELECT r FROM Recenzija r"),
    @NamedQuery(name = "Recenzija.findByIdKor", query = "SELECT r FROM Recenzija r WHERE r.recenzijaPK.idKor = :idKor"),
    @NamedQuery(name = "Recenzija.findByIdArtikal", query = "SELECT r FROM Recenzija r WHERE r.recenzijaPK.idArtikal = :idArtikal"),
    @NamedQuery(name = "Recenzija.findByOcena", query = "SELECT r FROM Recenzija r WHERE r.ocena = :ocena"),
    @NamedQuery(name = "Recenzija.findByOpis", query = "SELECT r FROM Recenzija r WHERE r.opis = :opis")})
public class Recenzija implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RecenzijaPK recenzijaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ocena")
    private double ocena;
    @Size(max = 45)
    @Column(name = "opis")
    private String opis;

    public Recenzija() {
    }

    public Recenzija(RecenzijaPK recenzijaPK) {
        this.recenzijaPK = recenzijaPK;
    }

    public Recenzija(RecenzijaPK recenzijaPK, double ocena) {
        this.recenzijaPK = recenzijaPK;
        this.ocena = ocena;
    }

    public Recenzija(int idKor, String idArtikal) {
        this.recenzijaPK = new RecenzijaPK(idKor, idArtikal);
    }

    public RecenzijaPK getRecenzijaPK() {
        return recenzijaPK;
    }

    public void setRecenzijaPK(RecenzijaPK recenzijaPK) {
        this.recenzijaPK = recenzijaPK;
    }

    public double getOcena() {
        return ocena;
    }

    public void setOcena(double ocena) {
        this.ocena = ocena;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recenzijaPK != null ? recenzijaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recenzija)) {
            return false;
        }
        Recenzija other = (Recenzija) object;
        if ((this.recenzijaPK == null && other.recenzijaPK != null) || (this.recenzijaPK != null && !this.recenzijaPK.equals(other.recenzijaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Recenzija[ recenzijaPK=" + recenzijaPK + " ]";
    }
    
}
