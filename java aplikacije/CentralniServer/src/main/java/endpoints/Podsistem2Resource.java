/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import response.*;


/**
 *
 * @author Bogdan
 */
@Path("podsistem2")
@Stateless
public class Podsistem2Resource {
    
    @Resource(lookup="__myConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup="myQueuePodsistem2")
    private Queue myQueue;
    
    @Resource(lookup="podsistem3ResoruceQ")
    private Queue p3rq;
    
    
    // function kreiranje prodaje
    @POST
    @Path("prodaje/add/{idA}/{idProdavac}/{cena}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response createProdaje(
            @PathParam("idA") int idA,
            @PathParam("idProdavac") int idProdavac,
            @PathParam("cena") double cena
    )
    {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
            
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            
            String msg = idA + ";" + idProdavac + ";" + cena;
           
            textMsg.setText(msg);
            
            textMsg.setIntProperty("function", 200);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            TextMessage status = (TextMessage)consumer.receive();
            String ret = status.getText();
            
            return Response.status(200).entity(ret).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();        
        
    }

    
    // function 5
    @POST
    @Path("kategorija/{naziv}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response createCategory(
            @PathParam("naziv") String naziv,
            @QueryParam("idNadKat") Integer idNadKat
    ){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
        try {

            
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            String msg = naziv + ";";
            if(idNadKat != null)
                msg += idNadKat;
            else
                msg += -1;
            
            textMsg.setText(msg);
            
            textMsg.setIntProperty("function", 5);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            TextMessage status = (TextMessage)consumer.receive();
            String ret = status.getText();
            
            return Response.status(200).entity(ret).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();
    }  
   
    // function 6
    @POST
    @Path("artikal/{naziv}/{idKat}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response createArtikal(
            @PathParam("naziv") String naziv,
            @PathParam("idKat") int idKat,
            @QueryParam("opis") String opis
    )
    {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
            
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            String msg = naziv + ";" + idKat + ";";
            
            if(opis == null || opis.length() == 0){
                msg += "NULL";
            }else{
                msg += opis;
            }
            
            textMsg.setText(msg);
            
            textMsg.setIntProperty("function", 6);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            TextMessage status = (TextMessage)consumer.receive();
            String ret = status.getText();
            
            return Response.status(200).entity(ret).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();
    }
    
    // function 7
    @POST
    @Path("artikal/cena/{idKor}/{idA}/{cena}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response changePriceArtikal(
            @PathParam("idK") int idKor,
            @PathParam("idA") int idA,
            @PathParam("cena") double cena
    ){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
            
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            
            String msg = idKor + ";" + idA + ";" + cena;
           
            textMsg.setText(msg);
            
            textMsg.setIntProperty("function", 7);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            TextMessage status = (TextMessage)consumer.receive();
            String ret = status.getText();
            
            return Response.status(200).entity(ret).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();        
        
    }
    
    
     // function 8
    @POST
    @Path("artikal/popust/{idKor}/{idA}/{cena}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response changePopustArtikal(
            @PathParam("idK") int idKor,
            @PathParam("idA") int idA,
            @PathParam("cena") double popust
    ){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
            
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            
            String msg = idKor + ";" + idA + ";" + popust;
           
            textMsg.setText(msg);
            
            textMsg.setIntProperty("function", 7);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            TextMessage status = (TextMessage)consumer.receive();
            String ret = status.getText();
            
            return Response.status(200).entity(ret).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();        
        
    }

     // function 9
    @POST
    @Path("korpa/add/{username}/{idA}/{usernameProdavac}/{kolicina}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response addArtikalKorpa(
            @PathParam("username") String username,
            @PathParam("idA") int idA,
            @PathParam("usernameProdavac") String usernameProdavac,
            @PathParam("kolicina") int kolicina 
    )    
    {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
            
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            String msg = username + ";" + idA + ";" + usernameProdavac + ";" + kolicina;
            textMsg.setText(msg);
            
            textMsg.setIntProperty("function", 9);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            TextMessage status = (TextMessage)consumer.receive();
            String ret = status.getText();
            
            return Response.status(200).entity(ret).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();        
    }
    
     // function 10
    @POST
    @Path("korpa/delete/{username}/{idA}/{usernameProdavac}/{kolicina}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response deleteArtikalKorpa(
            @PathParam("username") String username,
            @PathParam("idA") int idA,
            @PathParam("usernameProdavac") String usernameProdavac,
            @PathParam("kolicina") int kolicina 
    )    
    {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
            
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            String msg = username + ";" + idA + ";" + usernameProdavac + ";" + kolicina;
            textMsg.setText(msg);
            
            textMsg.setIntProperty("function", 10);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            TextMessage status = (TextMessage)consumer.receive();
            String ret = status.getText();
            
            return Response.status(200).entity(ret).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();        
    }    
    
    // function 14
    @GET
    @Path("kategorija")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response getCategor(){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
            
            
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            
            textMsg.setText("korisnici");
            textMsg.setIntProperty("function", 14);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            
            Message response = consumer.receive();
            
            if(!(response instanceof ObjectMessage)){
                return Response.status(500).entity("Nije proslo").build();
            }
            
            KategorijeResponse kategorijeResponse = (KategorijeResponse)((ObjectMessage)response).getObject();
            
            StringBuilder ret = new StringBuilder();
            for(KategorijaResponse kategorija : kategorijeResponse.getKategorije()){
                StringBuilder s = new StringBuilder(kategorija.toString() + "\n");
                ret = ret.append(s);
            }
            
            return Response.status(200).entity(ret.toString()).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();
    }
    
    // function 15
    @GET
    @Path("prodaje/{username}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response getProdaje(@PathParam("username") String username){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
            
            
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            
            textMsg.setText(username);
            textMsg.setIntProperty("function", 15);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            
            Message response = consumer.receive();
            
            if(!(response instanceof ObjectMessage)){
                return Response.status(500).entity("Nije proslo").build();
            }
            
            ArtikliResponse artikli = (ArtikliResponse)((ObjectMessage)response).getObject();
            
            StringBuilder ret = new StringBuilder("Prodaje:\n");
            for(ArtikalResponse artikal : artikli.getArtikli()){
                StringBuilder s = new StringBuilder(artikal.toString() + "\n");
                ret = ret.append(s);
            }
            
            return Response.status(200).entity(ret.toString()).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();
    }
    
    // function 16
    @GET
    @Path("ukorpi/{username}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response getUkorpi(@PathParam("username") String username){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
            
            
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            
            textMsg.setText(username);
            textMsg.setIntProperty("function", 16);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            
            Message response = consumer.receive();
            
            if(!(response instanceof ObjectMessage)){
                return Response.status(500).entity("Nije proslo").build();
            }
            
            ArtikliResponse artikli = (ArtikliResponse)((ObjectMessage)response).getObject();
            
            StringBuilder ret = new StringBuilder("U korpi je\n");
            for(ArtikalResponse artikal : artikli.getArtikli()){
                StringBuilder s = new StringBuilder(artikal.toString() + "\n");
                ret = ret.append(s);
            }
            
            return Response.status(200).entity(ret.toString()).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();
    }
    
    
    
}
