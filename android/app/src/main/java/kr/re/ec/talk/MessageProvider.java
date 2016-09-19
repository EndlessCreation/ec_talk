package kr.re.ec.talk;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * ContentProvider for Chatting Msg Storage
 * Created by slhyv on 9/19/2016.
 */
public class MessageProvider extends ContentProvider {
    private static final String TAG = MessageProvider.class.getSimpleName();

    //general info
    public static final String TABLE_NAME_MESSAGES = "messages";
    private static final String DATABASE_NAME_MESSAGES = "messages.db";
    private static final String AUTHORITY = "kr.re.ec.talk.MessageProvider"; //TODO: change it's name
    public static final Uri CONTENT_URI_MESSAGE = Uri.parse("content://"
            + AUTHORITY + "/" + TABLE_NAME_MESSAGES);

    /** local DB version */
    public static final int DATABASE_VERSION = 4; //TODO: it is public, right?

    //cols
    /** pk */
    public static final String COL_ID = "_id";
    public static final String COL_SENDER_ID = "senderId";
    public static final String COL_SENDER_TOKEN = "senderToken";
    public static final String COL_SENDER_NICKNAME = "senderNickname";
    public static final String COL_SEND_DATETIME = "sendDatetime" ;
    public static final String COL_CONTENTS = "contents";
    public static final String COL_STATE = "state";

    //order criteria
    /** The history order_asc. */
    public static final String ORDER_BY_SEQ_ID_ASC = COL_SEND_DATETIME + " ASC";

    /** create db string */
    public static final String DATABASE_CREATE_MESSAGE = "CREATE TABLE "
            + TABLE_NAME_MESSAGES + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COL_SENDER_ID + " INTEGER,"
            + COL_SENDER_TOKEN + " TEXT NOT NULL,"
            + COL_SENDER_NICKNAME + " TEXT,"
            + COL_SEND_DATETIME + " TEXT NOT NULL,"
            + COL_CONTENTS + " TEXT,"
            + COL_STATE + " INT NOT NULL);";


    private static ChatMsgDataBaseHelper mDbHelperChatMsg;
    private SQLiteDatabase dbMessage; // db object

    @Override
    public boolean onCreate() {
        LogUtil.v(TAG, "invoked"); //invoked.
        mDbHelperChatMsg = new ChatMsgDataBaseHelper(getContext(), //TODO: can it work?
                DATABASE_NAME_MESSAGES, null, DATABASE_VERSION);
        dbMessage = mDbHelperChatMsg.getWritableDatabase();
        return (dbMessage == null) ? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        cursor = dbMessage.query(TABLE_NAME_MESSAGES, projection, selection,
                selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        dbMessage.insertOrThrow(TABLE_NAME_MESSAGES, null, values);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;
        count = dbMessage.delete(TABLE_NAME_MESSAGES, selection, selectionArgs);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = dbMessage.update(TABLE_NAME_MESSAGES, values, selection, selectionArgs);
        return count;
    }

    private static class ChatMsgDataBaseHelper extends SQLiteOpenHelper {
        public ChatMsgDataBaseHelper(Context context, String name,
                                     CursorFactory factory, int version) {
            super(context, DATABASE_NAME_MESSAGES, factory, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_MESSAGE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            LogUtil.w(TAG, "invoked! " + oldVersion + " -> " + newVersion + ", it'll destroy old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MESSAGES);
            onCreate(db);
        }
    }
}

