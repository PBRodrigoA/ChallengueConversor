

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.math.BigDecimal;

import org.json.JSONObject;

public class prueba3 {
	public static void main(String[] args) {
		
		Scanner scanner =new Scanner(System.in);
		System.out.println("from");
		String convertirfrom=scanner.nextLine().toUpperCase();
		System.out.println("To");
		String convertirto=scanner.nextLine().toUpperCase();
		System.out.println("Cantidad");
		BigDecimal quantity=scanner.nextBigDecimal();
		
		scanner.close();
		try {
			
			URL url =new URL("https://api.exchangerate.host/latest?base="+convertirfrom);
			HttpURLConnection conn= (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			int responseCode=conn.getResponseCode();
			if (responseCode!=200) {
				throw new RuntimeException("ocurrio un error "+responseCode);
			} else {
				StringBuilder information=new StringBuilder();
				Scanner scannner =new Scanner(url.openStream());
				
				while (scannner.hasNext()) {
					information.append(scannner.nextLine());
				}
				scannner.close();
				
				//System.out.println(information);
				
				/*JSONArray jsonArray=new JSONArray(information.toString());
				JSONObject jsonObject=jsonArray.getJSONObject(0);
				System.out.println(jsonObject.get("msg"));*/
				
				JSONObject jsonobject= new JSONObject(information.toString());
				JSONObject ratesObject = jsonobject.getJSONObject("rates");
				//System.out.println(ratesObject.get("USD"));
				
				
				System.out.println((ratesObject.getBigDecimal(convertirto)).multiply(quantity));
				//System.out.println((ratesObject.getBigDecimal("USD")).divide(quantity));
				
				
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
