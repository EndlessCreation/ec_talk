package kr.re.ec.talk.dto;

/**
 * POJO base object for handling all the requests.
 * @author Taehee Kim 2016-09-17
 */
public class RequestBase {
	private String code;
	private String token;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "RequestBase [code=" + code + ", token=" + token + "]";
	}
}
