package kr.re.ec.talk.service;

/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import kr.re.ec.talk.R;
import kr.re.ec.talk.util.Constants;
import kr.re.ec.talk.util.LogUtil;

/**
 * Created by slhyv_000 on 2015-06-18.
 * Register my id to server
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = RegistrationIntentService.class.getSimpleName();
    private static final String[] TOPICS = {"global"};
    //private static final String PROJECT_ID = "133397775680";

    //private String REQUEST_URL = Constants.Network.HOST_URL + "/api/gcm/set_reg_id";

    public RegistrationIntentService() {
        super(TAG);
        LogUtil.v(TAG, "constructor called");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtil.v(TAG, "onHandleIntent invoked");
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            // In the (unlikely) event that multiple refresh operations occur simultaneously,
            // ensure that they are processed sequentially.
            synchronized (TAG) {
                // [START register_for_gcm]
                // Initially this call goes out to the network to retrieve the token, subsequent calls
                // are local.
                // [START get_token]
                InstanceID instanceID = InstanceID.getInstance(this);
//                instanceID.deleteToken(PROJECT_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                // [END get_token]
                LogUtil.v(TAG, "GCM Registration Token: " + token);
                Constants.MetaInfo.setMyDeviceId(token);

                // Subscribe to topic channels
                subscribeTopics(token);

                // [END register_for_gcm]
            }
        } catch (Exception e) {
            LogUtil.v(TAG, "GCM Failed to complete token refresh : " + e.getMessage());

        }
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        for (String topic : TOPICS) {
            GcmPubSub pubSub = GcmPubSub.getInstance(this);
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
    // [END subscribe_topics]

}

