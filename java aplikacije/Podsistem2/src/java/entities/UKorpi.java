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
@Table(name = "u_korpi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UKorpi.findAll", query = "SELECT u FROM UKorpi u"),
    @NamedQuery(name = "UKorpi.findByIdKorpa", query = "SELECT u FROM UKorpi u WHERE u.uKorpiPK.idKorpa = :idKorpa"),
    @NamedQuery(name = "UKorpi.findByIdProdaja", query = "SELECT u FROM UKorpi u WHERE u.uKorpiPK.idProdaja = :idProdaja"),
    @NamedQuery(name = "UKorpi.findByKolicina", query = "SELECT u FROM UKorpi u WHERE u.kolicina = :kolicina")})
public class UKorpi implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UKorpiPK uKorpiPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicina")
    private int kolicina;

    public UKorpi() {
    }

    public UKorpi(UKorpiPK uKorpiPK) {
        this.uKorpiPK = uKorpiPK;
    }

    public UKorpi(UKorpiPK uKorpiPK, int kolicina) {
        this.uKorpiPK = uKorpiPK;
        this.kolicina = kolicina;
    }

    public UKorpi(int idKorpa, int idProdaja) {
        this.uKorpiPK = new UKorpiPK(idKorpa, idProdaja);
    }

    public UKorpiPK getUKorpiPK() {
        return uKorpiPK;
    }

    public void setUKorpiPK(UKorpiPK uKorpiPK) {
        this.uKorpiPK = uKorpiPK;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uKorpiPK != null ? uKorpiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UKorpi)) {
            return false;
        }
        UKorpi other = (UKorpi) object;
        if ((this.uKorpiPK == null && other.uKorpiPK != null) || (this.uKorpiPK != null && !this.uKorpiPK.equals(other.uKorpiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UKorpi[ uKorpiPK=" + uKorpiPK + " ]";
    }
    
}
