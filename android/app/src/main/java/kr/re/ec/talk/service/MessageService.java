package kr.re.ec.talk.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import kr.re.ec.talk.Constants;
import kr.re.ec.talk.LogUtil;
import kr.re.ec.talk.Message;
import kr.re.ec.talk.ProviderController;

/**
 * This Service Managing All Communications.
 * Created by slhyv on 9/20/2016.
 */
public class MessageService extends Service {
    private static final String TAG = MessageService.class.getSimpleName();

    private Context mContext = null;
    private MessageReceiver mReceiver = null;

    @Override
    public void onCreate() {
        LogUtil.i(TAG, "onCreate invoked!!!");

        mContext = this;
        registerReceiverWithActions();
    }
    private void registerReceiverWithActions() {
        //register receiver with actions
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.Action.ACTION_TO_SERVICE_SEND_MESSAGE_REQ);

        mReceiver = new MessageReceiver();
        registerReceiver(mReceiver, filter);
        LogUtil.v(TAG, "registerReceiver()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i(TAG, "\n========MessageService onStartCommand()!==========");

        //TODO: below is dummyscript
//        Intent demoIntent = new Intent(Constants.Action.ACTION_TO_CLIENT_SEND_CHATMSG);
//        String initMsg = "Hello!";
//        demoIntent.putExtra(Constants.Action.ACTION_TO_CLIENT_SEND_CHATMSG, initMsg);
//        sendBroadcast(demoIntent);
//        LogUtil.d(TAG, "dummy msg sent: " + initMsg);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        LogUtil.i(TAG, "onDestroy() invoked");

        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            LogUtil.i(TAG, "unregisterReceiver() called");
            mReceiver = null; //explicit null
        }
        super.onDestroy();
    }

    private class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.v(TAG, "onReceive invoked!");

            if(intent.getAction() != null) {
                switch (intent.getAction()) {
                    case Intent.ACTION_BOOT_COMPLETED:
                    case Intent.ACTION_USER_PRESENT: //TODO: is it duplicated with ServiceWaker? or is it work properly without registerreceiver?
                        LogUtil.d(TAG, "wake up process. start service.");
                        context.startService(new Intent(context, MessageService.class));
                        break;

                    case Constants.Action.ACTION_TO_SERVICE_SEND_MESSAGE_REQ:

                        LogUtil.v(TAG, "received ACTION_TO_SERVICE_SEND_MESSAGE_REQ");

                        String messageString = intent.getStringExtra(Constants.Action.ACTION_TO_SERVICE_SEND_MESSAGE_REQ);
                        LogUtil.d(TAG, "received msg from client: " + messageString);

                        //insert new temporary message
                        Message m = new Message(
                                Message.ID_NOT_SET,
                                -1, //TODO: my id..?
                                Constants.MY_TOKEN,
                                "나 바로 나", //TODO: make my nickname...?
                                "now", //TODO: make this real now
                                messageString,
                                Message.STATE_NOT_SENT_TO_SERVER);
                        ProviderController.MessageController.insertNewChatMsg(mContext, m);

                        Intent refreshIntent = new Intent(Constants.Action.ACTION_TO_CHATTING_REFRESH_VIEW_REQ);
                        sendBroadcast(refreshIntent);

                        //msg from client. send msg to server.
                        //TODO: DO SERVER REQUEST HERE!
                        break;
                }
            }
        }
    }
}
