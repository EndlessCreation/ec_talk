package kr.re.ec.talk.dto;

/**
 * POJO object for handling Authentication.
 * @author Taehee Kim 2016-09-17
 */
public final class AuthRequest extends RequestBase {
	private String deviceId;
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	@Override
	public String toString() {
		return "AuthRequest [deviceId=" + deviceId + ", getCode()=" + getCode()
				+ ", getToken()=" + getToken() + "]";
	}
}
