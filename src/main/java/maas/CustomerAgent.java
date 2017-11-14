package maas;
//package maas;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
//import maas.BookBuyerAgent.shutdown;
public class CustomerAgent extends Agent {
	
	protected void setup(){
		System.out.println("hello Bakery "+getAID().getName()+"is ready");
		
		 try {
	 			Thread.sleep(3000);
	 		} catch (InterruptedException e) {
	 			//e.printStackTrace();
	 		}
		 
		// addBehaviour(new PlaceOrder());
		// addBehaviour(new AcknowledgeOrder());
	}
	
	
	// behaviours 

	private class PlaceOrder extends OneShotBehaviour{
		public void action() {
			//ToDo handle
			
		}
		}
	
	private class AcknowledgeOrder extends OneShotBehaviour{
		public void action() {
			//ToDo handle
			
		}
		}

}