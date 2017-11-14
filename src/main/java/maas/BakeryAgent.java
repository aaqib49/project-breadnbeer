package maas;

import jade.content.lang.Codec;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
//import maas.BookBuyerAgent.shutdown;

public class BakeryAgent extends Agent {
	protected void setup() {
	// Printout a welcome message
		System.out.println("Hello! --- Welcome <3---  Bakery-agent "+getAID().getName()+" is ready.");

        try {
 			Thread.sleep(3000);
 		} catch (InterruptedException e) {
 			//e.printStackTrace();
 		}
		//addBehaviour(new ReciveOrder());

	}
	protected void takeDown() {
		System.out.println(getAID().getLocalName() + ": Terminating.");
	}


// behaviours 

	private class ReciveOrder extends OneShotBehaviour{
		public void action() {
		//ToDo handle
		
	}
	}

	private class PassOrder2BakeryMngr extends OneShotBehaviour{
		public void action() {
		//ToDo handle
		
	}
	}

}