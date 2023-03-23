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
public class TransakcijeResponse implements Serializable{
    private final List<TransakcijaResponse> transkacije;

    public TransakcijeResponse(List<TransakcijaResponse> transkacije) {
        this.transkacije = transkacije;
    }

    public List<TransakcijaResponse> getTranskacije() {
        return transkacije;
    }
    
    
}
