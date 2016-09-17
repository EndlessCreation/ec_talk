package kr.re.ec.talk.dto;

/**
 * POJO object for handling Authentication.
 * @author Taehee Kim 2016-09-17
 */
public final class AuthRequest extends RequestBase {

	@Override
	public String toString() {
		return "AuthRequest [getCode()=" + getCode() + ", getToken()="
				+ getToken() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}


}
