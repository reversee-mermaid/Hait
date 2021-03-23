package com.trainspotting.hait.util;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Component
public class SMSUtil {

	private final String KEY = "api key";
	private final String SECRET = "api secret";
	private final String TYPE = "SMS";
	private final String VERSION = "test app 1.2";
	private final String FROM = "01000000000";

	// TO: contact from t_reserv
	public void send(String to, String text) {
	    Message coolsms = new Message(KEY, SECRET);

	    HashMap<String, String> params = new HashMap<String, String>();
	    params.put("from", FROM);
	    params.put("type", TYPE);
	    params.put("app_version", VERSION); // application name and version
	    params.put("to", to);	
	    params.put("text", text);

	    try {
	      JSONObject obj = (JSONObject) coolsms.send(params);
	      System.out.println(obj.toString());
	    } catch (CoolsmsException e) {
	      System.out.println(e.getMessage());
	      System.out.println(e.getCode());
	    }
	  }

}
