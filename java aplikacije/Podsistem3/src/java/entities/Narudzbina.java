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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bogdan
 */
@Entity
@Table(name = "narudzbina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Narudzbina.findAll", query = "SELECT n FROM Narudzbina n"),
    @NamedQuery(name = "Narudzbina.findByIdNarudzbina", query = "SELECT n FROM Narudzbina n WHERE n.idNarudzbina = :idNarudzbina"),
    @NamedQuery(name = "Narudzbina.findByGrad", query = "SELECT n FROM Narudzbina n WHERE n.grad = :grad"),
    @NamedQuery(name = "Narudzbina.findByCena", query = "SELECT n FROM Narudzbina n WHERE n.cena = :cena"),
    @NamedQuery(name = "Narudzbina.findByVreme", query = "SELECT n FROM Narudzbina n WHERE n.vreme = :vreme"),
    @NamedQuery(name = "Narudzbina.findByAdresa", query = "SELECT n FROM Narudzbina n WHERE n.adresa = :adresa")})
public class Narudzbina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idNarudzbina")
    private Integer idNarudzbina;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "grad")
    private String grad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private double cena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vreme;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "adresa")
    private String adresa;
    @JoinColumn(name = "idKorisnik", referencedColumnName = "idKorisnik")
    @ManyToOne(optional = false)
    private Korisnik idKorisnik;

    public Narudzbina() {
    }

    public Narudzbina(Integer idNarudzbina) {
        this.idNarudzbina = idNarudzbina;
    }

    public Narudzbina(Integer idNarudzbina, String grad, double cena, Date vreme, String adresa) {
        this.idNarudzbina = idNarudzbina;
        this.grad = grad;
        this.cena = cena;
        this.vreme = vreme;
        this.adresa = adresa;
    }

    public Integer getIdNarudzbina() {
        return idNarudzbina;
    }

    public void setIdNarudzbina(Integer idNarudzbina) {
        this.idNarudzbina = idNarudzbina;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Korisnik getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(Korisnik idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNarudzbina != null ? idNarudzbina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Narudzbina)) {
            return false;
        }
        Narudzbina other = (Narudzbina) object;
        if ((this.idNarudzbina == null && other.idNarudzbina != null) || (this.idNarudzbina != null && !this.idNarudzbina.equals(other.idNarudzbina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Narudzbina[ idNarudzbina=" + idNarudzbina + " ]";
    }
    
}
