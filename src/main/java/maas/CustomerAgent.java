package maas;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


//import maas.BookBuyerAgent.shutdown;
public class CustomerAgent extends Agent {
	
	private AID bakeryAgent = new AID("bakery", AID.ISLOCALNAME);
	private MessageTemplate mt;
	
	private ArrayList<String> OrderCreate(){
		
		
		ArrayList<String> orderList = new ArrayList<String>();
		orderList.add(getAID().getName());
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the order date <dd.hh>:");
		orderList.add(scanner.nextLine());
		System.out.println("Enter the order date <dd.hh>:");
		orderList.add(scanner.nextLine());
		System.out.println("Enter the vector over the range of products:");
		orderList.add(scanner.nextLine());
		scanner.close();
		
		return orderList;
	}
	
	protected void setup(){
		System.out.println("Customer-Agent "+getAID().getName()+"is ready");
		addBehaviour(new PlaceandWaitOrder());
		// addBehaviour(new AcknowledgeOrder());
	}
	
	
	// behaviours 

	private class PlaceandWaitOrder extends OneShotBehaviour{
		
		public void action() {
			//ToDo handle
			ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
			cfp.addReceiver(bakeryAgent);
			cfp.setContent(String.valueOf(OrderCreate()));
			cfp.setConversationId("order");
			cfp.setReplyWith("cfp"+System.currentTimeMillis());
			send(cfp);
			mt = MessageTemplate.and(MessageTemplate.MatchConversationId("order-reply"),
					MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
			addBehaviour(new AcknowledgeOrder());
			
		}
	}
	
	private class AcknowledgeOrder extends OneShotBehaviour{
		public void action() {
			//ToDo handle
			ACLMessage reply = myAgent.receive(mt);
            
            if (reply.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                //Wait for 
            	System.out.println("succeeded");
                doDelete();
            } else {
                // Retry
                System.out.println("failed");
                doDelete();
            }
           
		}
	}

}