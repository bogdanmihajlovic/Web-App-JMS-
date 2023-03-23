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
public class GradResponse implements Serializable {
    private final Integer idGrad;
    private final String naziv;
    
    public GradResponse(Integer idG, String n){
        idGrad = idG;
        naziv =  n;
    }
    
    @Override
    public String toString(){
        return naziv + " " + idGrad;
    }
    
}
