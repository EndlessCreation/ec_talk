package kr.re.ec.talk.dto;

/**
 * POJO object for handling Authentication.
 * @author Taehee Kim 2016-09-17
 */
public final class AuthResponse extends ResponseBase {

	@Override
	public String toString() {
		return "AuthResponse [getSuccess()=" + getSuccess() + ", getMessage()="
				+ getMessage() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
