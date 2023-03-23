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
public class KategorijaResponse implements Serializable{
    private final int idKat;
    private final String naziv;

    public KategorijaResponse(int idKat, String naziv) {
        this.idKat = idKat;
        this.naziv = naziv;
    }
    
    @Override
    public String toString() {
        return "KategorijaResponse{" + "idKat=" + idKat + ", naziv=" + naziv + '}';
    }
    
    
}
