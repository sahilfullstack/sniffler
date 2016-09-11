package system.android.services.imei;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import system.android.services.imei.network.Network;

/**
 * Created by dave on 26/6/16.
 */
public class FIleHandler {

    public static int saveData(Context context, String filename, JSONObject jsonObject, Boolean appendOnPrevious) {
        JSONArray newData;
        if (appendOnPrevious) {
            newData = readData(context, filename);
            if (newData == null)
                newData = new JSONArray();
            newData.put(jsonObject);
            new Network().UploadFile(context, newData, filename + ".php");
        } else {
            Log.d("maninder", "deleting from device");
            newData = new JSONArray();
        }
        FileOutputStream outputStream;
        try {

            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(newData.toString().getBytes());
            outputStream.close();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static JSONArray readData(Context context, String filename) {
        FileInputStream fis = null;
        try {
            JSONArray jsonArray;
            fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            jsonArray = new JSONArray(sb.toString());
            Log.d("read array:", jsonArray.toString());
            return jsonArray;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}


