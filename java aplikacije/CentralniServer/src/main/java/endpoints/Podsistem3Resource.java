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
import javax.ws.rs.core.Response;
import response.*;


/**
 *
 * @author Bogdan
 */

@Path("podsistem3")
@Stateless
public class Podsistem3Resource {
    @Resource(lookup="__myConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup="myQueuePodsistem2") // Queue Podsistem3
    private Queue myQueue2;
        
    @Resource(lookup="myQueuePodsistem3") // Queue Podsistem3
    private Queue myQueue;
    
    @Resource(lookup="podsistem3ResoruceQ")
    private Queue p3rq;
    
    // function 17
    @GET
    @Path("narudzbine/{username}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response getAllNarudzbine(@PathParam("username") String username){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
            
            
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            
            textMsg.setText(username);
            textMsg.setIntProperty("function", 200);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            
            Message response = consumer.receive();
            
            if(!(response instanceof ObjectMessage)){
                return Response.status(500).entity("Nije proslo").build();
            }
            
            NarudzbineResponse narudzbine = (NarudzbineResponse)((ObjectMessage)response).getObject();
            
            StringBuilder ret = new StringBuilder("Narudzbine:\n");
            for(NarudzbinaResponse artikal : narudzbine.getNarudzbine()){
                StringBuilder s = new StringBuilder(artikal.toString() + "\n");
                ret = ret.append(s);
            }
            consumer.close();
            return Response.status(200).entity(ret.toString()).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();
        
    }
    
    // function 18
    @GET
    @Path("narudzbine")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response getAllNarudzbine(){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
            
            
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            
            textMsg.setText("narudzbine");
            textMsg.setIntProperty("function", 18);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            
            Message response = consumer.receive();
            
            if(!(response instanceof ObjectMessage)){
                return Response.status(500).entity("Nije proslo").build();
            }
            
            NarudzbineResponse narudzbine = (NarudzbineResponse)((ObjectMessage)response).getObject();
            
            StringBuilder ret = new StringBuilder("Narudzbine:\n");
            for(NarudzbinaResponse artikal : narudzbine.getNarudzbine()){
                StringBuilder s = new StringBuilder(artikal.toString() + "\n");
                ret = ret.append(s);
            }
            consumer.close();
            return Response.status(200).entity(ret.toString()).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();
        
    }
    
    // function 19
    @GET
    @Path("transakcije")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response getAllTranskacije(){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server
                        
            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            
            textMsg.setText("transkacije");
            textMsg.setIntProperty("function", 19);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue, textMsg);
            
            // prijem odgovora
            
            Message response = consumer.receive();
            
            if(!(response instanceof ObjectMessage)){
                return Response.status(500).entity("Nije proslo").build();
            }
            
            TransakcijeResponse transakcije = (TransakcijeResponse)((ObjectMessage)response).getObject();
            
            StringBuilder ret = new StringBuilder("Transkacije:\n");
            for(TransakcijaResponse trans : transakcije.getTranskacije()){
                StringBuilder s = new StringBuilder(trans.toString() + "\n");
                ret = ret.append(s);
            }
            
            consumer.close();
            return Response.status(200).entity(ret.toString()).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();       
    }
    
    // function 11
    @POST
    @Path("placanje/{username}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response plati(@PathParam("username") String username){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            
            JMSConsumer consumer = context.createConsumer(myQueue, "Type = '0'"); // 0 server komunikacija sa podsistemom3
            JMSConsumer consumer2 = context.createConsumer(p3rq, "Type = '3'"); // 0 server podsistem2
            JMSConsumer consumer3 = context.createConsumer(p3rq, "Type = '5'"); // 0 server podsistem2

            // kreiranje poruke za slanje 
            TextMessage textMsg = context.createTextMessage();
            
            textMsg.setText(username);
            textMsg.setIntProperty("function", 100);
            textMsg.setStringProperty("Type", "1");
            
            // slanje poruke
            producer.send(myQueue2, textMsg);
            
            // prijem odgovora            
            Message response = consumer2.receive();
            
            if(!(response instanceof ObjectMessage)){
                return Response.status(500).entity("Nije proslo").build();
            }
            
            StavkeResponse stavke = (StavkeResponse)((ObjectMessage)response).getObject();
            
            // saljem podsistemu 3
            ObjectMessage objMsg = context.createObjectMessage(stavke);
            objMsg.setStringProperty("username", username);
            objMsg.setStringProperty("Type", "1");
            objMsg.setIntProperty("function", 11);
            producer.send(myQueue, objMsg);
            
            textMsg = (TextMessage)consumer.receive();
            String ret = textMsg.getText();
            
            if(ret.equals("SUC")){
                textMsg = context.createTextMessage();
                textMsg.setText(username);
                textMsg.setIntProperty("function", 101);
                textMsg.setStringProperty("Type", "1");
                producer.send(myQueue2, textMsg);
                
                TextMessage txtMessage = (TextMessage)consumer3.receive();
                ret = txtMessage.getText();
                
            }
            consumer.close();
            consumer2.close();
            consumer3.close();
            return Response.status(200).entity(ret).build();

        } catch (JMSException ex) {
            
            //Logger.getLogger(GradResource.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        
        return Response.status(500).entity("Nije proslo").build();       
    }
    
    
}
