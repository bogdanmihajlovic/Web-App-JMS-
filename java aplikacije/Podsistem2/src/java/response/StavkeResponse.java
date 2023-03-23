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
public class StavkeResponse implements Serializable{
    private final List<StavkaResponse> stavke;

    public StavkeResponse(List<StavkaResponse> stavke) {
        this.stavke = stavke;
    }

    public List<StavkaResponse> getStavke() {
        return stavke;
    }
    
    
    
    
}
