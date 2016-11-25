package com.vhiefa.nyayur.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.vhiefa.nyayur.R;


/**
 * Created by budic on 10/29/2016.
 */

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    String tukangsayur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     /*   txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        txtMessage = (TextView) findViewById(R.id.txt_push_message);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null)
        {
            tukangsayur =(String) b.get("tukangsayur");
            //Textv.setText(j);
        }

        Log.d(TAG,"ID TOPIK : "+tukangsayur);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(tukangsayur);
                    Log.d(TAG,"ID TOPIK : "+tukangsayur);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    Log.d(TAG,"Milih config push : "+tukangsayur);
                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    txtMessage.setText(message);
                }
            }
        };
        FirebaseMessaging.getInstance().subscribeToTopic(tukangsayur);
        displayFirebaseRegId();*/
    }
/*
    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    } */
}