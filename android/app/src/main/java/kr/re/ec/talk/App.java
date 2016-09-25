package kr.re.ec.talk;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import kr.re.ec.talk.service.RegistrationIntentService;
import kr.re.ec.talk.util.Constants;
import kr.re.ec.talk.util.LogUtil;
import kr.re.ec.talk.util.PrefUtil;

/**
 * Created by slhyv on 8/11/2016.
 * Start point of application
 */
public class App extends Application {
    public static final String TAG = App.class.getSimpleName();
    public static Context context;

    //for gcm
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    public void onCreate() {
        LogUtil.i(TAG, "================Start of application=============");
        super.onCreate();
        context = this;

        //register gcm
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String deviceId = Constants.MetaInfo.getMyDeviceId();
                LogUtil.d(TAG, "deviceId: " + deviceId);
                if (!(deviceId.equals(""))) { //already sent to server
                    LogUtil.v(TAG, "gcm: Token retrieved and sent to server! You can now use gcmsender to\n" +
                            "send downstream messages to this app.");
                } else {
                    LogUtil.v(TAG, "gcm: An error occurred while either fetching the InstanceID token,\n" +
                            "sending the fetched token to the server or subscribing to the PubSub topic. Please try\n" +
                            "running the sample again.");
                }
            }
        };
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    /** TODO: check for gcm. make gcm class and remove deprecated*/
    private boolean checkPlayServices() {
        LogUtil.v(TAG, "checkPlayServices called");
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                LogUtil.v(TAG, "gcm: user recoverable error");  //TODO: what is this?
//                GooglePlayServicesUtil.getErrorDialog(resultCode, context,
//                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                LogUtil.e(TAG, "gcm: This device is not supported.");
            }
            return false;
        }
        return true;
    }
}
