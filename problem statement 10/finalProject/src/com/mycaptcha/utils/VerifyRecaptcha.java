package com.mycaptcha.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

public class VerifyRecaptcha {
	
	public static final String url = "https://www.google.com/recaptcha/api/siteverify";
	public static final String secret = "6LdYUN0hAAAAAJ04RD-vw7eW0jBO6ahhIqaucMD_";
	private final static String USER_AGENT = "Chrome/105.0.0.0 Safari/537.36";
	
	
	public static boolean verify(String recaptchaResponse) throws IOException {
		if(recaptchaResponse == null || "".equals(recaptchaResponse)) {
			return false;
		}
		
		try {
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			
			con.setRequestMethod("POST");
			con.setRequestProperty("user-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			String postParams = "secret= " +secret+ "&response= " +recaptchaResponse;
			
			
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
			
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " +url);
			System.out.println("Post parameters :" +postParams);
			System.out.println("Response Code : " +responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			
			in.close();
			
			System.out.println(response.toString());
			
			
			JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
			JsonObject jsonObject = jsonReader.readObject();
			jsonReader.close();
			
			return jsonObject.getBoolean("success");
		}catch(Exception e) {
			e.printStackTrace();
			return false;

			
		}
	}
	

}
