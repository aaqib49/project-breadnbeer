package maas;

import maas.Parser;

import java.util.List;
import java.util.Vector;

import org.json.simple.JSONObject;

public class Start {
    public static void main(String[] args) {
    	List<String> agents = new Vector<>();
    	Parser p = new Parser();
    	System.out.println("hello its in");
    	
    	agents.add("BakeryManger:maas.KneadingAgent");  // to start the bakery manager agent
    	
//Create bakery agents    	
    	for (Object c: p.jsonBakeries){
    		JSONObject bakeryObj = (JSONObject) c;
    		String name = (String) bakeryObj.get("guid") + ":maas.BakeryAgent";
    		String modName = name.replace(' ', '-');
    		agents.add(modName);
    	}    	
//Create customer agents
    	for (Object c: p.jsonCustomers){
    		JSONObject customerObj = (JSONObject) c;
    		String name = (String) customerObj.get("guid") + ":maas.CustomerAgent";
    		String modName = name.replace(' ', '-');
    		agents.add(modName);
    	}

    	
//    	agents.add("customer:maas.CustomerAgent");
//    	agents.add("bakery:maas.BakeryAgent");
    	List<String> cmd = new Vector<>();
    	cmd.add("-agents");
    	StringBuilder sb = new StringBuilder();
    	for (String a : agents) {
    		sb.append(a);
    		sb.append(";");
    	}
    	cmd.add(sb.toString());
        jade.Boot.main(cmd.toArray(new String[cmd.size()]));
    }
}
