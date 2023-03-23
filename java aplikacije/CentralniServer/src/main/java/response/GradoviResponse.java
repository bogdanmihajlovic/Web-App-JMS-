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
public class GradoviResponse implements Serializable{
    private final List<GradResponse> gradovi;

    public GradoviResponse(List<GradResponse> gradovi) {
        this.gradovi = gradovi;
    }

    
    public List<GradResponse> getGradovi() {
        return gradovi;
    }


    
}
