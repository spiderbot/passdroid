package com.kodholken.passdroid;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class TimeoutActivity extends Activity {
    private BroadcastReceiver receiver;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Session.TIMEOUT_ACTION);
        
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Session.getInstance().setLoggedIn(false);
                finish();
            }
        };

        registerReceiver(receiver, filter);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        if (Session.getInstance().decResume() == 0) {
            Session.setTimeoutTimer(this);
        }
        super.onPause();
    }
    
    @Override
    protected void onResume() {
        Session.getInstance().incResume();
        Session.clearTimeoutTimer(this);
        super.onResume();
    }
    
    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
