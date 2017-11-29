package maas;

import java.util.ArrayList;

import jade.content.lang.Codec;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.MessageTemplate;

public class BakeryAgent extends Agent {
	
	private MessageTemplate mt;
	private ArrayList<String> bakeryOrderList = new ArrayList<String>();
	private boolean isAccepted = true;
	private String order_info;
	private int step=0;
	protected void setup() {
	// Printout a welcome message
		System.out.println("Hello! Bakery-agent "+getAID().getName()+" is ready.");
		addBehaviour(new ReceiveOrder());
		

	}
	protected void takeDown() {
		System.out.println(getAID().getLocalName() + ": Terminating.");
	}


// behaviours 

	// need to be cyclic behaviour
	private class ReceiveOrder extends CyclicBehaviour{
		public void action() {
			//ToDo handle
			
			switch (step){
			case 0: //send the order to the bakery manager and check 
					MessageTemplate.and(MessageTemplate.MatchConversationId("order_proposal"),
					MessageTemplate.MatchPerformative(ACLMessage.CFP));
				
					ACLMessage msg = receive(mt);
					if (msg!=null){
							order_info=	msg.getContent();		
							String order = msg.getContent();
							ACLMessage msg_req = new ACLMessage(ACLMessage.INFORM);
							msg_req.addReceiver(new AID("BakeryManger",AID.ISLOCALNAME));
							msg_req.setContent(order);
							send(msg);
							step=1;
							break;
							
					}
			
			case 1: // recive the reply from the bakerymanger if yes then reply yes to customer else no!
					break;
			case 2:
					//ACLMessage rly = msg.createReply();
					bakeryOrderList.add(order_info);
					break;
			}
/*			
			MessageTemplate.and(MessageTemplate.MatchConversationId("order_proposal-reply"),
					MessageTemplate.MatchPerformative(ACLMessage.CFP));
			ACLMessage msg = receive(mt);
			// checking for if the message is empty or not and waiting for it
			if (msg!=null){
			order_info=	msg.getContent();		
			String order = msg.getContent();
			ACLMessage rly = msg.createReply();
			bakeryOrderList.add(order);
			if (isAccepted){
				rly.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
			} else {
				rly.setPerformative(ACLMessage.REJECT_PROPOSAL);
			}
			}
			else {
				block();
			}
			
*/			
		
		}			
	}

	private class PassOrder2BakeryMngr extends OneShotBehaviour{
		public void action() {
		//ToDo handle
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(new AID("BakeryManger",AID.ISLOCALNAME));
			msg.setContent(order_info);
			//msg.setLanguage("English");
			//msg.setOntology("Weather-forecast-ontology");
			//msg.setContent("Today it’s raining");
			send(msg);
		
		}
	}

}