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
import javax.persistence.ManyToOne;
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
@Table(name = "prodaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prodaje.findAll", query = "SELECT p FROM Prodaje p"),
    @NamedQuery(name = "Prodaje.findByIdProdaje", query = "SELECT p FROM Prodaje p WHERE p.idProdaje = :idProdaje"),
    @NamedQuery(name = "Prodaje.findByCena", query = "SELECT p FROM Prodaje p WHERE p.cena = :cena"),
    @NamedQuery(name = "Prodaje.findByPopust", query = "SELECT p FROM Prodaje p WHERE p.popust = :popust")})
public class Prodaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProdaje")
    private Integer idProdaje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private double cena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "popust")
    private double popust;
    @JoinColumn(name = "idArtikal", referencedColumnName = "idArtikal")
    @ManyToOne(optional = false)
    private Artikal idArtikal;
    @JoinColumn(name = "idProdavac", referencedColumnName = "idKorisnik")
    @ManyToOne(optional = false)
    private Korisnik idProdavac;

    public Prodaje() {
    }

    public Prodaje(Integer idProdaje) {
        this.idProdaje = idProdaje;
    }

    public Prodaje(Integer idProdaje, double cena, double popust) {
        this.idProdaje = idProdaje;
        this.cena = cena;
        this.popust = popust;
    }

    public Integer getIdProdaje() {
        return idProdaje;
    }

    public void setIdProdaje(Integer idProdaje) {
        this.idProdaje = idProdaje;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getPopust() {
        return popust;
    }

    public void setPopust(double popust) {
        this.popust = popust;
    }

    public Artikal getIdArtikal() {
        return idArtikal;
    }

    public void setIdArtikal(Artikal idArtikal) {
        this.idArtikal = idArtikal;
    }

    public Korisnik getIdProdavac() {
        return idProdavac;
    }

    public void setIdProdavac(Korisnik idProdavac) {
        this.idProdavac = idProdavac;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProdaje != null ? idProdaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prodaje)) {
            return false;
        }
        Prodaje other = (Prodaje) object;
        if ((this.idProdaje == null && other.idProdaje != null) || (this.idProdaje != null && !this.idProdaje.equals(other.idProdaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Prodaje[ idProdaje=" + idProdaje + " ]";
    }
    
}
