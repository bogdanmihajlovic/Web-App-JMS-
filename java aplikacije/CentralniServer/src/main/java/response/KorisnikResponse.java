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
public class KorisnikResponse implements Serializable{
    private final int idK;
    private final String username;
    private final String pass;
    private final String imePrezime;
    private final String adresa;
    private final int idG;
    private final double novac;

    public KorisnikResponse(int idK, String username, String pass, String imePrezime, String adresa, int idG, double novac) {
        this.idK = idK;
        this.username = username;
        this.pass = pass;
        this.imePrezime = imePrezime;
        this.adresa = adresa;
        this.idG = idG;
        this.novac = novac;
    }
    
    @Override
    public String toString(){
        return idK + " " + username + " " + pass + " " + imePrezime + " " + adresa + " " + idG + " " + novac;
    }
    
}
