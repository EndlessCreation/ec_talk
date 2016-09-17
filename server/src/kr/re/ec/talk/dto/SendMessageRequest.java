package kr.re.ec.talk.dto;

/**
 * POJO object for handling request about sending message to other.
 * @author Taehee Kim 2016-09-17
 */
public class SendMessageRequest extends RequestBase {
	private Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
}
