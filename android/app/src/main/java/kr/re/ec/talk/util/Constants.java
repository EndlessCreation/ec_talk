package kr.re.ec.talk.util;

/**
 * Created by slhyv on 9/19/2016.
 */
public final class Constants {
    public static final boolean IS_DEBUG = true;

    //TODO: temporary value
    //public static final String MY_TOKEN = "f99b7262-046a-42e3-994b-a96a6f278a8a"; //Taehee Kim
    public static final String MY_TOKEN = "873636ce-cedb-4c4a-8695-470a000bd5ef"; //Gang Choi


    public abstract class Network {
        public static final String HOST_URL = "http://117.17.198.41:8080/ec_talk";

        public static final String SEND_MESSAGE_URL = HOST_URL + "/SendMessageServlet";
        public static final String CODE_TYPE_SEND_MESSAGE = "CODE_TYPE_SEND_MESSAGE";

        public static final String REQUEST_NEW_MESSAGES_URL = HOST_URL + "/RequestNewMessagesServlet";
        public static final String CODE_TYPE_REQUEST_NEW_MESSAGES = "CODE_TYPE_REQUEST_NEW_MESSAGES";
    }

    public abstract class Action {
        public static final String ACTION_TO_SERVICE_SEND_MESSAGE_REQ =
                Package.PACKAGE_NAME + "." + "ACTION_TO_SERVICE_SEND_MESSAGE_REQ";

        public static final String ACTION_TO_CHATTING_SEND_MESSAGE_RES =
                Package.PACKAGE_NAME + "." + "ACTION_TO_CHATTING_SEND_MESSAGE_RES";
        public static final String ACTION_TO_CHATTING_REFRESH_VIEW_REQ = //TODO: is it needed?
                Package.PACKAGE_NAME + "." + "ACTION_TO_CHATTING_REFRESH_VIEW_REQ";
        public static final String ACTION_TO_CHATTING_CHECK_TOKEN_RES =
                Package.PACKAGE_NAME + "." + "ACTION_TO_CHATTING_CHECK_TOKEN_RES";
    }

    public abstract class Package {
        public static final String PACKAGE_NAME = "kr.re.ec.talk";
        public static final String SERVICE_PACKAGE_NAME = PACKAGE_NAME + ".service";
        public static final String SERVICE_CLASS_NAME = SERVICE_PACKAGE_NAME + ".MessageService";
    }
}
