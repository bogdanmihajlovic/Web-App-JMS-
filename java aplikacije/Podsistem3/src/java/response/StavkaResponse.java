/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package response;

import java.io.Serializable;

/**
 *
 * @author Bogdan
 */
public class StavkaResponse implements Serializable {
    private final int idA;
    private final String naziv;
    private final int kolicina;
    private final double cenaKom;

    public StavkaResponse(int idA, String naziv, int kolicina, double cenaKom) {
        this.idA = idA;
        this.naziv = naziv;
        this.kolicina = kolicina;
        this.cenaKom = cenaKom;
    }

    public int getIdA() {
        return idA;
    }

    public String getNaziv() {
        return naziv;
    }

    public int getKolicina() {
        return kolicina;
    }

    public double getCenaKom() {
        return cenaKom;
    }

    
    @Override
    public String toString() {
        return "StavkaResponse{" + "idA=" + idA + ", naziv=" + naziv + ", kolicina=" + kolicina + ", cenaKom=" + cenaKom + '}';
    }
    
    
}
