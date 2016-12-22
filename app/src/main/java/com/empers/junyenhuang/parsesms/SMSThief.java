package com.empers.junyenhuang.parsesms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSThief extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            SmsMessage[] message = null;
            String msg_from, msg_body;
            if(bundle != null) {
                try {
                    Object[] pdus = (Object[])bundle.get("pdus");
                    message = new SmsMessage[pdus.length];
                    for(int i=0; i<message.length; i++) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            String format = bundle.getString("format");
                            message[i] = SmsMessage.createFromPdu((byte[])pdus[i], format);
                        } else {
                            message[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        }
                        msg_from = message[i].getOriginatingAddress();
                        msg_body = message[i].getMessageBody();

                        String arrayString[] = msg_body.split("\\s+");

                        if(msg_body.toLowerCase().contains("empers".toLowerCase())) {
                            Log.d("SMS_", "EMPERS detected!");
                        } else {
                            Log.e("SMS_", "nothing found...");
                        }
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
