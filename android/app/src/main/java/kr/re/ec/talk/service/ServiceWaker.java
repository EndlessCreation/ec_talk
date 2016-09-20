package kr.re.ec.talk.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import kr.re.ec.talk.Constants;
import kr.re.ec.talk.LogUtil;

/**
 * When reboot, start MessageService
 * Created by slhyv on 9/20/2016.
 */
public class ServiceWaker extends BroadcastReceiver {
    private static final String TAG = ServiceWaker.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.i(TAG, "onReceive invoked!");

        if (intent.getAction() != null) {
            if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) { //when reboot
                LogUtil.v(TAG, "BOOT_COMPLETED received");

                //start service
                Intent i = new Intent();
                i.setClassName(Constants.Package.SERVICE_PACKAGE_NAME, Constants.Package.SERVICE_CLASS_NAME);
                i.setAction(".START");
                ComponentName componentName = context.startService(i);
                LogUtil.v(TAG, "result of startService: "+componentName.toShortString());

            }
        }

    }
}
