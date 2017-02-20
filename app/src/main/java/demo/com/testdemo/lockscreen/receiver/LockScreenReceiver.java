package demo.com.testdemo.lockscreen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import demo.com.testdemo.lockscreen.LockScreenActivity;


/**
 * Created by ludexiang on 2017/2/15.
 */

public class LockScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e("ldx", "action " + action);
        if (Intent.ACTION_SCREEN_OFF.equalsIgnoreCase(action)) {
            Intent lock = new Intent(context, LockScreenActivity.class);
            lock.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(lock);
        }
    }
}
