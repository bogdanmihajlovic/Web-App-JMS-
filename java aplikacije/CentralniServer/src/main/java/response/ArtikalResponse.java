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
public class ArtikalResponse implements Serializable {
    private final int id;
    private final String naziv;
    private final double cena;

    public ArtikalResponse(int id, String naziv, double cena) {
        this.id = id;
        this.naziv = naziv;
        this.cena = cena;
    }


    @Override
    public String toString() {
        return "ArtikalResponse{" + "id=" + id + ", naziv=" + naziv + ", cena=" + cena + '}';
    }
    
    
    
}
