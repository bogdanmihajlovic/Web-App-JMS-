/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package response;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Bogdan
 */
public class TransakcijaResponse implements Serializable {
    private final int idTrans;
    private final double placeno;
    private final Date vreme;

    public TransakcijaResponse(int idTrans, double placeno, Date vreme) {
        this.idTrans = idTrans;
        this.placeno = placeno;
        this.vreme = vreme;
    }

    @Override
    public String toString() {
        return "TransakcijaResponse{" + "idTrans=" + idTrans + ", placeno=" + placeno + ", vreme=" + vreme + '}';
    }
    
    
}
