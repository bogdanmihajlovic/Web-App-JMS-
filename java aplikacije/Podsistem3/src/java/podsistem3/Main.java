/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem3;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import response.*;
import entities.*;
import java.util.Date;
import javax.jms.Message;

/**
 *
 * @author Bogdan
 */
public class Main {
    @Resource(lookup="__myConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup="myQueuePodsistem3")
    private static Queue myQueue;
    

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
        EntityManager em = emf.createEntityManager();
        
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer= context.createConsumer(myQueue, "Type = '1'"); //1 podsistem1
        JMSProducer producer = context.createProducer();
        
        String msg = null;
        TextMessage txtResponse = null;
        TextMessage txtMsg = null;
        ObjectMessage objResponse = null;
        ObjectMessage objMsg = null;
        while (true) {
            try {
                System.out.println("podsistem3 wait for request");
                // prijem poruke od servera
                txtMsg = null;
                objMsg = null;
                Message message = consumer.receive();
                
                int swcase = 0;
                if(message instanceof ObjectMessage){
                    objMsg = (ObjectMessage)message;               
                    swcase = objMsg.getIntProperty("function");
                    System.out.println("podsistem3 primljeno objectmessage");
                }else{

                    txtMsg = (TextMessage)message;
                    swcase = txtMsg.getIntProperty("function");

                }
                
                
                switch (swcase) {
                    case 3:
                        System.out.println("podsistem3 funckija 3 primanje zahteva");
                        msg = function3(txtMsg, em);
                        
                        txtResponse = context.createTextMessage(msg);
                        txtResponse.setStringProperty("Type", "2");
                        producer.send(myQueue, txtResponse);
                    case 11:
                        // obradjivanje zahteva
                        System.out.println("podsistem funckija 11 primanje zahteva");
                        msg = function11(objMsg, em);                       
                        
                        // slanje odgovor serveru
                        txtResponse = context.createTextMessage(msg);
                        txtResponse.setStringProperty("Type", "0");
                  
                        producer.send(myQueue, txtResponse);
                        System.out.println("podsistem3 funckija 11 vracanje zahteva");
                        break;
                    case 17:
                        // obradjivanje zahteva
                        System.out.println("podsistem3 funckija 17 primanje zahteva");
                        NarudzbineResponse narudzbineResponse1 = function17(txtMsg, em);
                        
                        // slanje odgovor serveru
                        objResponse = context.createObjectMessage(narudzbineResponse1);
                        objResponse.setStringProperty("Type", "0");                        
                        producer.send(myQueue, objResponse);
                        break;
                    case 18:
                        System.out.println("podsistem3 funckija 18 primanje zahteva");
                        NarudzbineResponse narudzbineResponse = function18(em);
                        
                        objResponse = context.createObjectMessage(narudzbineResponse);
                        objResponse.setStringProperty("Type", "0");                        
                        producer.send(myQueue, objResponse);
                        break;
                    case 19:
                        System.out.println("podsistem3 funckija 19 primanje zahteva");
                        TransakcijeResponse transkacijeResponse = function19(em);
                        
                        objResponse = context.createObjectMessage(transkacijeResponse);
                        objResponse.setStringProperty("Type", "0");                        
                        producer.send(myQueue, objResponse);
                        break;
                    case 22: // kreiranje korisnika
                        // obradjivanje zahteva
                        System.out.println("podsistem3 funckija 22 primanje zahteva");
                        msg = createUser(txtMsg, em);                       
                        
                        // slanje odgovor serveru
                        txtResponse = context.createTextMessage(msg);
                        txtResponse.setStringProperty("Type", "2");
                        producer.send(myQueue, txtResponse);
                        break;                    
                    default:
                        System.out.println("poslata opcije nema");
                        continue; 
                }
                
                
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }catch(NumberFormatException e){
                
            }
        }        
    }
    private static String function11(ObjectMessage objMsg, EntityManager em){
        String ret = "";
        try {
            StavkeResponse stavkeResponse = (StavkeResponse)objMsg.getObject();
            String username = objMsg.getStringProperty("username");
            // provera korisnik
            Korisnik korisnik = proveraKorisnikPostoji(username, em);
            if(korisnik == null){
                return "FAIL";
            }
            // provera novac
            double cena = 0;
            for (StavkaResponse stavkaResponse : stavkeResponse.getStavke()) {
                cena += stavkaResponse.getCenaKom()*stavkaResponse.getKolicina();
            }
            if(cena > korisnik.getNovac()){
                return "FAIL";
            }
            // moze da se kreira
            Narudzbina narudzbina = new Narudzbina();
            narudzbina.setAdresa(korisnik.getAdresa());
            narudzbina.setGrad(korisnik.getGrad());
            narudzbina.setIdKorisnik(korisnik);
            narudzbina.setVreme(new Date());
            narudzbina.setCena(cena);
            
            em.getTransaction().begin();
            em.persist(narudzbina);
            em.flush();
            em.getTransaction().commit();

            for (StavkaResponse stavkaResponse : stavkeResponse.getStavke()) {
                Stavka stavka = new Stavka();
                stavka.setCenaKomad(stavkaResponse.getCenaKom());
                stavka.setKolicina(stavkaResponse.getKolicina());
                stavka.setStavkaPK(new StavkaPK(narudzbina.getIdNarudzbina(), stavkaResponse.getIdA()));
                
                em.getTransaction().begin();
                em.persist(stavka);
                em.flush();
                em.getTransaction().commit();
            }
            Transakcija transakcija = new Transakcija();
            transakcija.setPlaceno(cena);
            transakcija.setVreme(new Date());
            
            em.getTransaction().begin();
            em.persist(transakcija);
            em.flush();
            em.getTransaction().commit();
            
            korisnik.setNovac(korisnik.getNovac() - cena);
            em.getTransaction().begin();
            em.persist(korisnik);
            em.flush();
            em.getTransaction().commit();
            
            ret = "SUC";
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();  
                ret = "FAIL";
            }
                
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
    
    private static NarudzbineResponse function17(TextMessage txtMessage, EntityManager em){
        
       List<NarudzbinaResponse> retList = new ArrayList<>();
        try {
            String username = txtMessage.getText();
            List<Narudzbina> narudzbine = em.createNamedQuery("Narudzbina.findAll", Narudzbina.class).getResultList();
            Korisnik korisnik = proveraKorisnikPostoji(username, em);
            
            for (Narudzbina nar : narudzbine) {
                if(korisnik != nar.getIdKorisnik())
                    continue;
                
                NarudzbinaResponse n = new NarudzbinaResponse(
                        nar.getIdNarudzbina(),
                        nar.getIdKorisnik().getIdKorisnik(),
                        nar.getGrad(),
                        nar.getAdresa(),
                        nar.getCena(),
                        nar.getVreme()
                );
                
                retList.add(n);
            }
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();               
            }
                
        }
        NarudzbineResponse response = new NarudzbineResponse(retList);
        
        return response;
    }
    
    
    private static NarudzbineResponse function18(EntityManager em){
        
       List<NarudzbinaResponse> retList = new ArrayList<>();
        try {
            List<Narudzbina> narudzbine = em.createNamedQuery("Narudzbina.findAll", Narudzbina.class).getResultList();
            
            for (Narudzbina nar : narudzbine) {
                NarudzbinaResponse n = new NarudzbinaResponse(
                        nar.getIdNarudzbina(),
                        nar.getIdKorisnik().getIdKorisnik(),
                        nar.getGrad(),
                        nar.getAdresa(),
                        nar.getCena(),
                        nar.getVreme()
                );
                
                retList.add(n);
            }
            
        } finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();               
            }
                
        }
        NarudzbineResponse response = new NarudzbineResponse(retList);
        
        return response;
    }
    
    private static TransakcijeResponse function19(EntityManager em){
        List<TransakcijaResponse> retList = new ArrayList<>();
        try {
            List<Transakcija> transakcije = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
            
            for (Transakcija tra : transakcije) {
                TransakcijaResponse n = new TransakcijaResponse(
                        tra.getIdTranskacija(),
                        tra.getPlaceno(),
                        tra.getVreme()
                );
                
                retList.add(n);
            }
            
        } finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();               
            }
                
        }
        TransakcijeResponse response = new TransakcijeResponse(retList);
        
        return response;
    }
    
    private static String createUser(TextMessage txtMsg, EntityManager em){
        String ret = "";
        try {
           
            String msg = txtMsg.getText();
            String[] podaci = msg.split(";");
            String username = podaci[0];
            String grad = podaci[1];
            String adresa = podaci[2];
            
                                
            // provera korisnik 
            Korisnik korisnik = proveraKorisnikPostoji(username, em);
            if(korisnik != null){
                return "Korisnik sa datim username postoji.";
            }
            
            korisnik = new Korisnik();
            korisnik.setUsername(username);
            korisnik.setGrad(grad);
            korisnik.setAdresa(adresa);
            
          
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
