package maas;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.ShutdownPlatform;
import jade.lang.acl.ACLMessage;
import java.util.List;
import java.util.Vector;


@SuppressWarnings("serial")
public class BakeryMangerAgent extends Agent {
	protected void setup() {
	// Printout a welcome message
		System.out.println("Hello! BakeryManger-agent "+getAID().getName()+" is ready.");
		
		List<String> agents = new Vector<>();
		
		
    	//agents.add("bakery:maas.BakeryAgent");
    	List<String> cmd = new Vector<>();
    	cmd.add("-agents");
    	StringBuilder sb = new StringBuilder();
    	for (String a : agents) {
    		sb.append(a);
    		sb.append(";");
    	}
    	cmd.add(sb.toString());
        jade.Boot.main(cmd.toArray(new String[cmd.size()]));
 
    	
    	addBehaviour(new status());

	}
	protected void takeDown() {
		System.out.println(getAID().getLocalName() + ": Terminating.");
	}
	
	// behaviours
	
	// Status should be continuous as it regularly or 
	//we can do it one shot but manager needs to call again nagain.
	private class status extends Behaviour{
		public void action() {
			;
		}
		
		public boolean done(){
			return(false); 
		}
		}
	
	
	private class Bake extends OneShotBehaviour{
		public void action() {
			;
		}
		}
		
	private class update extends UpdateManager{
		// can be implemented or can be done with status cyclic
			}	
}