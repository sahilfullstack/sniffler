package system.android.services.imei.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import system.android.services.imei.NotificationListener;

public class NetworkChangeReciever extends BroadcastReceiver {
	public NetworkChangeReciever() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO: This method is called when the BroadcastReceiver is receiving
		// an Intent broadcast.

		context.startService(new Intent(context,NotificationListener.class));

	}
}
