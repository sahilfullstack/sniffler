package system.android.services.imei.network;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by dave on 12/4/16.
 */
public class HttpRequest {

	private static MediaType JSON = null;

	static final String POST = NetworkConstants.POST_REQUEST;
	static final String PUT = NetworkConstants.PUT_REQUEST;
	static final String DELETE = NetworkConstants.DELETE_REQUEST;
	private static final String PATCH = NetworkConstants.PATCH_REQUEST;
	static final String GET = NetworkConstants.GET_REQUEST;

	private static final String HEADER_CONTENT_TYPE = NetworkConstants.HEADER_CONTENT_TYPE;
	private static final String HEADER_TEXT_JSON = NetworkConstants.HEADER_TEXT_JSON;
	private static final String APPLICATION_JSON_CHARSET_UTF_8 = NetworkConstants.APPLICATION_JSON_CHARSET_UTF_8;

	private static final String TAG = HttpRequest.class.getCanonicalName();

	final Context mContext;


	public HttpRequest(Context context) {
		mContext = context;
	}


	public  void send(String url, String requestType, JSONObject payloadJson, Callback callback) {

		if (!Network.isNetworkAvailable(mContext)) {
			return;
		}
		Log.i(TAG, "send: payload : " + payloadJson);
		OkHttpClient client = new OkHttpClient.Builder()
				                      .connectTimeout(2, TimeUnit.MINUTES)
				                      .readTimeout(2, TimeUnit.MINUTES)
				                      .build();

		Request.Builder requestBuilder = new Request.Builder()
				                                 .addHeader(HEADER_CONTENT_TYPE, HEADER_TEXT_JSON)
				                                 .url(url);

		Request httpRequest = getHttpRequest(requestBuilder, url, requestType, payloadJson);


		Log.i(TAG, "send: sending call to server");
		client.newCall(httpRequest).enqueue(callback);
	}



	private Request getHttpRequest(Request.Builder requestBuilder, String url, String requestType, JSONObject payloadJson) {
		String payloadString = "";

		if (payloadJson != null) {
			payloadString = payloadJson.toString();
		}

		RequestBody body = RequestBody.create(JSON, payloadString);

		Request request = null;

		switch (requestType) {
			case POST:
				request = requestBuilder.post(body).build();
				break;
			case PUT:
				request = requestBuilder.put(body).build();
				break;
			case PATCH:
				request = requestBuilder.patch(body).build();
				break;
			case GET:
				request = requestBuilder.get().build();
				break;
			case DELETE:
				request = requestBuilder.delete().build();
				break;
		}

		return request;
	}
}
