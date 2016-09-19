package kr.re.ec.talk;

import java.util.Date;

/**
 * Value object of ChatMsg
 * Created by slhyv on 9/20/2016.
 * @since
 * @modifiedDate 140420
 */
public class Message {
    public static final int STATE_NOT_SENT_TO_SERVER = 2200;
    public static final int STATE_REQUEST_SENT_TO_SERVER = 2201;
    public static final int STATE_SENT = 2202;
    public static final int ID_NOT_SET = -1;

    private int id;
    private int senderId;
    private String senderToken;
    private String senderNickname;
    private String sendDatetime;
    private String contents;
    private int state;

    public Message(int id, int senderId, String senderToken, String senderNickname, String sendDatetime, String contents, int state) {
        this.id = id;
        this.senderId = senderId;
        this.senderToken = senderToken;
        this.senderNickname = senderNickname;
        this.sendDatetime = sendDatetime;
        this.contents = contents;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getSenderToken() {
        return senderToken;
    }

    public void setSenderToken(String senderToken) {
        this.senderToken = senderToken;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public String getSendDatetime() {
        return sendDatetime;
    }

    public void setSendDatetime(String sendDatetime) {
        this.sendDatetime = sendDatetime;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", senderToken='" + senderToken + '\'' +
                ", senderNickname='" + senderNickname + '\'' +
                ", sendDatetime='" + sendDatetime + '\'' +
                ", contents='" + contents + '\'' +
                ", state=" + state +
                '}';
    }

}

