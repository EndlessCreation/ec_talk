package kr.re.ec.talk;

import android.app.Application;
import android.content.Context;

import kr.re.ec.talk.util.LogUtil;

/**
 * Created by slhyv on 8/11/2016.
 * Start point of application
 */
public class App extends Application {
    public static final String TAG = App.class.getSimpleName();
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.i(TAG, "================Start of application=============");
        context = this;

    }
}
