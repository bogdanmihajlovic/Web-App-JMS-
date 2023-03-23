/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem1;

import javax.annotation.Resource;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.ObjectMessage;
import javax.jms.JMSConsumer;
import javax.jms.TextMessage;

import entities.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import response.GradResponse;
import response.GradoviResponse;
import response.KorisniciResponse;
import response.KorisnikResponse;
/**
 *
 * @author Bogdan
 */

public class Main {
        

    
    @Resource(lookup="__myConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup="myQueue2")
    private static Queue myQueue;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
        EntityManager em = emf.createEntityManager();
        
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer= context.createConsumer(myQueue, "Type = '1'"); //1 podsistem1
        JMSProducer producer = context.createProducer();
        
        String msg = null;
        TextMessage txtResponse = null;
        ObjectMessage objResponse = null;
        while (true) {
            try {
                System.out.println("podsistem1 wait for request");
                // prijem poruke od servera
                TextMessage txtMsg = (TextMessage)consumer.receive();
                
                switch (txtMsg.getIntProperty("function")) {
                    case 1:
                        // obradjivanje zahteva
                        System.out.println("podsistem1 funckija 1 prijem");
                        msg = function1(txtMsg, em);                       
                        
                        // slanje odgovor serveru
                        txtResponse = context.createTextMessage(msg);
                        txtResponse.setStringProperty("Type", "0");
                        System.out.println("podsistem1 funckija 1 slanje");
                        producer.send(myQueue, txtResponse);
                        
                        break;
                    case 2:
                        // obradjivanje zahteva
                        System.out.println("podsistem1 funckija 2 prijem");
                        msg = function2(txtMsg, em);
                        
                        // slanje odgovor serveru
                        txtResponse = context.createTextMessage(msg);
                        txtResponse.setStringProperty("Type", "0");
                        System.out.println("podsistem1 funckija 2 slanje");
                        producer.send(myQueue, txtResponse);
                        break;
                    case 3:
                        // obradjivanje zahteva
                        System.out.println("podsistem1 funckija 3 prijem");
                        msg = function3(txtMsg, em);
                        
                        // slanje odgovor serveru
                        txtResponse = context.createTextMessage(msg);
                        txtResponse.setStringProperty("Type", "0");
                        System.out.println("podsistem1 funckija 3 slanje");
                        producer.send(myQueue, txtResponse);
                        break;
                    case 4:
                        // obradjivanje zahteva
                        System.out.println("podsistem1 funckija 4 prijem");

                        msg = function4(txtMsg, em);
                        
                        // slanje odgovor serveru
                        txtResponse = context.createTextMessage(msg);
                        txtResponse.setStringProperty("Type", "0");
                        System.out.println("podsistem1 funckija 4 slanje");
                        producer.send(myQueue, txtResponse);

                        break;
                    case 12:
                        System.out.println("podsistem1 funckija 12 prijem");
                        GradoviResponse gradoviResponse = function12(em);
                        objResponse = context.createObjectMessage(gradoviResponse);
                        objResponse.setStringProperty("Type", "0");
                        System.out.println("podsistem1 funckija 12 slanje");                        
                        producer.send(myQueue, objResponse);
                        
                        break;
                    case 13:
                        System.out.println("podsistem1 funckija 13 prijem");

                        KorisniciResponse korisniciResponse = function13(em);
                        objResponse = context.createObjectMessage(korisniciResponse);
                        objResponse.setStringProperty("Type", "0");  
                        System.out.println("podsistem1 funckija 13 slanje");
                        producer.send(myQueue, objResponse);
                        

                        break;                        
                    default:
                        throw new AssertionError();
                }
                
                
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
        
        
        // Testiranje podsistema
        //System.out.println(function1("Cacak", em));
        //System.out.println(function2("bogdanm;123;Bogdan Mihajlovic;Marsala Tita 85;2", em));
        //System.out.println(function3("bogdanm;123.45", em));
        

        //System.out.println(function4("bogdanm;Marsala Tita 95G;1", em));
        //System.out.println(function1("", em));
    
    }
    
    private static String function1(TextMessage txtMsg, EntityManager em){
        String ret = "";
        try {
      
            String naziv = txtMsg.getText();
            if(naziv.length() == 0){
                return "Niste zadali ime grada.";
            }
            
            Grad grad = new Grad();
            
            grad.setNaziv(naziv);
            
            em.getTransaction().begin();
            em.persist(grad);
            em.getTransaction().commit();
            ret = "Grad je uspesno dodat.";
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ret = "Doslo je do greske u prenosu.";
        } finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
                ret = "Transakcija nije izvrsena.";
            }
                
        }
        return ret;
    }
    
    
    private static String function2(TextMessage txtMsg, EntityManager em){
        String ret = "";
        try {
           
            String msg = txtMsg.getText();
            String[] podaci = msg.split(";");
            String username = podaci[0];
            String password = podaci[1];
            String imePrezime = podaci[2];
            String adresa = podaci[3];
            int idG = Integer.parseInt(podaci[4]);
            
            // provera korisnik 
            Korisnik korisnik = proveraKorisnikPostoji(username, em);
            if(korisnik != null){
                return "Korisnik sa datim username postoji.";
            }
            
            // provera grad
            Grad grad = em.find(Grad.class, idG);
            if(grad == null){
                return "Zadati grad ne postoji.";
            }
            
            korisnik = new Korisnik();
            korisnik.setUsername(username);
            korisnik.setPassword(password);
            korisnik.setImePrezime(imePrezime);
            korisnik.setAdresa(adresa);
            korisnik.setIdG(grad);
            
            em.getTransaction().begin();
            em.persist(korisnik);
            em.getTransaction().commit();
            ret = "Uspesno je kreiran korisnik";
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ret = "Doslo je do greske u prenosu.";
        } catch(NumberFormatException nfe){
            ret = "Losi parametri.";
        }finally {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        return ret;
    }
    
    
    private static String function3(TextMessage txtMsg, EntityManager em){
        String ret = "";
        try {
            

            String msg = txtMsg.getText();
            String[] podaci = msg.split(";");
            String username = podaci[0];
            double novac = Double.parseDouble(podaci[1]);
            
            Korisnik korisnik = proveraKorisnikPostoji(username, em);
            if(korisnik == null){
                return "Pogresan username.";
            }
            
            korisnik.setNovac(korisnik.getNovac() + novac);
            
            em.getTransaction().begin();
            em.persist(korisnik);
            em.getTransaction().commit();
            
            ret = "Novac je uspesno dodat.";
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ret = "Doslo je do greske u prenosu.";
        } catch(NumberFormatException nfe){
            ret = "Losi parametri.";
        }finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
                ret = "Transkacija nije izvrsena.";
            }
                
        }
        return ret;
    }
    

    private static String function4(TextMessage txtMsg, EntityManager em){
        String ret = "";
        try {
            
            String msg = txtMsg.getText();
            
            String[] podaci = msg.split(";");
            
            String username = podaci[0];
            String adresa = podaci[1];
            int idG = Integer.parseInt(podaci[2]);
            Korisnik korisnik = proveraKorisnikPostoji(username, em);
            
            if(korisnik == null){
                return "Pogresan username.";
            }
            
            Grad grad = em.find(Grad.class, idG);
            if(grad == null){
                return "Uneti grad ne postoji.";
            }
            
            korisnik.setAdresa(adresa);
            korisnik.setIdG(grad);
            
            em.getTransaction().begin();
            em.persist(korisnik);
            em.getTransaction().commit();
            ret = "Adresa i grad su promenjeni.";
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ret = "Doslo je do greske.";
        } catch(NumberFormatException nfe){
            ret = "Losi parametri."; 
        } finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
                ret = "Transakcija nije izvrsena.";
            }
                
        }
        return ret;
    }
    
    
    private static GradoviResponse function12(EntityManager em){
 
        List<GradResponse> retList = new ArrayList<>();
        try {
            List<Grad> gradovi = em.createNamedQuery("Grad.findAll", Grad.class).getResultList();
            
            for (Grad grad : gradovi) {
                GradResponse gr = new GradResponse(grad.getIdGrad(), grad.getNaziv());
                retList.add(gr);
            }
            
        } finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();               
            }
                
        }
        GradoviResponse response = new GradoviResponse(retList);
        
        return response;
    }
    
    private static KorisniciResponse function13(EntityManager em){
 
        List<KorisnikResponse> retList = new ArrayList<>();
        try {
            List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findAll", Korisnik.class).getResultList();
            
            for (Korisnik korisnik: korisnici) {
                KorisnikResponse k = new KorisnikResponse(
                        korisnik.getIdKorisnik(),
                        korisnik.getUsername(),
                        korisnik.getPassword(),
                        korisnik.getImePrezime(),
                        korisnik.getAdresa(),
                        korisnik.getIdG().getIdGrad(),
                        korisnik.getNovac()
                
                );
                retList.add(k);
            }
            
        } finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();               
            }
                
        }
        KorisniciResponse response = new KorisniciResponse(retList);
        
        return response;
    }
    
    private static Korisnik proveraKorisnikPostoji(String username, EntityManager em){
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).
                    setParameter("username", username).
                    getResultList();
            
            if(korisnici.size() != 1){
                return null;
            }
            return korisnici.get(0);
    }
}
