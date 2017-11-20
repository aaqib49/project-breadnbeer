package maas;

import java.util.ArrayList;

import jade.content.lang.Codec;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.MessageTemplate;

public class BakeryAgent extends Agent {
	
	private MessageTemplate mt;
	private ArrayList<String> bakeryOrderList = new ArrayList<String>();
	private boolean isAccepted = true;
	
	protected void setup() {
	// Printout a welcome message
		System.out.println("Hello! Bakery-agent "+getAID().getName()+" is ready.");
		addBehaviour(new ReceiveOrder());

	}
	protected void takeDown() {
		System.out.println(getAID().getLocalName() + ": Terminating.");
	}


// behaviours 

	private class ReceiveOrder extends OneShotBehaviour{
		public void action() {
			//ToDo handle
			MessageTemplate.and(MessageTemplate.MatchConversationId("order-reply"),
					MessageTemplate.MatchPerformative(ACLMessage.CFP));
			ACLMessage msg = receive(mt);
			String order = msg.getContent();
			ACLMessage rly = msg.createReply();
			bakeryOrderList.add(order);
			if (isAccepted){
				rly.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
			} else {
				rly.setPerformative(ACLMessage.REJECT_PROPOSAL);
			}
			
			
		
		}			
	}

	private class PassOrder2BakeryMngr extends OneShotBehaviour{
		public void action() {
		//ToDo handle
		
		}
	}

}