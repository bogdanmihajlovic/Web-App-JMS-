/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem2;

import com.oracle.webservices.api.databinding.DatabindingModeFeature;
import entities.Artikal;
import entities.Kategorija;
import entities.Korisnik;
import entities.Korpa;
import entities.Prodaje;
import entities.UKorpi;
import entities.UKorpiPK;
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
import response.ArtikalResponse;
import response.ArtikliResponse;
import response.KategorijaResponse;
import response.KategorijeResponse;
import response.StavkaResponse;
import response.StavkeResponse;

/**
 *
 * @author Bogdan
 */
public class Main {

    @Resource(lookup="__myConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup="myQueuePodsistem2")
    private static Queue myQueue;
    
    @Resource(lookup="podsistem3ResoruceQ")
    private static Queue p3rq;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
        EntityManager em = emf.createEntityManager();
        
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(myQueue, "Type = '1'"); //1 podsistem1
        JMSProducer producer = context.createProducer();
        
        String msg = null;
        TextMessage txtResponse = null;
        ObjectMessage objResponse = null;
        while (true) {
            try {
                System.out.println("podsistem2 wait for request");
                // prijem poruke od servera
                TextMessage txtMsg = (TextMessage)consumer.receive();
                
                switch (txtMsg.getIntProperty("function")) {
                    case 5:
                        System.out.println("podsistem2 zahtev 5 primljen");
                        // obradjivanje zahteva
                        msg = function5(txtMsg, em);                       
                        
                        // slanje odgovor serveru
                        txtResponse = context.createTextMessage(msg);
                        txtResponse.setStringProperty("Type", "0");
                        producer.send(myQueue, txtResponse);
                        
                        break;
                    case 6:
                        System.out.println("podsistem2 zahtev 6 primljen");
                        // obradjivanje zahteva
                        msg = function6(txtMsg, em);
                        
                        // slanje odgovor serveru
                        txtResponse = context.createTextMessage(msg);
                        txtResponse.setStringProperty("Type", "0");
                        producer.send(myQueue, txtResponse);
                        break;
                    case 7:
                        System.out.println("podsistem2 zahtev 7 primljen");
                        // obradjivanje zahteva
                        msg = function7(txtMsg, em);
                        
                        // slanje odgovor serveru
                        txtResponse = context.createTextMessage(msg);
                        txtResponse.setStringProperty("Type", "0");
                        producer.send(myQueue, txtResponse);
                        break;
                    case 8:
                        System.out.println("podsistem2 zahtev 8 primljen");
                        // obradjivanje zahteva
                        msg = function8(txtMsg, em);
                        
                        // slanje odgovor serveru
                        txtResponse = context.createTextMessage(msg);
                        txtResponse.setStringProperty("Type", "0");
                        producer.send(myQueue, txtResponse);
                        break;
                    case 9:
                        System.out.println("podsistem2 zahtev 9 primljen");
                        // obradjivanje zahteva
                        msg = function9(txtMsg, em);
                        
                        // slanje odgovor serveru
                        txtResponse = context.createTextMessage(msg);
                        txtResponse.setStringProperty("Type", "0");
                        producer.send(myQueue, txtResponse);
                        System.out.println("podsistem2 zahtev 9 poslat");
                        break;
                    case 10:
                        
                        // obradjivanje zahteva
                        System.out.println("podsistem2 zahtev 10 primljen");
                        msg = function10(txtMsg, em);
                        
                        // slanje odgovor serveru
                        txtResponse = context.createTextMessage(msg);
                        txtResponse.setStringProperty("Type", "0");
                        producer.send(myQueue, txtResponse);
                        break;
                    case 14:
                        System.out.println("podsistem2 zahtev 14 primljen");
                        KategorijeResponse kategorijeResponse = function14(em);
                        objResponse = context.createObjectMessage(kategorijeResponse);
                        objResponse.setStringProperty("Type", "0");                        
                        producer.send(myQueue, objResponse);
                        break;
                    case 15:
                        System.out.println("podsistem2 zahtev 15 primljen");
                        ArtikliResponse artikliResponse = function15(txtMsg, em);
                        objResponse = context.createObjectMessage(artikliResponse);
                        objResponse.setStringProperty("Type", "0");                        
                        producer.send(myQueue, objResponse);
                        break;
                    case 16:
                        ArtikliResponse artResponse = function16(txtMsg, em);
                        objResponse = context.createObjectMessage(artResponse);
                        objResponse.setStringProperty("Type", "0");                        
                        producer.send(myQueue, objResponse);
                        break;
                    case 20:
                        System.out.println("podsistem2 zahtev 20 primljen");
                        msg = createUser(txtMsg, em);                       
                        
                        // slanje odgovor serveru
                        txtResponse = context.createTextMessage(msg);
                        txtResponse.setStringProperty("Type", "2"); // send to Podsistem1Resoruce
                        producer.send(myQueue, txtResponse);
                        break;
                    case 100: // provera da li Korisnik ima novca
                        System.out.println("podsistem2 funckija 100 prijem");
                        StavkeResponse sr = function100(txtMsg, em);
                        
                        objResponse = context.createObjectMessage(sr);
                        objResponse.setStringProperty("Type", "3"); // Podsistem2Resource saljem odgovor
                        System.out.println("podsistem2 funckija 100 slanje");

                        producer.send(p3rq, objResponse);
                        break;
                    case 101:
                        System.out.println("podsistem2 function 101 prijem(brisanje iz korpe)");
                        msg = brisanjeIzKorpe(txtMsg, em);
                        
                        txtResponse = context.createTextMessage(msg);
                        txtResponse.setStringProperty("Type", "5");
                        producer.send(p3rq, txtResponse);
                        System.out.println("podsistem2 funckija 101 slanje");
                        break;
                    case 200:
                        
                        // obradjivanje zahteva
                        System.out.println("podsistem2 zahtev 200 primljen");
                        msg = function200(txtMsg, em);
                        
                        // slanje odgovor serveru
                        txtResponse = context.createTextMessage(msg);
                        txtResponse.setStringProperty("Type", "0");
                        producer.send(myQueue, txtResponse);   
                        break;
                    default:
                        System.out.println("poslata opcije nema");
                        continue;                       
                }
                
                
            } catch (JMSException ex) {
                //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        }     

        // testiranje podsistema
//        System.out.println(function6("iPhone 14;1;Najbolji iPhone", em));
//        System.out.println(function6("Xiami 10;1;Odnos cene/kvalitet", em));
//        System.out.println(function6("Xiami 10;10;Odnos cene/kvalitet", em));

        //System.out.println(function10("bogdankupac;1;bogdanadmin;2", em));


    }
    
    private static String function200(TextMessage txtMsg, EntityManager em){
        String ret = "";
        try {
            
            String msg = txtMsg.getText();
            String[] podaci = msg.split(";");
            int idA = Integer.parseInt(podaci[0]);
            int idKor = Integer.parseInt(podaci[1]);
            double cena = Double.parseDouble(podaci[2]);
            
            Artikal artikal = em.find(Artikal.class, idA);
            if(artikal == null){           
                return "Artikal ne postoji.";
            }
            
            Korisnik korisnik = em.find(Korisnik.class, idKor);
            if(korisnik == null){
                return "Los korisnik.";
            }
            
            Prodaje prodaje = new Prodaje();
            prodaje.setIdArtikal(artikal);
            prodaje.setIdProdavac(korisnik);
            prodaje.setCena(cena);
            
          
            em.getTransaction().begin();
            em.persist(prodaje);
            em.getTransaction().commit();
            ret = "Prodaje je uspesno dodata.";
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ret = "Doslo je do greske u prenosu.";
        } catch(NumberFormatException nfe){
            ret = "Losi parametri.";
        }finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
                ret = "Transakcija nije izvrsena.";
            }
                
        }
        return ret;
    }
    private static StavkeResponse function100(TextMessage txtMsg, EntityManager em){
        
        List<StavkaResponse> retList = new ArrayList<>();
        
        try {
            String username = txtMsg.getText();
            // dohvati korisnika
            Korisnik korisnik = proveraKorisnikPostoji(username, em);
            if(korisnik == null){
                return (new StavkeResponse(retList));
            }
            Korpa korpa = korisnik.getKorpa();
            
            if(korpa != null){
                List<UKorpi> uKorpis = em.createNamedQuery("UKorpi.findByIdKorpa", UKorpi.class).setParameter("idKorpa", korpa.getIdKorpa()).getResultList();
            
                for (UKorpi uKorpi : uKorpis) {
                    int kol = uKorpi.getKolicina();

                    int idProd = uKorpi.getUKorpiPK().getIdProdaja();
                    Prodaje prodaja = em.find(Prodaje.class, idProd);
                    double cenaKomad = prodaja.getCena();
                    int idA = prodaja.getIdArtikal().getIdArtikal();
                    Artikal artikal = em.find(Artikal.class, idA);
                    String naziv = artikal.getNaziv();

                    StavkaResponse sr = new StavkaResponse(idA, naziv, kol, cenaKomad);
                    retList.add(sr);

                }
            }
                               
        } catch (JMSException ex) {
           // Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();               
            }
                
        }
        StavkeResponse response = new StavkeResponse(retList);
        
