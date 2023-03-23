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
public class ArtikliResponse implements Serializable {
    private final List<ArtikalResponse> artikli;

    public ArtikliResponse(List<ArtikalResponse> artikli) {
        this.artikli = artikli;
    }

    public List<ArtikalResponse> getArtikli() {
        return artikli;
    }
        
}
