package kr.re.ec.talk.dto;

/**
 * POJO base object for handling all the reponses.
 * @author Taehee Kim 2016-09-17
 */
public class ResponseBase {
	private Boolean success;
	private String message;
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ResponseBase [success=" + success + ", message=" + message
				+ "]";
	}
}
