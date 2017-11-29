package maas;

import java.util.ArrayList;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.ShutdownPlatform;
import jade.lang.acl.MessageTemplate;
//import maas.BookBuyerAgent.shutdown;

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
							
					}
					else {
		                System.out.println("No message Recieved from Customer");
		                doDelete();
		                
		            }
					break;
			
			case 1: // recive the reply from the bakerymanger if yes then reply yes to customer else no!
				System.out.println("------message Recieved and i am in step 2-----\n");
				ACLMessage Mngr_reply = receive(mt);
				if (Mngr_reply!=null){
					 System.out.println( " - " +
		                       myAgent.getLocalName() + " <- " +
		                       Mngr_reply.getContent() );
		                       
						ACLMessage replyToCustomer = Mngr_reply.createReply();
						if (Mngr_reply.getContent()=="YES"){
							Mngr_reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
						}
						else {
							Mngr_reply.setPerformative(ACLMessage.CANCEL);
						}
						//ACLMessage msg_req = new ACLMessage(ACLMessage.INFORM);
						//msg_req.addReceiver(new AID("BakeryManger",AID.ISLOCALNAME));
						//msg_req.setContent(order);
						send(Mngr_reply);
						step=2;
						
				}
				else {
	                System.out.println("No message Recieved from Bakery Manager");
	                doDelete();
	                
	            }
					break;
			case 2:
					//ACLMessage rly = msg.createReply();
				System.out.println(" order has been accepted\n");
					bakeryOrderList.add(order_info);
					doDelete();
					//addBehaviour(new shutdown());
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
	
	private class shutdown extends OneShotBehaviour{
		public void action() {
			ACLMessage shutdownMessage = new ACLMessage(ACLMessage.REQUEST);
			Codec codec = new SLCodec();
			myAgent.getContentManager().registerLanguage(codec);
			myAgent.getContentManager().registerOntology(JADEManagementOntology.getInstance());
			shutdownMessage.addReceiver(myAgent.getAMS());
			shutdownMessage.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
			shutdownMessage.setOntology(JADEManagementOntology.getInstance().getName());
			try {
			    myAgent.getContentManager().fillContent(shutdownMessage,new Action(myAgent.getAID(), new ShutdownPlatform()));
			    myAgent.send(shutdownMessage);
			}
			catch (Exception e) {
			    //LOGGER.error(e);
			}

		}
	}

}