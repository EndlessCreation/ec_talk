package kr.re.ec.talk;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by slhyv on 9/19/2016.
 */
public class ChattingActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ChattingActivity.class.getSimpleName();

    private EditText mEtMessage;	//text to send
    private Button mBtnSend;
    private ListView mLvChat;

    /** The m layout others. */
    private LinearLayout mLayoutOthers;

    /** The m layout mine. */
    private LinearLayout mLayoutMine;

    /** The m inflater. */
    private LayoutInflater mInflater = null;

    private ChatCursorAdapter mChatCursorAdapter = null;

    private Context mContext = null;

    private Cursor mMainCursor = null;

    private MessageReceiver mReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.v(TAG, "onCreate invoked!");

        setContentView(R.layout.activity_chatting);

        mEtMessage = (EditText) findViewById(R.id.chat_et_message);
        mBtnSend = (Button) findViewById(R.id.chat_btn_send);
        mBtnSend.setOnClickListener(this);
        mLvChat = (ListView) findViewById(R.id.chat_listview);

        mContext = this;

        //Util.hideKeyboard(etMessage);

        mInflater = (LayoutInflater) mContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE);

        initDbWithDummyData();
    }

    private void initDbWithDummyData() {
        //db init
        ProviderController.MessageController.deleteAllMessages(this);

        Message m = new Message(
                Message.ID_NOT_SET,
                7,
                "8e6f7e60-db5d-498a-bfb1-6c16dfd2e5d5",
                "조영진[25]",
                "1988-07-21T05:55:55",
                "이씨톡에 온 것을 환영하여요!",
                Message.STATE_SENT
        );
        ProviderController.MessageController.insertNewChatMsg(this, m);

        m = new Message(
                Message.ID_NOT_SET,
                2,
                "b4199a46-7467-42de-b0fa-58a4f19cacdd",
                "김지홍[22]",
                "1988-07-21T05:55:58",
                "이씨톡에 온 것을 환영하여요4!",
                Message.STATE_SENT
        );
        ProviderController.MessageController.insertNewChatMsg(this, m);

        m = new Message(
                Message.ID_NOT_SET,
                10,
                "88d4552e-1696-49fa-b144-00e9770b5000",
                "김성규[26]",
                "1988-07-21T05:55:61",
                "이씨톡에 온 것을 환영하여요7!",
                Message.STATE_SENT
        );
        ProviderController.MessageController.insertNewChatMsg(this, m);

        m = new Message(
                Message.ID_NOT_SET,
                11,
                "c1220e4c-e872-4ada-b1f0-03261a6e8831",
                "박주환[26]",
                "1988-07-21T05:55:62",
                "이씨톡에 온 것을 환영하여요8!",
                Message.STATE_SENT
        );
        ProviderController.MessageController.insertNewChatMsg(this, m);

        m = new Message(
                Message.ID_NOT_SET,
                6,
                "9a17bf93-fde2-406b-ab9e-864b3c38e0ac",
                "허수정[24]",
                "1988-07-21T05:55:57",
                "이씨톡에 온 것을 환영하여요3!",
                Message.STATE_SENT
        );
        ProviderController.MessageController.insertNewChatMsg(this, m);

        m = new Message(
                Message.ID_NOT_SET,
                4,
                "56e7601e-4f24-447f-b239-caa5cf799467",
                "주현석[24]",
                "1988-07-21T05:55:59",
                "이씨톡에 온 것을 환영하여요5!",
                Message.STATE_SENT
        );
        ProviderController.MessageController.insertNewChatMsg(this, m);

        m = new Message(
                Message.ID_NOT_SET,
                12,
                "f99b7262-046a-42e3-994b-a96a6f278a8a",
                "김태희[21]",
                "1988-07-21T05:55:56",
                "이씨톡에 온 것을 환영하여요2!",
                Message.STATE_SENT
        );
        ProviderController.MessageController.insertNewChatMsg(this, m);

        m = new Message(
                Message.ID_NOT_SET,
                1,
                "5de46a86-a867-4d89-8400-639f6016acb2",
                "서주리[22]",
                "1988-07-21T05:55:60",
                "이씨톡에 온 것을 환영하여요6!",
                Message.STATE_SENT
        );
        ProviderController.MessageController.insertNewChatMsg(this, m);
    }

    @Override
    public void onClick(View v) {
        LogUtil.v(TAG, "onClick() invoked");
        switch (v.getId()) {
            case R.id.chat_btn_send:
                //if edittext not empty
                if(mEtMessage.getText().toString().equals("")) {
                    LogUtil.w(TAG, "edittext is empty");
                    return;
                }

                //insert new temporary message
                ProviderController.MessageController.insertNewChatMsg(this,
                        new Message(
                                Message.ID_NOT_SET,
                                -1, //TODO: my id..?
                                Constants.MY_TOKEN,
                                "나 바로 나", //TODO: make my nickname...?
                                "now", //TODO: make this real now
                                mEtMessage.getText().toString(),
                                Message.STATE_NOT_SENT_TO_SERVER)
                );

                //refresh chatting list
                if( mMainCursor != null) {
                    mMainCursor.requery(); //TODO: it's deprecated.
                }
                LogUtil.v(TAG, "maincursor requeryed pos: " + mMainCursor.getPosition());

                //notifyDataSetChanged() make scroll down automatically with android:transcriptMode="alwaysScroll"
                mChatCursorAdapter.notifyDataSetChanged();

                //make edittext empty
                mEtMessage.setText("");

                //TODO: send message to server

                break;

            default:
                LogUtil.e(TAG, "default switch err");
                break;
        }

    }

    /**
     * The listener interface for receiving myScroll events. The class that is
     * interested in processing a myScroll event implements this interface, and
     * the object created with that class is registered with a component using
     * the component's <code>addMyScrollListener<code> method. When
     * the myScroll event occurs, that object's appropriate
     * method is invoked.
     */
    class MyScrollListener implements AbsListView.OnScrollListener {

        /*
         * (non-Javadoc)
         *
         * @see
         * android.widget.AbsListView.OnScrollListener#onScroll(android.widget
         * .AbsListView, int, int, int)
         */
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            //  Auto-generated method stub
//			mLastItemPos = firstVisibleItem + visibleItemCount;
//
//			if (totalItemCount == mLastItemPos) {
//				isSelectionExecute = true;
//			} else {
//				isSelectionExecute = false;
//			}
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * android.widget.AbsListView.OnScrollListener#onScrollStateChanged(
         * android.widget.AbsListView, int)
         */
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //  Auto-generated method stub
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        //TODO: remove notification for this chattingRoom

        mMainCursor = getChatCursor();

        mChatCursorAdapter = new ChatCursorAdapter(mContext, mMainCursor);
        mLvChat.setAdapter(mChatCursorAdapter);
        mLvChat.setOnScrollListener(new MyScrollListener());

        //TODO: restore listview position

        //mContext = this;

        //		mInflater = (LayoutInflater) mContext
        //				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        //		profileData = PrefUtil.getProfileInfoPreference(mContext);
        //		try {
        //			JSONObject profilJsonData = new JSONObject(profileData);
        //			if (ImessengerConst.ISDEBUG) {
        //				Log.e(TAG, "profilJsonData ::  " + profilJsonData.toString());
        //			}
        //
        //			mMyName = profilJsonData
        //					.getString(ImessengerConst.JSON_DETAIL_KEY_KOREAN_NAME);
        //			mMyId = profilJsonData
        //					.getString(ImessengerConst.JSON_DETAIL_KEY_USER_ID);
        //			mMyFileTransferAuth = profilJsonData
        //					.getString(ImessengerConst.JSON_DETAIL_KEY_FILE_TRANS_AUTH);
        //
        //			mMyUserKey = profilJsonData
        //					.getString(ImessengerConst.JSON_DETAIL_KEY_USER_KEY);
        //			mMyMobileIndex = profilJsonData
        //					.getString(ImessengerConst.JSON_DETAIL_KEY_MOBILE_INDEX);
        //		} catch (JSONException e) {
        //			e.printStackTrace();
        //		}

    }

    @Override
    public void onDestroy() { //TODO: sometimes it doesn't invoke. It may cause to call onReceive() multiple times.
        LogUtil.i(TAG, "invoked");

        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            LogUtil.i(TAG, "unregisterReceiver()");
            mReceiver = null;
        }

        super.onDestroy();
    }

    @SuppressWarnings("deprecation")
    private Cursor getChatCursor() {
        Cursor c = null;
        c = managedQuery(MessageProvider.CONTENT_URI_MESSAGE, //TODO: need to change it to cursorLoader() cuz it's deprecated.
                ProviderController.COLS_MESSAGE_ARR, null, null, MessageProvider.ORDER_BY_SEQ_ID_ASC);
        LogUtil.i(TAG, "c.getCount(): " + c.getCount());

//		if (c != null && c.getCount() > 0) { // TODO: I DONT KNOW WHY IT EXISTS.
//			c.moveToFirst();
//			Cursor chattingC = null;
//
//			String groupChatState = c.getString(c
//					.getColumnIndex(ChatHistoryProvider.KEY_GROUP_CHAT_STATE));
//			ImessengerConst.IS_GROUP_STATE = groupChatState;
//
//			if (groupChatState.endsWith("true")) {
//				// 그룹 채팅 상태
//				chattingC = managedQuery(ChatHistoryProvider.CONTENT_URI_MSG,
//						ProviderUtil.AS_CHATHISTORYCOLUMNS_TO_RETURN,
//						ChatHistoryProvider.KEY_ROOM_ID + " =?" + " AND "
//								+ ChatHistoryProvider.KEY_MIME_TYPE + " !=?",
//						new String[] { RoomId, "" },
//						ProviderUtil.historyOrder_asc);
//			} else {
//				// 1:1 채팅 상태
//				chattingC = managedQuery(ChatHistoryProvider.CONTENT_URI_MSG,
//						ProviderUtil.AS_CHATHISTORYCOLUMNS_TO_RETURN,
//						ChatHistoryProvider.KEY_ROOM_ID + " =?" + " AND "
//								+ ChatHistoryProvider.KEY_MIME_TYPE + " !=?",
//						new String[] { RoomId, "" },
//						ProviderUtil.historyOrder_asc);
//			}
//			if (chattingC != null && chattingC.getCount() > 0) {
//				chattingC.moveToFirst();
//				startManagingCursor(chattingC);
//			}
//
//			c.close();
//
//			return chattingC;
        return c;
    }

    /**
     * MessageReceiver extends BroadcastReceiver
     * Created by slhyv on 9/19/2016.
     *
     */
    class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.v(TAG, "invoked!");
            if (intent.getAction() != null) {
                if(intent.getAction().equals(Constants.ACTION_TO_CHATTING_VIEW_REFRESH_REQ)) {
                    LogUtil.d(TAG, "ACTION_TO_CHATTING_VIEW_REFRESH_REQ received");


                    if( mMainCursor != null) {
                        mMainCursor.requery(); //TODO: it's deprecated.
                    }
                    LogUtil.v(TAG, "maincursor requeryed pos: " + mMainCursor.getPosition());

                    mChatCursorAdapter.notifyDataSetChanged();
                    //TODO: refresh chatroom
                }
            }
        }
    }

    class ChatCursorAdapter extends CursorAdapter {
        @SuppressWarnings("deprecation")
        public ChatCursorAdapter(Context context, Cursor c) {
            super(context, c);
        }

        @SuppressWarnings("unused")
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            LogUtil.v(TAG, "invoked!");
            int pos = cursor.getPosition();
            // layout left - 상대방
            mLayoutOthers = (LinearLayout) view.findViewById(R.id.chat_item_layout_others);
            ImageView ivOthersPhoto = (ImageView) view.findViewById(R.id.chat_item_layout_others_photo);
            RelativeLayout layoutOthersBubble = (RelativeLayout) view.findViewById(R.id.chat_item_layout_others_bubblelayout);
            TextView tvOthersName = (TextView) view.findViewById(R.id.chat_item_layout_others_name);
            TextView tvOthersTime = (TextView) view.findViewById(R.id.chat_item_layout_others_bubble_timestamp);
            TextView tvOthersContent = (TextView) view.findViewById(R.id.chat_item_layout_others_bubble_content);

            // layout right - 본인
            mLayoutMine = (LinearLayout) view.findViewById(R.id.chat_item_layout_mine);
            RelativeLayout layoutMineBubble = (RelativeLayout) view.findViewById(R.id.chat_item_layout_mine_bubblelayout);
            TextView tvMineTime = (TextView) view.findViewById(R.id.chat_item_layout_mine_bubble_timestamp);
            TextView tvMineContent = (TextView) view.findViewById(R.id.chat_item_layout_mine_bubble_content);

            Message m = ProviderController.MessageController.getMessageByCursor(cursor);

            LogUtil.v(TAG, "pos: " + pos + " / m data: " + m.toString());

            if(!(m.getSenderToken().equals(Constants.MY_TOKEN))) {
                //not my message

                tvOthersName.setText(m.getSenderNickname());
                tvOthersTime.setText(m.getSendDatetime());
                tvOthersContent.setText(m.getContents());

                mLayoutOthers.setVisibility(View.VISIBLE);
                mLayoutMine.setVisibility(View.GONE);
            } else {
                //my message
                tvMineTime.setText(m.getSendDatetime());
                tvMineContent.setText(m.getContents());

                mLayoutOthers.setVisibility(View.GONE);
                mLayoutMine.setVisibility(View.VISIBLE);
            }

        }
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = (LinearLayout) mInflater.inflate(R.layout.chat_item, null, false);
            return view;
        }
    }
}
