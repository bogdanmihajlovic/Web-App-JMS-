/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints;

import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import response.GradResponse;
import response.GradoviResponse;
import response.KorisniciResponse;
import response.KorisnikResponse;

/**
 *
 * @author Bogdan
 */
@Path("podsistem1")
@Stateless
public class Podsistem1Resource {
    
    @Resource(lookup="__myConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup="myQueue2") // Queue Podsistem1
    private Queue myQueue;
    
    @Resource(lookup="myQueuePodsistem2") // Queue Podsistem2
    private Queue myQueue2;
    
    @Resource(lookup = "myQueuePodsistem3") // Queue Podsistem3
    private Queue myQueue3;
    
    @Resource(lookup="podsistem3ResoruceQ")
    private Queue p3rq;
        
    @GET
    @Path("cleanQueue")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response clearQ(){
            JMSContext context = connectionFactory.createContext();
            
            JMSConsumer consumer = context.createConsumer(myQueue); // 0 server                       
            JMSConsumer consumer2 = context.createConsumer(myQueue2); // 2 server                        
            JMSConsumer consumer3 = context.createConsumer(myQueue3); // 2 server
            JMSConsumer consumer4 = context.createConsumer(p3rq); // 2 server
            

            
            
            for(int i = 0; i < 200;i++){
                consumer.receiveNoWait();
                consumer3.receiveNoWait();
                consumer4.receiveNoWait();
                consumer2.receiveNoWait();
      

            }
            consumer.close();
            consumer2.close();
            consumer3.close();
            return Response.ok().entity("Redovi su ocisceni.").build();
    }
    // function 1
    @POST
    @Path("grad/{naziv}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response createCity(@PathParam("naziv") String naziv){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
            
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            textMsg.setText(naziv);
            
            textMsg.setIntProperty("function", 1);
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
    
    // function 2
    @POST
    @Path("korisnik/{username}/{password}/{imePrezime}/{adresa}/{idG}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response createUser(
            @PathParam("username") String username,
            @PathParam("password") String password,
            @PathParam("imePrezime") String imePrezime,
            @PathParam("adresa") String adresa,
            @PathParam("idG") int idG 
    ) 
    {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
                     
            JMSConsumer consumer2 = context.createConsumer(myQueue2, "Type = '2'"); // 2 server
                        
            JMSConsumer consumer3 = context.createConsumer(myQueue3, "Type = '2'"); // 2 server

            
            // podsistem1
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            String msg = username + ";" + password + ";" + imePrezime + ";" + adresa + ";" + idG;
            
            textMsg.setText(msg);
            textMsg.setIntProperty("function", 2);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            TextMessage status = (TextMessage)consumer.receive();
            String ret = status.getText();
            
            // podsistem2
            //kreiranje poruke za slanje 
            textMsg = context.createTextMessage();
            msg = username;
            
            textMsg.setText(msg);
            textMsg.setIntProperty("function", 20);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue2, textMsg); // salje podsistemu2
            status = (TextMessage)consumer2.receive();
            
            ret = ret + " podsistem1\n" + status.getText() + " podsistem2";
            
            // podsistem3
            //kreiranje poruke za slanje 
            textMsg = context.createTextMessage();
            
            msg = username + ";" + "Beograd;" + adresa;
            
            textMsg.setText(msg);
            textMsg.setIntProperty("function", 22);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue3, textMsg); // salje podsistemu3
            status = (TextMessage)consumer3.receive();
            ret += status.getText() + " podsistem3";
            
            consumer.close();
            consumer2.close();
            consumer3.close();
            return Response.status(200).entity(ret).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
               
        
        return Response.status(500).entity("Nije proslo").build();
    }
    
    // function 3
    @POST
    @Path("korisnik/{username}/{novac}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response addMoney(
            @PathParam("username") String username,
            @PathParam("novac") double novac
    )
    {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
            
            JMSConsumer consumer3 = context.createConsumer(myQueue3, "Type = '2'"); // 0 server
            
            // podsistem1
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            String msg = username + ";" + novac;
            textMsg.setText(msg);
            textMsg.setIntProperty("function", 3);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            TextMessage status = (TextMessage)consumer.receive();
            String ret = status.getText();
            
            // slanje podsistemu3
            textMsg = context.createTextMessage();
            
            msg = username + ";" + novac;
            textMsg.setText(msg);
            textMsg.setIntProperty("function", 3);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue3, textMsg);
            
            // prijem odgovora
            status = (TextMessage)consumer3.receive();
            ret = "podsistem1 " + ret + " podsistem3 " +status.getText();
            
            consumer.close();
            consumer3.close();
            return Response.status(200).entity(ret).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();
    }
    
    // function 4
    @POST
    @Path("korisnik/{username}/{adresa}/{idG}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response changeAddrCity(
            @PathParam("username") String username,
            @PathParam("adresa") String adresa,
            @PathParam("idG") int idG
    )
    {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
            
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            String msg = username + ";" + adresa + ";" + idG;
            textMsg.setText(msg);
            textMsg.setIntProperty("function", 4);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            TextMessage status = (TextMessage)consumer.receive();
            String ret = status.getText();
            consumer.close();
            return Response.status(200).entity(ret).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();
    }
    
    // function 12
    @GET
    @Path("gradovi")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response getCity(){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
            
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            
            textMsg.setText("gradovi");
            textMsg.setIntProperty("function", 12);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            
            Message response = consumer.receive();
            
            if(!(response instanceof ObjectMessage)){
                return Response.status(500).entity("Nije proslo").build();
            }
            GradoviResponse gradoviResponse = (GradoviResponse)((ObjectMessage)response).getObject();
            
            StringBuilder ret = new StringBuilder();
            for(GradResponse grad : gradoviResponse.getGradovi()){
                StringBuilder s = new StringBuilder(grad.toString() + "\n");
                ret = ret.append(s);
            }
            consumer.close();
            return Response.status(200).entity(ret.toString()).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();
    }
    
    // function 13
    @GET
    @Path("korisnici")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response getUsers(){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
            
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            
            textMsg.setText("korisnici");
            textMsg.setIntProperty("function", 13);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            
            Message response = consumer.receive();
            
            if(!(response instanceof ObjectMessage)){
                return Response.status(500).entity("Nije proslo").build();
            }
            KorisniciResponse korisniciResponse = (KorisniciResponse)((ObjectMessage)response).getObject();
            
            StringBuilder ret = new StringBuilder();
            for(KorisnikResponse korisnik : korisniciResponse.getKorisnici()){
                StringBuilder s = new StringBuilder(korisnik.toString() + "\n");
                ret = ret.append(s);
            }
            
            consumer.close();
            return Response.status(200).entity(ret.toString()).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();
    }
    
}
