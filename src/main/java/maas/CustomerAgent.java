package maas;

import java.util.*;

import maas.Parser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import org.json.simple.*;


//import maas.BookBuyerAgent.shutdown;
public class CustomerAgent extends Agent {
	
//	private AID bakeryAgent = new AID("bakery", AID.ISLOCALNAME);
	private MessageTemplate mt;
	private Parser p = new Parser();
	private JSONArray bakeries = p.jsonBakeries;
	private JSONArray globalOrders = p.jsonOrders;
	private JSONArray sortedLocalOrders = new JSONArray();
	private JSONObject curOrder;
	
	
	protected void setup(){
		System.out.println("Customer-Agent "+getAID().getName()+"is ready");
		sortedLocalOrders = p.GetSortedLocalOrders(getAID().getLocalName());
		
//		System.out.println(getAID().getLocalName());
		System.out.println(sortedLocalOrders);
		
		addBehaviour(new PlaceOrder());
	}

	private class PlaceOrder extends OneShotBehaviour{
		
		public void action() {
			
			
			
			//ToDo handle
//			ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
//			cfp.addReceiver(bakeryAgent);
//			cfp.setContent(String.valueOf(OrderCreate()));
//			cfp.setConversationId("order");
//			cfp.setReplyWith("cfp"+System.currentTimeMillis());
//			send(cfp);
//			mt = MessageTemplate.and(MessageTemplate.MatchConversationId("order-reply"),
//					MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
//			addBehaviour(new AcknowledgeOrder());
			
		}
	}
	
	private class AcknowledgeOrder extends CyclicBehaviour{
		public void action() {
			//ToDo handle
			ACLMessage reply = receive(mt);
            if (reply!=null){
	            if (reply.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
	            	System.out.println("succeeded");
	                doDelete();
	            } else {
	                System.out.println("failed");
	                doDelete();
	            }
            } else {
            	block();
            }
           
		}
	}

}