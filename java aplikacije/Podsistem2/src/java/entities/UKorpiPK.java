/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Bogdan
 */
@Embeddable
public class UKorpiPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idKorpa")
    private int idKorpa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idProdaja")
    private int idProdaja;

    public UKorpiPK() {
    }

    public UKorpiPK(int idKorpa, int idProdaja) {
        this.idKorpa = idKorpa;
        this.idProdaja = idProdaja;
    }

    public int getIdKorpa() {
        return idKorpa;
    }

    public void setIdKorpa(int idKorpa) {
        this.idKorpa = idKorpa;
    }

    public int getIdProdaja() {
        return idProdaja;
    }

    public void setIdProdaja(int idProdaja) {
        this.idProdaja = idProdaja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idKorpa;
        hash += (int) idProdaja;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UKorpiPK)) {
            return false;
        }
        UKorpiPK other = (UKorpiPK) object;
        if (this.idKorpa != other.idKorpa) {
            return false;
        }
        if (this.idProdaja != other.idProdaja) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UKorpiPK[ idKorpa=" + idKorpa + ", idProdaja=" + idProdaja + " ]";
    }
    
}
