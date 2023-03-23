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
public class KategorijeResponse implements Serializable{
    private final List<KategorijaResponse> kategorije;

    public KategorijeResponse(List<KategorijaResponse> kategorije) {
        this.kategorije = kategorije;
    }

    public List<KategorijaResponse> getKategorije() {
        return kategorije;
    }
    
}
