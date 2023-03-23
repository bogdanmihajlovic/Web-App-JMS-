/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package klijent;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;

import java.io.InputStreamReader;

/**
 *
 * @author Bogdan
 */
public class Klijent {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
        while(true){    
            String url = "http://localhost:8080/CentralniServer/";
            String method = "GET";
            String url1 = "";
            
            System.out.println("Izaberi funciju:");
            System.out.println("1. Kreiranje grada POST URL: resources/podsistem1/grad/{naziv}");
            System.out.println("2. Kreiranje grada POST URL: resources/podsistem1/korisnik/{username}/{password}/{imePrezime}/{adresa}/{idG}");
            System.out.println("3. Kreiranje grada POST URL: resources/podsistem1/korisnik/{username}/{novac}");
            System.out.println("4. Promena adrese i grada za korisnika. POST URL: resources/podsistem1/korisnik/{username}/{adresa}/{idG}");
            
            System.out.println("5. Kreiranje kategorije. POST URL: resources/podsistem2/kategorija/{naziv}?idNadKat");
            System.out.println("6. Kreiranje artikla. POST URL: resources/podsistem2/artikal/{naziv}/{idKat}?opis");
            System.out.println("7. Menjanje cene artikla. POST URL: resources/podsistem2/artikal/cena/{idKor}/{idA}/{cena}");
            System.out.println("8. ostavljanje popusta za artikal. POST URL: resources/podsistem2/artikal/popust/{idKor}/{idA}/{cena}");
            System.out.println("9. Dodavanje artikala u određenoj količini u korpu. POST URL: resources/podsistem2/korpa/add/{username}/{idA}/{usernameProdavac}/{kolicina}");
            System.out.println("10.Brisanje artikla u određenoj količini iz korpe POST URL: resources/korpa/delete/{username}/{idA}/{usernameProdavac}/{kolicina}");
            
            System.out.println("11. Placanje POST URL: resources/podsistem3/placanje/{username}");
            
            System.out.println("12.Dohvatanje svih gradova GET URL: resources/podsistem1/gradovi");
            System.out.println("13.Dohvatanje svih korisnika GET URL: resources/podsistem1/korisnici");
           
            System.out.println("14.Dohvatanje svih kategorija GET URL: resources/podsistem2/kategorija");
            System.out.println("15.Dohvatanje artikla koje prodaje korisnik GET URL: resources/podsistem2/prodaje/{username}");
            System.out.println("16.Dohvatanje korpe GET URL: resources/podsistem2/ukorpi/{username}");
            
            System.out.println("17.Dohvatanje odredjeni narudz GET URL: resources/podsistem3/narudzbine/{username}");
            System.out.println("18.Dohvatanje svih narudz GET URL: resources/podsistem3/narudzbine/");
            System.out.println("19.Dohvatanje svih transakcija narudz GET URL: resources/podsistem3/transkacije");
            System.out.println("20. Dodavanje prodaje artikla POST URL: resources/prodaje/add/{idA}/{idProdavac}/{cena}");
            System.out.println("Izlaz");
            
            BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));
 
            // Reading data using readLine
            url1 = reader.readLine();
            if(url1.equals("Izlaz")){
                break;
            }
            method = reader.readLine();
                      
            url = url + url1; 
            
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod(method);

            int responseCode = con.getResponseCode();
            System.out.println("Sending " + method + " request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
    
}
