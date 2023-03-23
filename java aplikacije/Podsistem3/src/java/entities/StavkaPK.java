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
public class StavkaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idNarudzbine")
    private int idNarudzbine;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idA")
    private int idA;

    public StavkaPK() {
    }

    public StavkaPK(int idNarudzbine, int idA) {
        this.idNarudzbine = idNarudzbine;
        this.idA = idA;
    }

    public int getIdNarudzbine() {
        return idNarudzbine;
    }

    public void setIdNarudzbine(int idNarudzbine) {
        this.idNarudzbine = idNarudzbine;
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idNarudzbine;
        hash += (int) idA;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StavkaPK)) {
            return false;
        }
        StavkaPK other = (StavkaPK) object;
        if (this.idNarudzbine != other.idNarudzbine) {
            return false;
        }
        if (this.idA != other.idA) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.StavkaPK[ idNarudzbine=" + idNarudzbine + ", idA=" + idA + " ]";
    }
    
}
