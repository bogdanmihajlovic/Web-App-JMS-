/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package response;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Bogdan
 */
public class NarudzbinaResponse implements Serializable {
    private final int idNarudzbina;
    private final int idKorisnik;
    private final String grad;
    private final String adresa;
    private final double cena;
    private final Date vreme;

    public NarudzbinaResponse(int idNarudzbina, int idKorisnik, String grad, String adresa, double cena, Date vreme) {
        this.idNarudzbina = idNarudzbina;
        this.idKorisnik = idKorisnik;
        this.grad = grad;
        this.adresa = adresa;
        this.cena = cena;
        this.vreme = vreme;
    }

    @Override
    public String toString() {
        return "NarudzbinaResponse{" + "idNarudzbina=" + idNarudzbina + ", idKorisnik=" + idKorisnik + ", grad=" + grad + ", adresa=" + adresa + ", cena=" + cena + ", vreme=" + vreme + '}';
    }
    
}
