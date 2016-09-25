package kr.re.ec.talk.service;

/**
 * Created by slhyv on 9/24/2016.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import kr.re.ec.talk.ChattingActivity;
import kr.re.ec.talk.R;
import kr.re.ec.talk.util.Constants;
import kr.re.ec.talk.util.LogUtil;

/**
 * Created by slhyv_000 on 2015-06-18.
 */
public class MyGcmListenerService extends GcmListenerService {
    private static final String TAG = MyGcmListenerService.class.getSimpleName();

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String nickname = data.getString("nickname");
        String datetime = data.getString("datetime");
        LogUtil.v(TAG, "From: " + from + ", Message: " + message + ", nickname: " + nickname + ", datetime: " + datetime);

        //for test
        String string = "Bundle{";
        for (String key : data.keySet()) {
            string += " " + key + " => " + data.get(key) + ";";
        }
        string += " }Bundle";
        LogUtil.d(TAG, string);

        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(message, nickname, datetime);
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message, String nickname, String datetime) {
        Intent intent = new Intent(this, ChattingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.no_profile_image_m)
                .setContentTitle(nickname) //TODO: change name
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        Intent requestNewMessagesIntent = new Intent(Constants.Action.ACTION_TO_SERVICE_REQUEST_NEW_MESSAGE);
        sendBroadcast(requestNewMessagesIntent);
    }
}
