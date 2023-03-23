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
public class NarudzbineResponse implements Serializable {
    private final List<NarudzbinaResponse> narudzbine;

    public NarudzbineResponse(List<NarudzbinaResponse> narudzbine) {
        this.narudzbine = narudzbine;
    }

    public List<NarudzbinaResponse> getNarudzbine() {
        return narudzbine;
    }
    
}
