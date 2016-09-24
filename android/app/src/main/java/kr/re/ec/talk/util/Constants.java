package kr.re.ec.talk.util;

/**
 * Created by slhyv on 9/19/2016.
 */
public final class Constants {
    public static final boolean IS_DEBUG = true;
    private static final String TAG = Constants.class.getSimpleName();

    public static class MetaInfo {
        private static String MY_TOKEN = "";
        private static String MY_NICKNAME = "";

        /**
         * TODO: add no my token exception
         * @return if empty string return, there is no MY_TOKEN in preferences.
         */
        public static String getMyToken() {
            if(MY_TOKEN.equals("")) { //if empty
                LogUtil.d(TAG, "MY_TOKEN is empty. attemp to load from preferences");
                MY_TOKEN = PrefUtil.getString(PrefUtil.PREFERENCES_KEY_MY_TOKEN, ""); //get temp data
            }
            return MY_TOKEN;
        }
        public static void setMyToken(String token) {
            MY_TOKEN = token;
            PrefUtil.putString(PrefUtil.PREFERENCES_KEY_MY_TOKEN, token);
        }

        public static String getMyNickname() {
            if(MY_NICKNAME.equals("")) { //if empty
                LogUtil.d(TAG, "MY_NICKNAME is empty. attemp to load from preferences");
                MY_NICKNAME = PrefUtil.getString(PrefUtil.PREFERENCES_KEY_MY_NICKNAME, ""); //get temp data
            }
            return MY_NICKNAME;
        }

        public static void setMyNickname(String nickname) {
            MY_NICKNAME = nickname;
            PrefUtil.putString(PrefUtil.PREFERENCES_KEY_MY_NICKNAME, nickname);
        }
    }

    public abstract class Package {
        public static final String PACKAGE_NAME = "kr.re.ec.talk";
        public static final String SERVICE_PACKAGE_NAME = PACKAGE_NAME + ".service";
        public static final String SERVICE_CLASS_NAME = SERVICE_PACKAGE_NAME + ".MessageService";
    }

    public abstract class Network {
        public static final String HOST_URL = "http://117.17.198.41:8080/ec_talk";

        public static final String SEND_MESSAGE_URL = HOST_URL + "/SendMessageServlet";
        public static final String CODE_TYPE_SEND_MESSAGE = "CODE_TYPE_SEND_MESSAGE";

        public static final String REQUEST_NEW_MESSAGES_URL = HOST_URL + "/RequestNewMessagesServlet";
        public static final String CODE_TYPE_REQUEST_NEW_MESSAGES = "CODE_TYPE_REQUEST_NEW_MESSAGES";

        public static final String AUTH_URL = HOST_URL + "/AuthServlet";
        public static final String CODE_TYPE_AUTH = "CODE_TYPE_AUTH";
    }

    public abstract class Action {
        public static final String ACTION_TO_SERVICE_SEND_MESSAGE_REQ =
                Package.PACKAGE_NAME + "." + "ACTION_TO_SERVICE_SEND_MESSAGE_REQ";
        public static final String ACTION_TO_CHATTING_SEND_MESSAGE_RES =
                Package.PACKAGE_NAME + "." + "ACTION_TO_CHATTING_SEND_MESSAGE_RES";

        public static final String ACTION_TO_CHATTING_REFRESH_VIEW_REQ =
                Package.PACKAGE_NAME + "." + "ACTION_TO_CHATTING_REFRESH_VIEW_REQ";

        public static final String ACTION_TO_SERVICE_AUTH_REQ =
                Package.PACKAGE_NAME + "." + "ACTION_TO_SERVICE_AUTH_REQ";
        public static final String ACTION_TO_SIGN_IN_AUTH_RES =
                Package.PACKAGE_NAME + "." + "ACTION_TO_SIGN_IN_AUTH_RES";
    }


}
