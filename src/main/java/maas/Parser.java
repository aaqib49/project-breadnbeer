package maas;

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

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import java.util.*;

public class Parser {
	public JSONObject jsonObject = new JSONObject();
	// Arrays of Bakeries, Customers, Orders etc.
	public JSONArray jsonBakeries = new JSONArray();
	public JSONArray jsonCustomers = new JSONArray();
	public JSONArray jsonOrders = new JSONArray();
	public JSONArray jsonStreetNetwork = new JSONArray();
	public JSONObject jsonMeta = new JSONObject();
	public HashMap bakeryHashMap = new HashMap();
	
	public Parser(){
		JSONParser parser = new JSONParser();
		List<String> product_names = new ArrayList<String>();

		try{
			jsonObject = (JSONObject) parser.parse(new FileReader("/home/chetan/Dropbox/Multi_Agent/project-breadnbeer/src/main/config/test.json"));
			//jsonObject = (JSONObject) parser.parse(new FileReader("/home/khoi/MAAS/project-breadnbeer/src/main/java/maas/test.json"));
			jsonBakeries = (JSONArray) jsonObject.get("bakeries");
			jsonCustomers = (JSONArray) jsonObject.get("customers");
			jsonOrders = (JSONArray) jsonObject.get("orders");
//			jsonStreetNetwork = (JSONArray) jsonObject.get("street_network");
			jsonMeta = (JSONObject) jsonObject.get("meta");
			
			for (Object c : jsonBakeries){
				JSONObject bakeryObj = (JSONObject) c;
				String bakeryGuid = (String) bakeryObj.get("guid");
				JSONArray productJSON = (JSONArray) bakeryObj.get("products");
				List<String> productList = new Vector<>();
				for (Object d: productJSON){
					JSONObject product = (JSONObject) d;
					String product_name = (String) product.get("guid");
					productList.add(product_name);
				}
				bakeryHashMap.put(bakeryGuid, productList);
			}
		} catch ( Exception e ){
			e.printStackTrace();
		}
	}
	
	//The function below return the list of orders for a customer-id
	
	public JSONArray GetSortedLocalOrders(String name){
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		JSONArray sortedLocalOrders = new JSONArray();
		for (Object c: jsonOrders){
			JSONObject orderObj = (JSONObject) c;
			String customerId = (String) orderObj.get("customer_id");
			
//			System.out.println( name);
//			System.out.println( customerId);
//			System.out.println(customerId.equals(name));
//			System.out.println(orderObj.toString());
			
			if(customerId.equals(name)){
//				jsonList.add(orderObj);
				sortedLocalOrders.add(orderObj);
			}
			
		}
		
//		Collections.sort( jsonList, new Comparator<JSONObject>() {
//
//		    public int compare(JSONObject a, JSONObject b) {
//		    	JSONObject A = new JSONObject();
//		    	JSONObject B = new JSONObject();
//		    	A = (JSONObject) a.get("order_date");
//		    	B = (JSONObject) b.get("order_date");
//		    	
//		        int dayA = (int) A.get("day");
//		        int dayB = (int) B.get("day");
//		        
//		        if (dayA != dayB){
//		        	return Integer.compare(dayA, dayB);
//		        }
//		        
//		        int hourA = (int) A.get("hour");
//	        	int hourB = (int) B.get("hour");
//	        	
//	        	return Integer.compare(hourA, hourB);   
//		    }
//		});
		
//		for (int i = 0; i < jsonList.size(); i++) {
//		    sortedLocalOrders.add(jsonList.get(i));
//		}
		
		return(sortedLocalOrders);
	}

}
