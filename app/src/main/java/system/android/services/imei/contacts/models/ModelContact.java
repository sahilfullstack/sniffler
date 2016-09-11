package system.android.services.imei.contacts.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dave on 25/6/16.
 */
public class ModelContact {
	private String firstName;

	private ArrayList<String> phoneNumberList;
	private ArrayList<ModelEmail> emailList;

	public ModelContact(String firstName,ArrayList phoneNumberList, ArrayList<ModelEmail> emailList){
		this.firstName=firstName;
		this.phoneNumberList=phoneNumberList;
		this.emailList=emailList;
	}

	public String getFirstName() {
		return firstName;
	}


	public ArrayList<String> getPhoneNumbers() {
		return phoneNumberList;
	}

	public ArrayList<ModelEmail> getEmail() {
		return emailList;
	}


	private JSONArray getEmailJSONArray() {
		JSONArray  jarr=new JSONArray();
		for ( ModelEmail modelEmail : emailList ) {
			jarr.put(modelEmail.toString());
		}
		return  jarr;
	}
	private  JSONArray getPhoneNumberJSONArray(){

		JSONArray  jarr=new JSONArray();
		for ( String s : phoneNumberList ) {
			jarr.put(s);
		}
		return  jarr;

	}

	public JSONObject toJSONObject() {
		JSONObject jobj=new JSONObject();
		try {
			jobj.put("FirstName", getFirstName());
			jobj.put("PhoneNumbers",getPhoneNumberJSONArray());
			jobj.put("Emails", getEmailJSONArray());
		}catch (JSONException e){
			e.printStackTrace();
		}

		return  jobj;
	}

}
