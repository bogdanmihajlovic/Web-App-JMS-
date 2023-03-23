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
import javax.validation.constraints.Size;

/**
 *
 * @author Bogdan
 */
@Embeddable
public class RecenzijaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idKor")
    private int idKor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "idArtikal")
    private String idArtikal;

    public RecenzijaPK() {
    }

    public RecenzijaPK(int idKor, String idArtikal) {
        this.idKor = idKor;
        this.idArtikal = idArtikal;
    }

    public int getIdKor() {
        return idKor;
    }

    public void setIdKor(int idKor) {
        this.idKor = idKor;
    }

    public String getIdArtikal() {
        return idArtikal;
    }

    public void setIdArtikal(String idArtikal) {
        this.idArtikal = idArtikal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idKor;
        hash += (idArtikal != null ? idArtikal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecenzijaPK)) {
            return false;
        }
        RecenzijaPK other = (RecenzijaPK) object;
        if (this.idKor != other.idKor) {
            return false;
        }
        if ((this.idArtikal == null && other.idArtikal != null) || (this.idArtikal != null && !this.idArtikal.equals(other.idArtikal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.RecenzijaPK[ idKor=" + idKor + ", idArtikal=" + idArtikal + " ]";
    }
    
}
