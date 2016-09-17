package kr.re.ec.talk.dto;

/**
 * Message Value Object
 * @author Taehee Kim 2016-09-17
 */
public class Message {
	public static final long ID_NOT_SET = -1;
	public static final int STATE_NOT_SENT_TO_CLIENT = 0x1;
	public static final int STATE_SENT_TO_CLIENT = 0x2;
	
	private long id;
	private int senderId;
	private String senderNickname;
	private String senderToken;
	/**
	 * RFC 3339 (https://tools.ietf.org/html/rfc3339#section-5.5)
	 * except for Timezone (refer to http://dba.stackexchange.com/questions/48704/mysql-5-6-datetime-incorrect-datetime-value-2013-08-25t1700000000-with-er)
	 */
	private String sendDatetime;
	private String receiverToken;
	private String contents;
	/**
	 * 0: wait for sending to client
	 * 1: already sent
	 */
	private int state;
	
	/**
	 * @param id
	 * @param senderId
	 * @param senderNickname
	 * @param senderToken
	 * @param sendDatetime
	 * @param receiverToken
	 * @param contents
	 * @param state
	 */
	public Message(long id, int senderId, String senderNickname,
			String senderToken, String sendDatetime, String receiverToken, String contents, int state) {
		this.id = id;
		this.senderId = senderId;
		this.senderNickname = senderNickname;
		this.senderToken = senderToken;
		this.sendDatetime = sendDatetime;
		this.receiverToken = receiverToken;
		this.contents = contents;
		this.state = state;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public String getSenderNickname() {
		return senderNickname;
	}
	public void setSenderNickname(String senderNickname) {
		this.senderNickname = senderNickname;
	}
	public String getSenderToken() {
		return senderToken;
	}
	public void setSenderToken(String senderToken) {
		this.senderToken = senderToken;
	}
	public String getSendDatetime() {
		return sendDatetime;
	}
	public void setSendDatetime(String sendDatetime) {
		this.sendDatetime = sendDatetime;
	}
	public String getReceiverToken() {
		return receiverToken;
	}
	public void setReceiverToken(String receiverToken) {
		this.receiverToken = receiverToken;
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
		return "Message [id=" + id + ", senderId=" + senderId
				+ ", senderNickname=" + senderNickname + ", senderToken="
				+ senderToken + ", sendDatetime=" + sendDatetime
				+ ", contents=" + contents + ", state=" + state + "]";
	}
}
