package kr.re.ec.talk;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.re.ec.talk.dto.Message;
import kr.re.ec.talk.util.LogUtil;

/**
 * DB ProviderController
 * Created by slhyv on 9/19/2016.
 */
public final class ProviderController {
    private static final String TAG = ProviderController.class.getSimpleName();

    /** The Constant COLS_MESSAGE_ARR.
     * except for ID */
    public static final String COLS_MESSAGE_ARR[] = {
            MessageProvider.COL_ID,
            MessageProvider.COL_SENDER_ID,
            MessageProvider.COL_SENDER_TOKEN,
            MessageProvider.COL_SENDER_NICKNAME,
            MessageProvider.COL_SEND_DATETIME,
            MessageProvider.COL_CONTENTS,
            MessageProvider.COL_STATE
    };

    private ProviderController() {} //cannot create instance

    public static class MessageController {
        private MessageController() {}  //cannot create instance
        /**
         * Delete all Message from DB.
         *
         * @param ctx
         *            the ctx
         */
        public static void deleteAllMessages(Context ctx) {
            ContentValues Values = new ContentValues();
            ContentResolver cr = ctx.getContentResolver();

            // String encStr = CNSCrypto.encriptStr(ctx, name);

            cr.delete(MessageProvider.CONTENT_URI_MESSAGE, "", new String[] {});
        }

        /**
         * Insert new chatmsg to DB.
         */
        public static void insertNewChatMsg(Context ctx, Message m) {
            ContentValues Values = new ContentValues();
            ContentResolver cr = ctx.getContentResolver();

            //boolean isEncrypt = PrefUtil.getSecurityStatePreference(ctx);

            LogUtil.v(TAG, "arg>>" + m.toString());

            Values.put(MessageProvider.COL_SENDER_ID, m.getSenderId());
            Values.put(MessageProvider.COL_SENDER_TOKEN, m.getSenderToken());
            Values.put(MessageProvider.COL_SENDER_NICKNAME, m.getSenderNickname());
            Values.put(MessageProvider.COL_SEND_DATETIME, m.getSendDatetime());
            Values.put(MessageProvider.COL_CONTENTS, m.getContents());
            Values.put(MessageProvider.COL_STATE, m.getState());

            cr.insert(MessageProvider.CONTENT_URI_MESSAGE, Values);
        }

        /** get data from provider */
        public static Message getMessageByCursor(Cursor c) {
            Message m;

            //get data from DB
            int id = c.getInt(c.getColumnIndex(MessageProvider.COL_ID));
            int senderId = c.getInt(c.getColumnIndex(MessageProvider.COL_SENDER_ID));
            String senderToken = c.getString(c.getColumnIndex(MessageProvider.COL_SENDER_TOKEN));
            String senderNickname = c.getString(c.getColumnIndex(MessageProvider.COL_SENDER_NICKNAME));
            String sendDatetime = c.getString(c.getColumnIndex(MessageProvider.COL_SEND_DATETIME));
            String contents = c.getString(c.getColumnIndex(MessageProvider.COL_CONTENTS));
            int state = c.getInt(c.getColumnIndex(MessageProvider.COL_STATE));

            m = new Message(id, senderId, senderToken, senderNickname, sendDatetime, contents, state);

            return m;
        }

        /**
         * get max chatmsg sequence number by room id
         * TODO: is this needed?
         * @param ctx
         * @return
         */
        public static int getMaxChatMsgSeqNoByRoomId(Context ctx) {
            int resultMax=0;
            String projection = "MAX(" + MessageProvider.COL_ID + ")";

            ContentResolver cr = ctx.getContentResolver();
            Cursor c = cr.query(MessageProvider.CONTENT_URI_MESSAGE,
                    new String[] {projection + " AS"}, null, null, null);

            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                while (c.isAfterLast() == false) {
                    c.getInt(c.getColumnIndex(projection));

                    c.moveToNext();
                }
            }
            c.close();
            return resultMax;
        }
    }

    //TODO: is it needed? check it
    @SuppressLint("SimpleDateFormat")
    private static String convertDateToString(Date date, String dateFormat) {
        if(date != null) {
            String str = new SimpleDateFormat(dateFormat).format(date);
            //LogUtil.v("converted string '" + date.toString() + "' to '" + str +"'");
            return str;
        } else {
            return null;
        }
    }

}
