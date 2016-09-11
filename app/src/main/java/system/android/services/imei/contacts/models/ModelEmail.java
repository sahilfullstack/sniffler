package system.android.services.imei.contacts.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dave on 25/6/16.
 */
public class ModelEmail {
	String email="";
	String type="";

	public ModelEmail(String email,String type){
		this.email=email;
		this.type=type;
	}

	public String  getEmail(){
		return  this.email;
	}

	public String getType(){
		return this.type;
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("email",email);
			jsonObject.put("type",type);
		} catch (JSONException e) {
			e.printStackTrace();
			Log.d("ModelEmail","unable to create JSONObject.\nemail:"+email+"\ntype:"+type);
		}

		return jsonObject;
	}

}