        return response;
    }
    
    private static String function5(TextMessage txtMsg, EntityManager em){
         String ret = "";
        try {
            
            String msg = txtMsg.getText();
            String[] podaci = msg.split(";");
            String naziv = podaci[0];
            int idNadKat = Integer.parseInt(podaci[1]);
            
            if(naziv.length() == 0){
                return "Niste zadali naziv kategorije.";
            }
            
            Kategorija kategorija = new Kategorija();
            
            kategorija.setNaziv(naziv);
            if(idNadKat != -1){
                kategorija.setIdNadKat(idNadKat);
            }
            
            em.getTransaction().begin();
            em.persist(kategorija);
            em.getTransaction().commit();
            ret = "Kategorija je uspesno dodata.";
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ret = "Doslo je do greske u prenosu.";
        } catch(NumberFormatException nfe){
            ret = "Losi parametri.";
        }finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
                ret = "Transakcija nije izvrsena.";
            }
                
        }
        return ret;
    }
    
  
    private static String function6(TextMessage txtMsg, EntityManager em){
        String ret = "";
        try {
                       
            String msg = txtMsg.getText();
            System.out.println(msg);
            String[] podaci = msg.split(";");
            String naziv = podaci[0];  
            int idKat = Integer.parseInt(podaci[1]);
            String opis = podaci[2];
            
            
            if(naziv.length() == 0){
                return "Niste zadali naziv artikla.";
            }
            // provera kategorije
            Kategorija kategorija = em.find(Kategorija.class, idKat);
            if(kategorija == null){
                return "Kategorija ne postoji.";
            }
            
            Artikal artikal = new Artikal();
            
            artikal.setNaziv(naziv);
            artikal.setIdKat(kategorija);
            if(!opis.equals("NULL")){
                artikal.setOpis(opis);
            }
            
            
            em.getTransaction().begin();
            em.persist(artikal);
            em.getTransaction().commit();
            ret = "Artikal je uspesno dodat.";
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ret = "Doslo je do greske u prenosu.";
        } catch(NumberFormatException nfe){
            ret = "Losi parametri.";
        }finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
                ret = "Transakcija nije izvrsena.";
            }
                
        }
        return ret;
    }    
    
    private static String function7(TextMessage txtMsg, EntityManager em){
         String ret = "";
        try {
            
            String msg = txtMsg.getText();
            String[] podaci = msg.split(";");
           
            int idKor = Integer.parseInt(podaci[0]);
            int idA = Integer.parseInt(podaci[1]);
            double cena = Double.parseDouble(podaci[2]);
            
            List<Prodaje> prodajeList = em.createQuery("SELECT p FROM Prodaje p WHERE p.idProdavac.idKorisnik = :idKor and p.idArtikal.idArtikal = :idA", Prodaje.class).
                    setParameter("idKor", idKor).
                    setParameter("idA", idA).getResultList();
            if(prodajeList == null || prodajeList.isEmpty()){
                return "Prodavac ne prodaje artikal.";
            }
            Prodaje prodaje = prodajeList.get(0);
            prodaje.setCena(cena);
            em.getTransaction().begin();
            em.persist(prodaje);
            em.getTransaction().commit();
            ret = "Cena je uspesno promenjena.";
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ret = "Doslo je do greske u prenosu.";
        } catch(NumberFormatException nfe){
            ret = "Losi parametri.";
        }finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
                ret = "Transakcija nije izvrsena.";
            }
                
        }
        return ret;
    }

    private static String function8(TextMessage txtMsg, EntityManager em){
        String ret = "";
        try {
            
            String msg = txtMsg.getText();
            String[] podaci = msg.split(";");
           
            int idKor = Integer.parseInt(podaci[0]);
            int idA = Integer.parseInt(podaci[1]);
            double popust = Double.parseDouble(podaci[2]);
            
            List<Prodaje> prodajeList = em.createQuery("SELECT p FROM Prodaje p WHERE p.idProdavac.idKorisnik = :idKor and p.idArtikal.idArtikal = :idA", Prodaje.class).
                    setParameter("idKor", idKor).
                    setParameter("idA", idA).getResultList();
            if(prodajeList == null || prodajeList.isEmpty()){
                return "Prodavac ne prodaje artikal.";
            }
            Prodaje prodaje = prodajeList.get(0);
            prodaje.setPopust(popust);
            em.getTransaction().begin();
            em.persist(prodaje);
            em.getTransaction().commit();
            ret = "Popust je uspesno promenjen.";
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ret = "Doslo je do greske u prenosu.";
        } catch(NumberFormatException nfe){
            ret = "Losi parametri.";
        }finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
                ret = "Transakcija nije izvrsena.";
            }
                
        }
        return ret;
    }
    
    private static String function9(TextMessage txtMsg, EntityManager em){
  
        String ret = "";
        try {
                        
            String msg = txtMsg.getText();
            String[] podaci = msg.split(";");
           
            String username = podaci[0];
            int idA = Integer.parseInt(podaci[1]);
            String prodavacUsername = podaci[2];
            int kolicina = Integer.parseInt(podaci[3]);
            
            Korisnik korisnik = proveraKorisnikPostoji(username, em);
            if(korisnik == null){
                return "Pogresan username";
            }
            // provera artikal
            Artikal artikal = em.find(Artikal.class, idA);
            if(artikal == null){
                return "Artikal ne postoji.";
            }
            
            Korisnik prodavac = proveraKorisnikPostoji(prodavacUsername, em);
            if(prodavac == null){
                return "Prodavac ne postoji.";
            }
            
            List<Prodaje> prodajeList = em.createQuery("SELECT p FROM Prodaje p WHERE p.idProdavac.idKorisnik = :idKor and p.idArtikal.idArtikal = :idA", Prodaje.class).
                    setParameter("idKor", prodavac.getIdKorisnik()).
                    setParameter("idA", idA).getResultList();
            if(prodajeList == null || prodajeList.isEmpty()){
                return "Prodavac ne prodaje artikal.";
            }
            Prodaje prodaje = prodajeList.get(0);
            
            // trazimo korpu za korisnika
            Korpa korpa = proveraKorpaPostoji(korisnik, em);
            if(korpa == null){
                korpa = new Korpa();
                korpa.setIdKorisnika(korisnik);
                korpa.setUkupncaCena(0);
                
                em.getTransaction().begin();
                em.persist(korpa);
                em.flush();
                em.getTransaction().commit();
            }
           
            UKorpi uKorpi = proveraUKorpaPostoji(korpa, prodaje, em);
            if(uKorpi != null){
                uKorpi.setKolicina(uKorpi.getKolicina() + kolicina);
            }else{
                uKorpi = new UKorpi();
                uKorpi.setKolicina(kolicina);
                UKorpiPK pk = new UKorpiPK(korpa.getIdKorpa(), prodaje.getIdProdaje());
                uKorpi.setUKorpiPK(pk);
            }
            

            
            em.getTransaction().begin();
            em.persist(uKorpi);
            em.flush();
            em.getTransaction().commit();
            
            // izracuvanje cene
            double ukupnaCena = prodaje.getCena()*kolicina;
            korpa.setUkupncaCena(korpa.getUkupncaCena() + ukupnaCena);
            
            em.getTransaction().begin();
            em.persist(korpa);
            em.flush();
            em.getTransaction().commit();
            
            ret = "Artikal je dodat uspesno u korpu.";
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ret = "Doslo je do greske u prenosu.";
        } catch(NumberFormatException nfe){
            ret = "Losi parametri.";
        }finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
                ret = "Transakcija nije izvrsena.";
            }
                
        }
        return ret;
    }
    
    private static String function10(TextMessage txtMsg, EntityManager em){
  
        String ret = "";
        try {
            
            String msg = txtMsg.getText();
            String[] podaci = msg.split(";");
           
            String username = podaci[0];
            int idA = Integer.parseInt(podaci[1]);
            String prodavacUsername = podaci[2];
            int kolicina = Integer.parseInt(podaci[3]);
            
            Korisnik korisnik = proveraKorisnikPostoji(username, em);
            if(korisnik == null){
                return "Pogresan username";
            }
            // provera artikal
            Artikal artikal = em.find(Artikal.class, idA);
            if(artikal == null){
                return "Artikal ne postoji.";
            }
            
            Korisnik prodavac = proveraKorisnikPostoji(prodavacUsername, em);
            if(prodavac == null){
                return "Prodavac ne postoji.";
            }
            
            List<Prodaje> pp = em.createNamedQuery("Prodaje.findAll", Prodaje.class).getResultList();
            for (Prodaje prodaje : pp) {
                System.out.println(prodaje.getIdProdavac().getUsername() + " " + prodaje.getIdArtikal().getNaziv());
            }
            List<Prodaje> prodajeList = em.createQuery("SELECT p FROM Prodaje p WHERE p.idProdavac.idKorisnik = :idKor and p.idArtikal.idArtikal = :idA", Prodaje.class).
                    setParameter("idKor", prodavac.getIdKorisnik()).
                    setParameter("idA", idA).getResultList();
            if(prodajeList == null || prodajeList.isEmpty()){
                return "Prodavac ne prodaje artikal.";
            }
            Prodaje prodaje = prodajeList.get(0);
            
            // trazimo korpu za korisnika
            Korpa korpa = proveraKorpaPostoji(korisnik, em);
            if(korpa == null){
                return "Kupac nema korpu.";
            }
            
                      
            UKorpi uKorpi = proveraUKorpaPostoji(korpa, prodaje, em);
            if(uKorpi == null){
                return "Korisnik nema u korpi artikal.";
            }
            if(uKorpi.getKolicina() <= kolicina){
                em.getTransaction().begin();
                em.remove(uKorpi);
                em.flush();
                em.getTransaction().commit();
            }else{
                int oldKol = uKorpi.getKolicina();
                int newKol = (oldKol - kolicina) < 0 ? 0 : oldKol - kolicina;
                uKorpi.setKolicina(newKol);
                em.getTransaction().begin();
                em.persist(uKorpi);
                em.flush();
                em.getTransaction().commit();
            }

            
            // izracuvanje cene
            double ukupnaCena = prodaje.getCena()*kolicina;
            korpa.setUkupncaCena(korpa.getUkupncaCena() - ukupnaCena);
            
            em.getTransaction().begin();
            em.persist(korpa);
            em.flush();
            em.getTransaction().commit();
            
            ret = "Artikal je obrisan uspesno iz korpe.";
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ret = "Doslo je do greske u prenosu.";
        } catch(NumberFormatException nfe){
            ret = "Losi parametri.";
        }finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
                ret = "Transakcija nije izvrsena.";
            }
                
        }
        return ret;
    }
    
    private static KategorijeResponse function14(EntityManager em){
 
        List<KategorijaResponse> retList = new ArrayList<>();
        try {
            List<Kategorija> kategorije = em.createNamedQuery("Kategorija.findAll", Kategorija.class).getResultList();
            
            for (Kategorija kategorija : kategorije) {
                KategorijaResponse gr = new KategorijaResponse(kategorija.getIdKat(), kategorija.getNaziv());
                retList.add(gr);
            }
            
        } finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();               
            }
                
        }
        KategorijeResponse response = new KategorijeResponse(retList);
        
        return response;
    }
    
    private static ArtikliResponse function15(TextMessage txtMsg, EntityManager em){
       
        List<ArtikalResponse> retList = new ArrayList<>();
        try {
            String msg = txtMsg.getText();
            
            // provera korisnik
            Korisnik korisnik = proveraKorisnikPostoji(msg, em);
            if(korisnik == null){
                return (new ArtikliResponse(retList));
            }
            
            List<Prodaje> prodajes = korisnik.getProdajeList();
            
            for (Prodaje prodaje : prodajes) {
                Artikal artikal = prodaje.getIdArtikal();
                ArtikalResponse ar = new ArtikalResponse(artikal.getIdArtikal(), artikal.getNaziv(), prodaje.getCena());
                retList.add(ar);
            }
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return (new ArtikliResponse(retList));
        } finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();               
            }
                
        }
        ArtikliResponse response = new ArtikliResponse(retList);
        
        return response;
    }
    
    private static ArtikliResponse function16(TextMessage txtMsg, EntityManager em){
        List<ArtikalResponse> retList = new ArrayList<>();
        try {
            String msg = txtMsg.getText();
            
            // provera korisnik
            Korisnik korisnik = proveraKorisnikPostoji(msg, em);
            if(korisnik == null){
                return (new ArtikliResponse(retList));
            }
            Korpa korpa = korisnik.getKorpa();
            
            List<UKorpi> uKorpis = em.createNamedQuery("UKorpi.findByIdKorpa", UKorpi.class).setParameter("idKorpa", korpa.getIdKorpa()).getResultList();
            
            for (UKorpi uKorpi : uKorpis) {
                Prodaje prodaje = em.find(Prodaje.class, uKorpi.getUKorpiPK().getIdProdaja());
                Artikal artikal = prodaje.getIdArtikal();
                
                ArtikalResponse ar = new ArtikalResponse(artikal.getIdArtikal(), artikal.getNaziv(), prodaje.getCena());
                retList.add(ar);
            }
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return (new ArtikliResponse(retList));
        } finally {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();               
            }
                
        }
        ArtikliResponse response = new ArtikliResponse(retList);
        
        return response;
    }
    
    private static String createUser(TextMessage txtMsg, EntityManager em){
        String ret = "";
        try {
           
            String username = txtMsg.getText();
                                
            // provera korisnik 
            Korisnik korisnik = proveraKorisnikPostoji(username, em);
            if(korisnik != null){
                return "Korisnik sa datim username postoji.";
            }
            
            korisnik = new Korisnik();
            korisnik.setUsername(username);

          
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

    private static Korisnik proveraKorisnikPostoji(String username, EntityManager em) {
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByUsername", Korisnik.class).
                    setParameter("username", username).
                    getResultList();
            
            if(korisnici.size() != 1){
                return null;
            }
            return korisnici.get(0);       
       
    }

    private static Korpa proveraKorpaPostoji(Korisnik korisnik, EntityManager em){
        List<Korpa> korpe = em.createQuery("SELECT k FROM Korpa k WHERE k.idKorisnika.idKorisnik = :idKorisnik", Korpa.class).
                    setParameter("idKorisnik", korisnik.getIdKorisnik()).
                    getResultList();
            
            if(korpe.size() != 1){
                return null;
            }
            return korpe.get(0);   
    }
    
    private static UKorpi proveraUKorpaPostoji(Korpa korpa, Prodaje prodaje,EntityManager em){
        List<UKorpi> korpe = em.createQuery("SELECT k FROM UKorpi k WHERE k.uKorpiPK.idKorpa = :idKorpa and k.uKorpiPK.idProdaja = :idProdaja", UKorpi.class).
                    setParameter("idKorpa", korpa.getIdKorpa()).
                    setParameter("idProdaja", prodaje.getIdProdaje()).
                    getResultList();
            
            if(korpe.size() != 1){
                return null;
            }
            return korpe.get(0);   
    }

    private static String brisanjeIzKorpe(TextMessage txtMsg, EntityManager em) {
        String ret = "";
        try {
           
            String username = txtMsg.getText();
                                
            // provera korisnik 
            Korisnik korisnik = proveraKorisnikPostoji(username, em);
            
            if(korisnik == null){
                return "Korisnik sa datim username nepostoji.";
            }
            
            Korpa korpa = korisnik.getKorpa();
            List<UKorpi> uKorpis = em.createNamedQuery("UKorpi.findByIdKorpa", UKorpi.class).setParameter("idKorpa", korpa.getIdKorpa()).getResultList();
            for (UKorpi uKorpi : uKorpis) {
                em.getTransaction().begin();
                em.remove(uKorpi);
                em.getTransaction().commit();
            }
            
            korpa.setUkupncaCena(0);
            em.getTransaction().begin();
            em.persist(korpa);
            em.getTransaction().commit();

            ret = "Uspesno je obrisano sve iz korpe.";
            
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
    
    
}
