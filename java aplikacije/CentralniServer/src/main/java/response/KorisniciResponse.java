/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package response;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Bogdan
 */
public class KorisniciResponse implements Serializable{
    private final List<KorisnikResponse> korisnici;

    public KorisniciResponse(List<KorisnikResponse> korisnici) {
        this.korisnici = korisnici;
    }

    public List<KorisnikResponse> getKorisnici() {
        return korisnici;
    }
    
    
}
