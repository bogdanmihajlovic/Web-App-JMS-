/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bogdan
 */
@Entity
@Table(name = "korpa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korpa.findAll", query = "SELECT k FROM Korpa k"),
    @NamedQuery(name = "Korpa.findByIdKorpa", query = "SELECT k FROM Korpa k WHERE k.idKorpa = :idKorpa"),
    @NamedQuery(name = "Korpa.findByUkupncaCena", query = "SELECT k FROM Korpa k WHERE k.ukupncaCena = :ukupncaCena")})
public class Korpa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKorpa")
    private Integer idKorpa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ukupncaCena")
    private double ukupncaCena;
    @JoinColumn(name = "idKorisnika", referencedColumnName = "idKorisnik")
    @OneToOne(optional = false)
    private Korisnik idKorisnika;

    public Korpa() {
    }

    public Korpa(Integer idKorpa) {
        this.idKorpa = idKorpa;
    }

    public Korpa(Integer idKorpa, double ukupncaCena) {
        this.idKorpa = idKorpa;
        this.ukupncaCena = ukupncaCena;
    }

    public Integer getIdKorpa() {
        return idKorpa;
    }

    public void setIdKorpa(Integer idKorpa) {
        this.idKorpa = idKorpa;
    }

    public double getUkupncaCena() {
        return ukupncaCena;
    }

    public void setUkupncaCena(double ukupncaCena) {
        this.ukupncaCena = ukupncaCena;
    }

    public Korisnik getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(Korisnik idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKorpa != null ? idKorpa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korpa)) {
            return false;
        }
        Korpa other = (Korpa) object;
        if ((this.idKorpa == null && other.idKorpa != null) || (this.idKorpa != null && !this.idKorpa.equals(other.idKorpa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Korpa[ idKorpa=" + idKorpa + " ]";
    }
    
}
