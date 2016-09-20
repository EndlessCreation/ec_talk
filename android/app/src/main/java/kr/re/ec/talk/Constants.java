package kr.re.ec.talk;

/**
 * Created by slhyv on 9/19/2016.
 */
public class Constants {
    public static final boolean IS_DEBUG = true;

    //TODO: temporary value
    public static final String MY_TOKEN = "f99b7262-046a-42e3-994b-a96a6f278a8a";

    public abstract class Action {
        public static final String ACTION_TO_SERVICE_SEND_MESSAGE_REQ =
                Package.PACKAGE_NAME + "." + "ACTION_TO_SERVICE_SEND_MESSAGE_REQ";

        public static final String ACTION_TO_CHATTING_SEND_MESSAGE_RES =
                Package.PACKAGE_NAME + "." + "ACTION_TO_CHATTING_SEND_MESSAGE_RES";
        public static final String ACTION_TO_CHATTING_REFRESH_VIEW_REQ =
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
