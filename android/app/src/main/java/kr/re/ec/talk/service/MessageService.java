package kr.re.ec.talk.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import kr.re.ec.talk.ProviderController;
import kr.re.ec.talk.dto.Message;
import kr.re.ec.talk.dto.SendMessageRequest;
import kr.re.ec.talk.dto.SendMessageResponse;
import kr.re.ec.talk.util.Constants;
import kr.re.ec.talk.util.GsonRequest;
import kr.re.ec.talk.util.LogUtil;
import kr.re.ec.talk.util.RequestController;

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

    private void requestSendMessage(Message m) {
        //make param
        SendMessageRequest requestParam = new SendMessageRequest();
        requestParam.setCode(Constants.Network.CODE_TYPE_SEND_MESSAGE);
        requestParam.setToken(Constants.MY_TOKEN);
        requestParam.setMessage(m);
        LogUtil.v(TAG, "requestParam: " + requestParam);

        GsonRequest<SendMessageResponse> request = new GsonRequest<>(
                Request.Method.POST, Constants.Network.SEND_MESSAGE_URL,
                new Gson().toJson(requestParam),
                SendMessageResponse.class, null,
                new Response.Listener<SendMessageResponse>() {
                    @Override
                    public void onResponse(SendMessageResponse response) {
                        LogUtil.v(TAG, "response: " + response.toString());

                        //on Success //TODO: throw new Exception ?
                        if(response.getSuccess()) {
                            Intent refreshIntent = new Intent(Constants.Action.ACTION_TO_CHATTING_REFRESH_VIEW_REQ);
                            sendBroadcast(refreshIntent);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d(TAG, "error! = " + error.toString());
            }
        });

        RequestController.getInstance(this.getApplicationContext()).addToRequestQueue(request, TAG);
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

                        //insert new message
                        Message m = new Message(
                                Message.ID_NOT_SET,
                                12, //TODO: my id..?
                                Constants.MY_TOKEN,
                                "김태희[21]", //TODO: make my nickname...?
                                "2016-09-20T13:57:00", //TODO: make this real now
                                messageString,
                                Message.STATE_NOT_SENT_TO_SERVER);
                        ProviderController.MessageController.insertNewChatMsg(mContext, m);

                        requestSendMessage(m);
                        //TODO: update message state after send request
                        break;
                }
            }
        }
    }
}
