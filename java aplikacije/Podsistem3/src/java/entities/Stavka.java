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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bogdan
 */
@Entity
@Table(name = "stavka")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stavka.findAll", query = "SELECT s FROM Stavka s"),
    @NamedQuery(name = "Stavka.findByIdNarudzbine", query = "SELECT s FROM Stavka s WHERE s.stavkaPK.idNarudzbine = :idNarudzbine"),
    @NamedQuery(name = "Stavka.findByIdA", query = "SELECT s FROM Stavka s WHERE s.stavkaPK.idA = :idA"),
    @NamedQuery(name = "Stavka.findByKolicina", query = "SELECT s FROM Stavka s WHERE s.kolicina = :kolicina"),
    @NamedQuery(name = "Stavka.findByCenaKomad", query = "SELECT s FROM Stavka s WHERE s.cenaKomad = :cenaKomad")})
public class Stavka implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StavkaPK stavkaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicina")
    private int kolicina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cenaKomad")
    private double cenaKomad;

    public Stavka() {
    }

    public Stavka(StavkaPK stavkaPK) {
        this.stavkaPK = stavkaPK;
    }

    public Stavka(StavkaPK stavkaPK, int kolicina, double cenaKomad) {
        this.stavkaPK = stavkaPK;
        this.kolicina = kolicina;
        this.cenaKomad = cenaKomad;
    }

    public Stavka(int idNarudzbine, int idA) {
        this.stavkaPK = new StavkaPK(idNarudzbine, idA);
    }

    public StavkaPK getStavkaPK() {
        return stavkaPK;
    }

    public void setStavkaPK(StavkaPK stavkaPK) {
        this.stavkaPK = stavkaPK;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getCenaKomad() {
        return cenaKomad;
    }

    public void setCenaKomad(double cenaKomad) {
        this.cenaKomad = cenaKomad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stavkaPK != null ? stavkaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stavka)) {
            return false;
        }
        Stavka other = (Stavka) object;
        if ((this.stavkaPK == null && other.stavkaPK != null) || (this.stavkaPK != null && !this.stavkaPK.equals(other.stavkaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Stavka[ stavkaPK=" + stavkaPK + " ]";
    }
    
}
